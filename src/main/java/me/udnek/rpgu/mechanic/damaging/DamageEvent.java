package me.udnek.rpgu.mechanic.damaging;


import me.udnek.itemscoreu.customevent.CustomEvent;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.attribute.instance.MagicalDefenseMultiplierAttribute;
import me.udnek.rpgu.attribute.instance.PhysicalArmorAttribute;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.equipment.PlayerEquipment;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DamageEvent extends CustomEvent {

    private final List<ExtraFlag> extraFlags = new ArrayList<>();

    private Damage damage;
    private final EntityDamageEvent handlerEvent;
    private final @Nullable Entity damager;
    private final LivingEntity victim;
    private final boolean isCritical;

    public DamageEvent(@NotNull EntityDamageEvent handlerEvent){
        this.handlerEvent = handlerEvent;
        if (handlerEvent.getEntity() instanceof LivingEntity living){victim = living;}
        else victim = null;
        if (handlerEvent instanceof EntityDamageByEntityEvent entityDamageByEntityEvent){
            damager = entityDamageByEntityEvent.getDamager();
            isCritical = entityDamageByEntityEvent.isCritical();
        }
        else {
            damager = null;
            isCritical = false;
        }
    }

    public @NotNull Damage getDamage() {return this.damage;}
    public @Nullable Entity getDamager() {return damager;}
    public @NotNull Entity getVictim() {return victim;}
    public @NotNull EntityDamageEvent getHandler() {return this.handlerEvent;}
    public boolean isCritical() {return isCritical;}
    public void invoke(){
        if (victim == null) return;

        damagerDependentCalculations();
        equipmentAttacks();
        equipmentReceives();

        callEvent();

        // TODO: 2/15/2024 IMPLEMENT FINAL DAMAGE
        handlerEvent.setDamage(0);
        handlerEvent.setDamage(damage.getTotal());
        //victim.damage(damage.getTotal());
/*        if (damager == null){
            victim.damage(damage.getTotal(), handlerEvent.getDamageSource());
        } else {
            victim.damage(damage.getTotal(), damager);
        }*/

        DamageVisualizer.visualize(damage, victim);
    }

    private void damagerDependentCalculations() {
        damage = new Damage(DamageUtils.getDamageType(handlerEvent), handlerEvent.getDamage());
        if (damager != null){
            switch (damager){
                case LivingEntity living -> {
                    if (!(handlerEvent.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || handlerEvent.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)){return;}
                    damage = new Damage();
                    double potential = Attributes.MAGICAL_POTENTIAL.calculate(living);
                    AttributeInstance attribute = living.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
                    damage.addPhysical((attribute == null ? 0 : attribute.getValue()) * (isCritical() ? 1.5 : 1));
                    damage.addMagical(Attributes.MELEE_MAGICAL_DAMAGE_MULTIPLIER.calculate(living) * potential);
                    if (living instanceof Player player){
                        damage.multiplyPhysical(player.getCooledAttackStrength(0));
                    }

                }
                case Projectile projectile -> {
                    if (projectile instanceof AbstractArrow arrow){
                        damage = new Damage(arrow.getDamage() * arrow.getVelocity().length() * (arrow.isCritical() ? 1.5 : 1), 0);
                    }
                    getDamagerIfPlayer(player ->
                            PlayerEquipment.get(player).getEquipment((slot, customItem) ->
                                customItem.getComponentOrDefault(ComponentTypes.EQUIPPABLE_ITEM).onPlayerHitsWithProjectileWhenEquipped(customItem, player, slot, DamageEvent.this))
                    );
                }
                default -> {
                    return;
                }
            }
        }
    }

    private void getDamagerIfPlayer(Consumer<Player> consumer){
        if (damager instanceof Player player) consumer.accept(player);
    }

    private void equipmentAttacks() {
        if (!(damager instanceof Player player)) return;

        PlayerEquipment.get(player).getEquipment((slot, customItem) ->
                customItem.getComponentOrDefault(ComponentTypes.EQUIPPABLE_ITEM).onPlayerAttacksWhenEquipped(customItem, player, slot, DamageEvent.this));
    }

    private void equipmentReceives() {
        double armor = Attributes.PHYSICAL_ARMOR.calculate(victim);
        double magical_defense_mul = Attributes.MAGICAL_DEFENSE_MULTIPLIER.calculate(victim);
        double potential = Attributes.MAGICAL_POTENTIAL.calculate(victim);

        damage.multiplyPhysical(1- PhysicalArmorAttribute.calculateAbsorption(armor));
        damage.multiplyMagical(1- MagicalDefenseMultiplierAttribute.calculateAbsorption(magical_defense_mul*potential));

        if (!(victim instanceof Player player)) return;
        PlayerEquipment.get(player).getEquipment((slot, customItem) ->
                customItem.getComponentOrDefault(ComponentTypes.EQUIPPABLE_ITEM).onPlayerReceivesDamageWhenEquipped(customItem, player, slot, DamageEvent.this));
    }

    public abstract static class ExtraFlag{
        public ExtraFlag(){}
        public String getName() {
            return this.getClass().getName();
        }
    }

    public abstract static class ValuableExtraFlag<T> extends ExtraFlag{
        private T value;
        public ValuableExtraFlag(T value){
            this.value = value;
        }
        public T getValue() {
            return this.value;
        }
        public void setValue(T value) {
            this.value = value;
        }
    }

    public void addExtraFlag(ExtraFlag flag){
        extraFlags.add(flag);
    }

    public boolean containsExtraFlag(ExtraFlag neededFlag){
        String name = neededFlag.getName();
        for (ExtraFlag flag : extraFlags) {
            if (flag.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
    public ExtraFlag getExtraFlag(ExtraFlag neededFlag){
        String name = neededFlag.getName();
        for (ExtraFlag flag : extraFlags) {
            if (flag.getName().equals(name)){
                return flag;
            }
        }
        return null;
    }
}

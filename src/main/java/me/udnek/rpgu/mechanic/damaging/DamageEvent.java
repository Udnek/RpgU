package me.udnek.rpgu.mechanic.damaging;


import me.udnek.itemscoreu.customequipmentslot.SingleSlot;
import me.udnek.itemscoreu.customevent.CustomEvent;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.equipment.PlayerEquipment;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DamageEvent extends CustomEvent {

    private final List<ExtraFlag> extraFlags = new ArrayList<>();

    private Damage damage;
    private final EntityDamageEvent handlerEvent;
    private final Entity damager;
    private final Entity victim;
    private final boolean isCritical;
    private final DamagerType damagerType;

    public DamageEvent(EntityDamageEvent handlerEvent){
        this.handlerEvent = handlerEvent;
        this.victim = handlerEvent.getEntity();
        if (handlerEvent instanceof EntityDamageByEntityEvent entityDamageByEntityEvent){
            damager = entityDamageByEntityEvent.getDamager();
            isCritical = entityDamageByEntityEvent.isCritical();
            damagerType = DamagerType.ENTITY;
        }
        else {
            damager = null;
            isCritical = false;
            damagerType = DamagerType.NON_ENTITY;
        }
    }

    public Damage getDamage() {return this.damage;}
    public Entity getDamager() {return damager;}
    public Entity getVictim() {return victim;}
    public EntityDamageEvent getHandler() {return this.handlerEvent;}
    public boolean isCritical() {return isCritical;}
    public DamagerType getDamagerType() {return damagerType;}

    public void invoke(){

        damage = new Damage(Damage.Type.PHYSICAL, handlerEvent.getDamage());

        damagerDependentCalculations();
        playerEquipmentAttacks();
        playerEquipmentReceives();

        callEvent();

        // TODO: 2/15/2024 IMPLEMENT FINAL DAMAGE
        handlerEvent.setDamage(damage.getTotalDamage());
        DamageVisualizer.visualize(damage, victim);
    }

    private void damagerDependentCalculations() {
        if (damagerType == DamagerType.ENTITY){
            if (damager instanceof Player) {
                //damage.multiplyPhysicalDamage(isCritical ? 1.5 : 1);
                damage.addMagicalDamage(Attributes.MAGICAL_DAMAGE.calculate(damager));
            }

            else if (damager instanceof AbstractArrow arrow) {
                damage = new Damage(
                        arrow.getDamage() * arrow.getVelocity().length() * (arrow.isCritical() ? 1.5 : 1), 0);
                // TODO: 6/9/2024 MAGICAL DAMAGE
                //MagicalDamageAttribute.get(arrow));
            }
            else if (damager instanceof LivingEntity livingEntity){
                damage = DamageUtils.calculateMeleeDamage(livingEntity);
            }
        }
        else {
            Damage.Type type = switch (handlerEvent.getCause()) {
                case POISON, MAGIC, WITHER, SONIC_BOOM, DRAGON_BREATH -> Damage.Type.MAGICAL;
                default -> Damage.Type.PHYSICAL;
            };
            damage = new Damage(type, handlerEvent.getDamage());
        }


    }

    private void playerEquipmentAttacks() {
        if (!(damager instanceof Player player)) return;

        PlayerEquipment.get(player).getEquipment(new PlayerEquipment.EquipmentConsumer() {
            @Override
            public void accept(@NotNull SingleSlot slot, @NotNull CustomItem customItem) {
                customItem.getComponentOrDefault(ComponentTypes.EQUIPPABLE_ITEM).onPlayerAttacksWhenEquipped(customItem, player, slot, DamageEvent.this);
            }
        });
    }

    private void playerEquipmentReceives() {
        if (!(victim instanceof Player player)) return;

        PlayerEquipment.get(player).getEquipment(new PlayerEquipment.EquipmentConsumer() {
            @Override
            public void accept(@NotNull SingleSlot slot, @NotNull CustomItem customItem) {
                customItem.getComponentOrDefault(ComponentTypes.EQUIPPABLE_ITEM).onPlayerReceivesDamageWhenEquipped(customItem, player, slot, DamageEvent.this);
            }
        });
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

    public enum DamagerType {
        ENTITY,
        NON_ENTITY
    }
}

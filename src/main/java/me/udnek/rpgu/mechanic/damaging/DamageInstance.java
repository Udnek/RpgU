package me.udnek.rpgu.mechanic.damaging;


import com.google.common.base.Function;
import me.udnek.coreu.util.Reflex;
import me.udnek.coreu.util.Utils;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.equipment.PlayerEquipment;
import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.*;

public class DamageInstance {

    public static final double NARROW_ENCHANTMENTS_DAMAGE_BONUS = 2.5;
    private static final double PROJECTILE_GLOBAL_DAMAGE_MULTIPLIER = 0.70;

    private final List<ExtraFlag> extraFlags = new ArrayList<>();
    private Damage damage;
    private final EntityDamageEvent handlerEvent;
    private final @Nullable Entity damager;
    private final LivingEntity victim;
    private final boolean isCritical;

    public DamageInstance(@NotNull EntityDamageEvent handlerEvent){
        this.handlerEvent = handlerEvent;
        if (handlerEvent.getEntity() instanceof LivingEntity living){
            victim = living;
        }
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

    public @NotNull Damage.Type getDamageType(){return DamageUtils.getDamageType(handlerEvent);}
    public @NotNull Damage getDamage() {return this.damage;}
    public @Nullable Entity getDamager() {return damager;}
    public @Nullable Entity getCausingDamager(){return handlerEvent.getDamageSource().getCausingEntity();}
    public @NotNull Entity getVictim() {return victim;}
    public float getCooledAttackStrength() {return (damager instanceof Player player ? player.getCooledAttackStrength(0) : 1);}
    public @NotNull EntityDamageEvent getHandler() {return this.handlerEvent;}
    public @NotNull EntityDamageEvent.DamageCause getCause(){return handlerEvent.getCause();}
    public boolean isCritical() {return isCritical;}
    public void invoke(){
        if (victim == null) return;

        if (!DamageUtils.canDamageThisTick(handlerEvent.getDamageSource().getDamageType())){
            handlerEvent.setCancelled(true);
            return;
        }

        attackCalculations();

        DamageEvent event = new DamageEvent(this);
        event.callEvent();
        if (event.isCancelled()) {
            handlerEvent.setCancelled(true);
            return;
        }

        equipmentAttacks();
        event.setState(DamageEvent.State.AFTER_EQUIPMENT_ATTACKS);
        event.callEvent();
        if (event.isCancelled()) {
            handlerEvent.setCancelled(true);
            return;
        }

        equipmentReceives();
        event.setState(DamageEvent.State.AFTER_EQUIPMENT_RECEIVES);
        event.callEvent();
        if (event.isCancelled()) {
            handlerEvent.setCancelled(true);
            return;
        }

        if (victim.getNoDamageTicks() > 0 && victim.getLastDamage() > damage.getTotal()){
            handlerEvent.setCancelled(true);
        } else {
            Map<EntityDamageEvent.DamageModifier, Function<? super Double, Double>> modifierMap = Reflex.getFieldValue(handlerEvent, "modifierFunctions");
            EntityDamageEvent.DamageModifier[] toNullify = new EntityDamageEvent.DamageModifier[]{
                    EntityDamageEvent.DamageModifier.FREEZING,
                    EntityDamageEvent.DamageModifier.HARD_HAT,
                    EntityDamageEvent.DamageModifier.ARMOR,
                    EntityDamageEvent.DamageModifier.RESISTANCE,
                    EntityDamageEvent.DamageModifier.MAGIC
            };
            double baseDamage = damage.getTotal();
            for (Map.Entry<EntityDamageEvent.DamageModifier, Function<? super Double, Double>> entry : modifierMap.entrySet()) {
                EntityDamageEvent.DamageModifier modifier = entry.getKey();
                Function<? super Double, Double> function = entry.getValue();
                if (ArrayUtils.contains(toNullify, modifier)) handlerEvent.setDamage(modifier, 0);
                else {
                    double absorbed = -function.apply(damage.getTotal());
                    handlerEvent.setDamage(modifier, -absorbed);
                    baseDamage -= absorbed;
                }
            }
            handlerEvent.setDamage(EntityDamageEvent.DamageModifier.BASE, baseDamage);

            DamageVisualizer.visualize(damage, victim);

            new BukkitRunnable() {
                @Override
                public void run() {victim.setNoDamageTicks(5);}
            }.runTaskLater(RpgU.getInstance(), 1);
        }
    }

    private void attackCalculations() {
        if (handlerEvent.getDamage() >= DamageUtils.PHYSICAL_DAMAGE_BUFFER*DamageUtils.PHYSICAL_DAMAGE_PRECISION){
            double rawDamage = handlerEvent.getDamage();
            damage = new Damage(
                    (Math.floor(rawDamage/DamageUtils.PHYSICAL_DAMAGE_BUFFER)/DamageUtils.PHYSICAL_DAMAGE_PRECISION) -1,
                    (rawDamage%(DamageUtils.PHYSICAL_DAMAGE_BUFFER))/DamageUtils.MAGICAL_DAMAGE_PRECISION);
            return;
        }

        damage = new Damage(DamageUtils.getDamageType(handlerEvent), handlerEvent.getDamage());
        if (damager != null){
            switch (damager){
                case LivingEntity livingDamager -> {
                    if (getCause() != ENTITY_ATTACK && getCause() != ENTITY_SWEEP_ATTACK){return;}
                    damage = new Damage();
                    double potential = Attributes.MAGICAL_POTENTIAL.calculate(livingDamager);
                    AttributeInstance attribute = livingDamager.getAttribute(Attribute.ATTACK_DAMAGE);
                    damage.addPhysical((attribute == null ? 0 : attribute.getValue()) * (isCritical() ? Attributes.CRITICAL_DAMAGE.calculate(livingDamager) : 1));
                    damage.addMagical(Attributes.MELEE_MAGICAL_DAMAGE_MULTIPLIER.calculate(livingDamager) * potential);


                    ItemStack mainHand = DamageUtils.getItemInMainHand(livingDamager);
                    // SMITE
                    int smite = mainHand.getEnchantmentLevel(Enchantment.SMITE);
                    if (smite != 0 && Tag.ENTITY_TYPES_SENSITIVE_TO_SMITE.isTagged(victim.getType())){
                        damage.addMagical(smite * NARROW_ENCHANTMENTS_DAMAGE_BONUS);}
                    // ARTHROPODS
                    int arthropods = mainHand.getEnchantmentLevel(Enchantment.BANE_OF_ARTHROPODS);
                    if (arthropods != 0 && Tag.ENTITY_TYPES_SENSITIVE_TO_BANE_OF_ARTHROPODS.isTagged(victim.getType())){
                        damage.addMagical(arthropods * NARROW_ENCHANTMENTS_DAMAGE_BONUS);}
                    // IMPALING
                    int impaling = mainHand.getEnchantmentLevel(Enchantment.IMPALING);
                    if (impaling != 0 && victim.isInWaterOrRain()){
                        damage.addMagical(impaling * NARROW_ENCHANTMENTS_DAMAGE_BONUS);}

                    // MACE
                    if (mainHand.getType() == Material.MACE && !damager.isOnGround() && damager.getFallDistance() > 1.5){
                        float firstLimit = 3;
                        float secondLimit = 8;
                        float fallDistance = damager.getFallDistance();
                        float bonus;
                        if (fallDistance <= firstLimit) {
                            bonus = 4 * fallDistance;
                        } else if (fallDistance <= secondLimit) {
                            bonus = 4*firstLimit + 2*(fallDistance - firstLimit);
                        } else {
                            bonus = (4*firstLimit +2*(secondLimit - firstLimit)) + 1*(fallDistance - secondLimit);
                        }
                        damage.addPhysical(bonus);
                    }

                    // DENSITY
                    int density = mainHand.getEnchantmentLevel(Enchantment.DENSITY);
                    if (density != 0){
                        damage.addPhysical(livingDamager.getFallDistance() * 0.5 * density);
                    }

                    // BREACH
                    int breach = mainHand.getEnchantmentLevel(Enchantment.BREACH);
                    if (breach != 0){
                        double toTransfer = damage.getPhysical()*0.15*breach;
                        damage.addPhysical(-toTransfer);
                        damage.addMagical(toTransfer);
                    }

                    if (livingDamager instanceof Player player){
                        if (getCause() == ENTITY_SWEEP_ATTACK){
                            double value = player.getAttribute(Attribute.SWEEPING_DAMAGE_RATIO).getValue();
                            damage.multiply(value).addPhysical(1);
                        } else {
                            damage.multiply(player.getCooledAttackStrength(0));
                        }

                    }

                }
                case Projectile projectile -> {
                    if (projectile instanceof AbstractArrow arrow){
                        damage = new Damage(arrow.getDamage() * arrow.getVelocity().length()  * PROJECTILE_GLOBAL_DAMAGE_MULTIPLIER, 0);
                        if (arrow.isCritical()){
                            if (arrow.getShooter() instanceof LivingEntity livingShooter)
                                damage.multiplyPhysical(Attributes.CRITICAL_DAMAGE.calculate(livingShooter));
                            else damage.multiplyPhysical(Attributes.CRITICAL_DAMAGE.getDefault());
                        }

                        Utils.consumeIfNotNull(arrow.getWeapon(), bow ->
                                damage.multiplyPhysical(1 + bow.getEnchantmentLevel(Enchantment.POWER)*0.3)
                        );
                        int impaling = arrow.getItemStack().getEnchantmentLevel(Enchantment.IMPALING);
                        if (impaling != 0 && victim.isInWaterOrRain()) damage.addMagical(impaling * NARROW_ENCHANTMENTS_DAMAGE_BONUS);
                    }
                    if (projectile.getShooter() instanceof Player shooter){
                        PlayerEquipment.get(shooter).getEquipment((slot, custom.Item) ->
                                custom.Item.getComponents().getOrDefault(ComponentTypes.EQUIPPABLE_ITEM).onPlayerHitsWithProjectileWhenEquipped(custom.Item, shooter, slot, DamageInstance.this));
                    }
                }
                default -> {}
            }
        }
        if (handlerEvent.getDamageSource().scalesWithDifficulty()){
            damage.multiply(DamageUtils.difficultyMultiplier(victim.getWorld().getDifficulty()));
        }
    }

    private void equipmentAttacks() {
        if (!(damager instanceof Player player)) return;

        PlayerEquipment.get(player).getEquipment((slot, custom.Item) ->
                custom.Item.getComponents().getOrDefault(ComponentTypes.EQUIPPABLE_ITEM).onPlayerAttacksWhenEquipped(custom.Item, player, slot, DamageInstance.this));
    }

    private void equipmentReceives() {

        damage.multiplyPhysical(1 - Attributes.PHYSICAL_RESISTANCE.calculate(victim));
        damage.multiplyMagical(1 - Attributes.MAGICAL_RESISTANCE.calculate(victim));

        final List<EntityDamageEvent.DamageCause> fireCauses = List.of(CAMPFIRE, FIRE, HOT_FLOOR, LAVA);
        if (damager instanceof Projectile){
            double levels = Attributes.PROJECTILE_PROTECTION.calculate(victim);
            damage.multiply(getDamageType(),1-levels/16);
        } else if (getCause() == BLOCK_EXPLOSION || handlerEvent.getCause() == ENTITY_EXPLOSION){
            double levels = Attributes.BLAST_PROTECTION.calculate(victim);
            damage.multiply(getDamageType(),1-levels/16);
        } else if (fireCauses.contains(getCause())){
            double levels = Attributes.FIRE_PROTECTION.calculate(victim);
            damage.multiply(getDamageType(),1-levels/16);
        } else if (handlerEvent.getCause() == FALL){
            double levels = Attributes.FALLING_PROTECTION.calculate(victim);
            damage.multiply(getDamageType(), 1-(levels/4/2));
        }

        if (!(victim instanceof Player player)) return;
        PlayerEquipment.get(player).getEquipment((slot, custom.Item) ->
                custom.Item.getComponents().getOrDefault(ComponentTypes.EQUIPPABLE_ITEM).onPlayerReceivesDamageWhenEquipped(custom.Item, player, slot, DamageInstance.this));
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

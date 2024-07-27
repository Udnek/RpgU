package me.udnek.rpgu.mechanic.damaging;


import me.udnek.itemscoreu.customevent.CustomEvent;
import me.udnek.rpgu.equipment.Equippable;
import me.udnek.rpgu.equipment.PlayerEquipment;
import me.udnek.rpgu.equipment.PlayerEquipmentDatabase;
import me.udnek.rpgu.mechanic.damaging.visualizer.DamageVisualizer;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.Map;

public class DamageEvent extends CustomEvent {

    private final ArrayList<ExtraFlag> extraFlags = new ArrayList<>();

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
    public EntityDamageEvent getHandlerEvent() {return this.handlerEvent;}
    public boolean isCritical() {return isCritical;}
    public DamagerType getDamagerType() {return damagerType;}

    public void invoke(){

        this.damagerDependentCalculations();
        this.playerEquipmentAttacks();
        this.playerEquipmentReceives();

        callEvent();

        // TODO: 2/15/2024 IMPLEMENT FINAL DAMAGE
        handlerEvent.setDamage(damage.getTotalDamage());
        DamageVisualizer.visualize(damage, victim);
    }

    private void damagerDependentCalculations() {
        if (damagerType == DamagerType.ENTITY){
            damage = DamageUtils.calculateMeleeDamage(damager);

            if (damager instanceof Player) {
                damage.multiplyPhysicalDamage(isCritical ? 1.5 : 1);
            }

            else if (damager instanceof AbstractArrow arrow) {
                this.damage = new Damage(
                        arrow.getDamage() * arrow.getVelocity().length() * (arrow.isCritical() ? 1.5 : 1), 0);
                // TODO: 6/9/2024 MAGICAL DAMAGE
                //MagicalDamageAttribute.get(arrow));
            }
        }
        else {
            Damage.Type type;
            switch (handlerEvent.getCause()){
                case POISON:
                case MAGIC:
                case WITHER:
                case SONIC_BOOM:
                case DRAGON_BREATH:
                    type = Damage.Type.MAGICAL;
                    break;
                default:
                    type = Damage.Type.PHYSICAL;
            }
            damage = new Damage(type, handlerEvent.getDamage());
        }


    }

    private void playerEquipmentAttacks() {
        if (!(damager instanceof Player player)) return;

        for (Map.Entry<PlayerEquipment.Slot, Equippable> entry : PlayerEquipmentDatabase.get(player).getFullEquipment().entrySet()) {
            entry.getValue().onPlayerAttacksWhenEquipped(player, entry.getKey().equipmentSlot, this);
        }
    }

    private void playerEquipmentReceives() {
        if (!(victim instanceof Player player)) return;

        for (Map.Entry<PlayerEquipment.Slot, Equippable> entry : PlayerEquipmentDatabase.get(player).getFullEquipment().entrySet()) {
            entry.getValue().onPlayerReceivesDamageWhenEquipped(player, entry.getKey().equipmentSlot, this);
        }
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

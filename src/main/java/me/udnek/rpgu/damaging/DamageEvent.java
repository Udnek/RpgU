package me.udnek.rpgu.damaging;


import me.udnek.itemscoreu.customevent.CustomEvent;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.utils.LogUtils;
import me.udnek.rpgu.damaging.visualizer.DamageVisualizer;
import me.udnek.rpgu.equipment.Equippable;
import me.udnek.rpgu.equipment.PlayerEquipmentDatabase;
import me.udnek.rpgu.item.abstraction.EquippableItem;
import me.udnek.rpgu.item.abstraction.RpgUCustomItem;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class DamageEvent extends CustomEvent {

    private final ArrayList<ExtraFlag> extraFlags = new ArrayList<>();

    private Damage damage;
    private final EntityDamageByEntityEvent handlerEvent;
    private final Entity damager;
    private final Entity victim;

    public DamageEvent(EntityDamageByEntityEvent handlerEvent){
        this.handlerEvent = handlerEvent;
        this.damager = handlerEvent.getDamager();
        this.victim = handlerEvent.getEntity();
    }

    public Damage getDamage() {return this.damage;}
    public Entity getDamager() {return damager;}
    public Entity getVictim() {return victim;}
    public EntityDamageByEntityEvent getHandlerEvent() {return this.handlerEvent;}

    public void invoke(){

        damage = new Damage(Damage.Type.PHYSICAL, handlerEvent.getDamage());

        this.entityDependentCalculations();
        this.meeleCalculations();
        this.playerEquipmentAttacks();
        this.playerEquipmentReceives();

        // TODO: 2/15/2024 IMPLEMENT FINAL DAMAGE
        handlerEvent.setDamage(damage.getTotalDamage());
        DamageVisualizer.visualize(damage, victim);
    }

    private void entityDependentCalculations() {
        damage = DamageUtils.calculateMeleeDamage(damager);

        if (damager instanceof Player) {
            damage.multiplyPhysicalDamage(handlerEvent.isCritical() ? 1.5 : 1);
        }

        else if (damager instanceof AbstractArrow arrow) {
            this.damage = new Damage(
                    arrow.getDamage() * arrow.getVelocity().length() * (arrow.isCritical() ? 1.5 : 1), 0);
            // TODO: 6/9/2024 MAGICAL DAMAGE
                    //MagicalDamageAttribute.get(arrow));
        }
    }


    private void meeleCalculations(){
        if (handlerEvent.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK || !(damager instanceof LivingEntity)) return;
        EntityEquipment equipment = ((LivingEntity) damager).getEquipment();
        if (equipment == null) return;
        ItemStack itemStack = equipment.getItemInMainHand();
        CustomItem customItem = CustomItem.get(itemStack);
        if (customItem == null) return;
        if (customItem instanceof RpgUCustomItem rpgUCustomItem) {
            rpgUCustomItem.onEntityAttacks(this);
        }
    }


    private void playerEquipmentAttacks() {
        if (!(damager instanceof Player player)) return;

        for (Equippable equippableItem : PlayerEquipmentDatabase.get(player).getFullEquipment()) {
            if (equippableItem != null) equippableItem.onPlayerAttacksWhenEquipped(player, this);
        }


    }

    private void playerEquipmentReceives() {
        if (!(victim instanceof Player player)) return;

        for (Equippable equippableItem : PlayerEquipmentDatabase.get(player).getFullEquipment()) {
            if (equippableItem != null)
                equippableItem.onPlayerReceivesDamageWhenEquipped(player, this);
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

}

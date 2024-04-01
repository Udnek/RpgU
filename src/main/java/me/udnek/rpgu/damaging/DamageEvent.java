package me.udnek.rpgu.damaging;


import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.utils.CustomItemUtils;
import me.udnek.rpgu.damaging.visualizer.DamageVisualizer;
import me.udnek.rpgu.equipment.PlayersEquipmentDatabase;
import me.udnek.rpgu.item.abstracts.EquippableItem;
import me.udnek.rpgu.item.abstracts.WeaponItem;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class DamageEvent{

    //private final HashMap<String, ExtraFlag<?>> extraFlags = new HashMap<>();
    private final ArrayList<ExtraFlag> extraFlags = new ArrayList<>();

    private Damage damage = new Damage();
    private final EntityDamageByEntityEvent event;

    public DamageEvent(EntityDamageByEntityEvent event){
        this.event = event;
    }

    public Damage getDamage() {return this.damage;}
    public EntityDamageByEntityEvent getEvent() {return this.event;}

    public void invoke(){

        this.damage = new Damage(Damage.DamageType.PHYSICAL, event.getDamage());

        this.entityDependentCalculations();
        this.meeleCalculations();
        this.playerEquipmentAttacks();
        this.playerEquipmentReceives();

        // TODO: 2/15/2024 IMPLEMENT FINAL DAMAGE
        event.setDamage(damage.getDamage());
        DamageVisualizer.visualize(damage, event.getEntity());
    }

    private void entityDependentCalculations() {
        if ((event.getDamager() instanceof Player)) {
            Player player = (Player) event.getDamager();
            damage = DamageUtils.calculateMeleeDamage(player);
            //player.get
            damage.multiplyPhysicalDamage(event.isCritical() ? 1.5 : 1);
        }

        else if (event.getDamager() instanceof AbstractArrow) {
            AbstractArrow arrow = (AbstractArrow) event.getDamager();
            this.damage = new Damage(
                    arrow.getDamage()*arrow.getVelocity().length()* (arrow.isCritical() ? 1.5f : 1), 0);
                    //MagicalDamageAttribute.get(arrow));
        }
    }


    private void meeleCalculations(){
        if (this.event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK || !(this.event.getDamager() instanceof LivingEntity)) return;
        EntityEquipment equipment = ((LivingEntity) this.event.getDamager()).getEquipment();
        if (equipment == null) return;
        ItemStack itemStack = equipment.getItemInMainHand();
        CustomItem customItem = CustomItemUtils.getFromItemStack(itemStack);
        if (customItem == null) return;
        if (customItem instanceof WeaponItem) {
            ((WeaponItem) customItem).onEntityAttacks((LivingEntity) this.event.getDamager(), this);
        }
    }


    private void playerEquipmentAttacks() {
        if (!(event.getDamager() instanceof Player)) return;

        Player player = (Player) event.getDamager();

        for (EquippableItem equippableItem : PlayersEquipmentDatabase.get(player).getFullEquipment()) {
            if (equippableItem != null) equippableItem.onPlayerAttacksWhenEquipped(player, this);
        }


    }

    private void playerEquipmentReceives() {
        if (!(event.getEntity() instanceof Player)){return;}

        Player player = (Player) event.getEntity();

        for (EquippableItem equippableItem : PlayersEquipmentDatabase.get(player).getFullEquipment()) {
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

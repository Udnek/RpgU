package me.udnek.rpgu.util;

import me.udnek.itemscoreu.customattribute.AttributeUtils;
import me.udnek.itemscoreu.customevent.CustomItemGeneratedEvent;
import me.udnek.itemscoreu.customevent.InitializationEvent;
import me.udnek.itemscoreu.util.InitializationProcess;
import me.udnek.itemscoreu.util.SelfRegisteringListener;
import me.udnek.itemscoreu.util.VanillaItemManager;
import me.udnek.rpgu.RpgU;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.EnumMap;

public class AttributeManaging extends SelfRegisteringListener {
     public AttributeManaging(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onInit(InitializationEvent event){
        if (event.getStep() == InitializationProcess.Step.BEFORE_VANILLA_MANAGER){
            for (Material item : items.keySet()){
                VanillaItemManager.getInstance().replaceVanillaMaterial(item);
            }
        }
    }

    private static final EnumMap<Material, HpAndArmor> items = new EnumMap<>(Material.class);

     static {
         items.put(Material.LEATHER_HELMET, new HpAndArmor(1, 0));
         items.put(Material.LEATHER_CHESTPLATE, new HpAndArmor(1, 0));
         items.put(Material.LEATHER_LEGGINGS, new HpAndArmor(1, 0));
         items.put(Material.LEATHER_BOOTS, new HpAndArmor(1, 0));



         items.put(Material.IRON_HELMET, new HpAndArmor(1, 1));
         items.put(Material.IRON_CHESTPLATE, new HpAndArmor(2, 1));
         items.put(Material.IRON_LEGGINGS, new HpAndArmor(2, 1));
         items.put(Material.IRON_BOOTS, new HpAndArmor(1, 1));

         items.put(Material.CHAINMAIL_HELMET, items.get(Material.IRON_HELMET));
         items.put(Material.CHAINMAIL_CHESTPLATE, items.get(Material.IRON_CHESTPLATE));
         items.put(Material.CHAINMAIL_LEGGINGS, items.get(Material.IRON_LEGGINGS));
         items.put(Material.CHAINMAIL_BOOTS, items.get(Material.IRON_BOOTS));

         items.put(Material.GOLDEN_HELMET, items.get(Material.IRON_HELMET));
         items.put(Material.GOLDEN_CHESTPLATE, items.get(Material.IRON_CHESTPLATE));
         items.put(Material.GOLDEN_LEGGINGS, items.get(Material.IRON_LEGGINGS));
         items.put(Material.GOLDEN_BOOTS, items.get(Material.IRON_BOOTS));

         items.put(Material.DIAMOND_HELMET, new HpAndArmor(2, 2));
         items.put(Material.DIAMOND_CHESTPLATE, new HpAndArmor(4, 5));
         items.put(Material.DIAMOND_LEGGINGS, new HpAndArmor(2, 4));
         items.put(Material.DIAMOND_BOOTS, new HpAndArmor(2, 1));

         items.put(Material.NETHERITE_HELMET, items.get(Material.DIAMOND_HELMET));
         items.put(Material.NETHERITE_CHESTPLATE, items.get(Material.DIAMOND_CHESTPLATE));
         items.put(Material.NETHERITE_LEGGINGS, items.get(Material.DIAMOND_LEGGINGS));
         items.put(Material.NETHERITE_BOOTS, items.get(Material.DIAMOND_BOOTS));
     }


    @EventHandler
    public void onItemGenerates(CustomItemGeneratedEvent event){
         ItemStack itemStack = event.getItemStack();
         Material material = itemStack.getType();

         if (VanillaItemManager.isReplaced(itemStack) && items.containsKey(material)){
            if (itemStack.getItemMeta().getAttributeModifiers() != null) AttributeUtils.addDefaultAttributes(itemStack);
            EquipmentSlotGroup slot = material.getEquipmentSlot().getGroup();
            AttributeUtils.appendAttribute(itemStack, Attribute.GENERIC_MAX_HEALTH, new NamespacedKey(RpgU.getInstance(), "max_health_" + slot), items.get(material).hp, AttributeModifier.Operation.ADD_NUMBER, slot);
            itemStack.editMeta(itemMeta -> itemMeta.removeAttributeModifier(Attribute.GENERIC_ARMOR));
            AttributeUtils.addAttribute(itemStack, Attribute.GENERIC_ARMOR, new NamespacedKey(RpgU.getInstance(), "base_armor_" + slot), items.get(material).armor, AttributeModifier.Operation.ADD_NUMBER, slot);
            itemStack.editMeta(itemMeta -> itemMeta.removeAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS));
        }
    }

    record HpAndArmor(double hp, double armor) {
    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();

        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(10);
    }
}

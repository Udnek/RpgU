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
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

public class AttributeManaging extends SelfRegisteringListener {
     public AttributeManaging(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onInit(InitializationEvent event){
        if (event.getStep() == InitializationProcess.Step.BEFORE_VANILLA_MANAGER){
            for (Material item : items_armor.keySet()){VanillaItemManager.getInstance().replaceVanillaMaterial(item);}
            for (Material item : diamond_tools){VanillaItemManager.getInstance().replaceVanillaMaterial(item);}
        }
    }

    private static final EnumMap<Material, HpAndArmor> items_armor = new EnumMap<>(Material.class);
    private static final Set<Material> chainmail_armor = new HashSet<>();
    private static final Set<Material> diamond_armor = new HashSet<>();
    private static final Set<Material> diamond_tools = new HashSet<>();

     static {
         items_armor.put(Material.LEATHER_HELMET, new HpAndArmor(1, 0));
         items_armor.put(Material.LEATHER_CHESTPLATE, new HpAndArmor(1, 0));
         items_armor.put(Material.LEATHER_LEGGINGS, new HpAndArmor(1, 0));
         items_armor.put(Material.LEATHER_BOOTS, new HpAndArmor(1, 0));

         items_armor.put(Material.IRON_HELMET, new HpAndArmor(1, 1));
         items_armor.put(Material.IRON_CHESTPLATE, new HpAndArmor(2, 1));
         items_armor.put(Material.IRON_LEGGINGS, new HpAndArmor(2, 1));
         items_armor.put(Material.IRON_BOOTS, new HpAndArmor(1, 1));

         items_armor.put(Material.CHAINMAIL_HELMET, items_armor.get(Material.IRON_HELMET));
         items_armor.put(Material.CHAINMAIL_CHESTPLATE, items_armor.get(Material.IRON_CHESTPLATE));
         items_armor.put(Material.CHAINMAIL_LEGGINGS, items_armor.get(Material.IRON_LEGGINGS));
         items_armor.put(Material.CHAINMAIL_BOOTS, items_armor.get(Material.IRON_BOOTS));

         items_armor.put(Material.GOLDEN_HELMET, items_armor.get(Material.IRON_HELMET));
         items_armor.put(Material.GOLDEN_CHESTPLATE, items_armor.get(Material.IRON_CHESTPLATE));
         items_armor.put(Material.GOLDEN_LEGGINGS, items_armor.get(Material.IRON_LEGGINGS));
         items_armor.put(Material.GOLDEN_BOOTS, items_armor.get(Material.IRON_BOOTS));

         items_armor.put(Material.DIAMOND_HELMET, new HpAndArmor(2, 2));
         items_armor.put(Material.DIAMOND_CHESTPLATE, new HpAndArmor(4, 5));
         items_armor.put(Material.DIAMOND_LEGGINGS, new HpAndArmor(2, 4));
         items_armor.put(Material.DIAMOND_BOOTS, new HpAndArmor(2, 1));

         items_armor.put(Material.NETHERITE_HELMET, items_armor.get(Material.DIAMOND_HELMET));
         items_armor.put(Material.NETHERITE_CHESTPLATE, items_armor.get(Material.DIAMOND_CHESTPLATE));
         items_armor.put(Material.NETHERITE_LEGGINGS, items_armor.get(Material.DIAMOND_LEGGINGS));
         items_armor.put(Material.NETHERITE_BOOTS, items_armor.get(Material.DIAMOND_BOOTS));

         chainmail_armor.add(Material.CHAINMAIL_HELMET);
         chainmail_armor.add(Material.CHAINMAIL_CHESTPLATE);
         chainmail_armor.add(Material.CHAINMAIL_LEGGINGS);
         chainmail_armor.add(Material.CHAINMAIL_BOOTS);

         diamond_armor.add(Material.DIAMOND_HELMET);
         diamond_armor.add(Material.DIAMOND_CHESTPLATE);
         diamond_armor.add(Material.DIAMOND_LEGGINGS);
         diamond_armor.add(Material.DIAMOND_BOOTS);

         diamond_tools.add(Material.DIAMOND_AXE);
         diamond_tools.add(Material.DIAMOND_HOE);
         diamond_tools.add(Material.DIAMOND_PICKAXE);
         diamond_tools.add(Material.DIAMOND_SWORD);
         diamond_tools.add(Material.DIAMOND_SHOVEL);
     }


    @EventHandler
    public void onItemGenerates(CustomItemGeneratedEvent event){
        ItemStack itemStack = event.getItemStack();
        Material material = itemStack.getType();

        if (!VanillaItemManager.isReplaced(itemStack))return;

        if (items_armor.containsKey(material)){
            AttributeUtils.addDefaultAttributes(itemStack);
            EquipmentSlotGroup slot = material.getEquipmentSlot().getGroup();
            AttributeUtils.appendAttribute(itemStack, Attribute.MAX_HEALTH, new NamespacedKey(RpgU.getInstance(), "max_health_" + slot), items_armor.get(material).hp, AttributeModifier.Operation.ADD_NUMBER, slot);
            itemStack.editMeta(itemMeta -> itemMeta.removeAttributeModifier(Attribute.ARMOR));
            AttributeUtils.addAttribute(itemStack, Attribute.ARMOR, new NamespacedKey(RpgU.getInstance(), "base_armor_" + slot), items_armor.get(material).armor, AttributeModifier.Operation.ADD_NUMBER, slot);
            itemStack.editMeta(itemMeta -> itemMeta.removeAttributeModifier(Attribute.ARMOR_TOUGHNESS));
        }

        if (chainmail_armor.contains(material)) {itemStack.editMeta(itemMeta -> itemMeta.setRarity(ItemRarity.COMMON));}

        if (diamond_armor.contains(material)) {itemStack.editMeta(Damageable.class, itemMeta -> itemMeta.setMaxDamage(material.getMaxDurability() * 37 / 33));}

        if (diamond_tools.contains(material)) {
            itemStack.editMeta(Damageable.class, itemMeta -> itemMeta.setMaxDamage(2031));

            AttributeUtils.addDefaultAttributes(itemStack);
            itemStack.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }
    }

    record HpAndArmor(double hp, double armor) {
    }
}

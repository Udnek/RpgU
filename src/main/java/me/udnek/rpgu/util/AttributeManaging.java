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
            for (Material item : itemsArmor.keySet()){VanillaItemManager.getInstance().replaceVanillaMaterial(item);}
            for (Material item : diamondTools){VanillaItemManager.getInstance().replaceVanillaMaterial(item);}
        }
    }

    private static final EnumMap<Material, HpAndArmor> itemsArmor = new EnumMap<>(Material.class);
    private static final Set<Material> chainmailArmor = new HashSet<>();
    private static final Set<Material> diamondArmor = new HashSet<>();
    private static final Set<Material> diamondTools = new HashSet<>();

     static {
         itemsArmor.put(Material.LEATHER_HELMET, new HpAndArmor(1, 0, 0));
         itemsArmor.put(Material.LEATHER_CHESTPLATE, new HpAndArmor(1, 0, 0));
         itemsArmor.put(Material.LEATHER_LEGGINGS, new HpAndArmor(1, 0, 0));
         itemsArmor.put(Material.LEATHER_BOOTS, new HpAndArmor(1, 0, 0));

         itemsArmor.put(Material.IRON_HELMET, new HpAndArmor(1, 1, 0.025));
         itemsArmor.put(Material.IRON_CHESTPLATE, new HpAndArmor(2, 1, 0.025));
         itemsArmor.put(Material.IRON_LEGGINGS, new HpAndArmor(2, 1, 0.025));
         itemsArmor.put(Material.IRON_BOOTS, new HpAndArmor(1, 1, 0.025));

         itemsArmor.put(Material.CHAINMAIL_HELMET, itemsArmor.get(Material.IRON_HELMET));
         itemsArmor.put(Material.CHAINMAIL_CHESTPLATE, itemsArmor.get(Material.IRON_CHESTPLATE));
         itemsArmor.put(Material.CHAINMAIL_LEGGINGS, itemsArmor.get(Material.IRON_LEGGINGS));
         itemsArmor.put(Material.CHAINMAIL_BOOTS, itemsArmor.get(Material.IRON_BOOTS));

         itemsArmor.put(Material.GOLDEN_HELMET, itemsArmor.get(Material.IRON_HELMET));
         itemsArmor.put(Material.GOLDEN_CHESTPLATE, itemsArmor.get(Material.IRON_CHESTPLATE));
         itemsArmor.put(Material.GOLDEN_LEGGINGS, itemsArmor.get(Material.IRON_LEGGINGS));
         itemsArmor.put(Material.GOLDEN_BOOTS, itemsArmor.get(Material.IRON_BOOTS));

         itemsArmor.put(Material.DIAMOND_HELMET, new HpAndArmor(2, 2, 0.05));
         itemsArmor.put(Material.DIAMOND_CHESTPLATE, new HpAndArmor(4, 5, 0.05));
         itemsArmor.put(Material.DIAMOND_LEGGINGS, new HpAndArmor(2, 4, 0.05));
         itemsArmor.put(Material.DIAMOND_BOOTS, new HpAndArmor(2, 1, 0.05));

         itemsArmor.put(Material.NETHERITE_HELMET, itemsArmor.get(Material.DIAMOND_HELMET));
         itemsArmor.put(Material.NETHERITE_CHESTPLATE, itemsArmor.get(Material.DIAMOND_CHESTPLATE));
         itemsArmor.put(Material.NETHERITE_LEGGINGS, itemsArmor.get(Material.DIAMOND_LEGGINGS));
         itemsArmor.put(Material.NETHERITE_BOOTS, itemsArmor.get(Material.DIAMOND_BOOTS));

         chainmailArmor.add(Material.CHAINMAIL_HELMET);
         chainmailArmor.add(Material.CHAINMAIL_CHESTPLATE);
         chainmailArmor.add(Material.CHAINMAIL_LEGGINGS);
         chainmailArmor.add(Material.CHAINMAIL_BOOTS);

         diamondArmor.add(Material.DIAMOND_HELMET);
         diamondArmor.add(Material.DIAMOND_CHESTPLATE);
         diamondArmor.add(Material.DIAMOND_LEGGINGS);
         diamondArmor.add(Material.DIAMOND_BOOTS);

         diamondTools.add(Material.DIAMOND_AXE);
         diamondTools.add(Material.DIAMOND_HOE);
         diamondTools.add(Material.DIAMOND_PICKAXE);
         diamondTools.add(Material.DIAMOND_SWORD);
         diamondTools.add(Material.DIAMOND_SHOVEL);
     }


    @EventHandler
    public void onItemGenerates(CustomItemGeneratedEvent event){
        ItemStack itemStack = event.getItemStack();
        Material material = itemStack.getType();

        if (!VanillaItemManager.isReplaced(itemStack))return;

        if (itemsArmor.containsKey(material)){
            AttributeUtils.addDefaultAttributes(itemStack);
            EquipmentSlotGroup slot = material.getEquipmentSlot().getGroup();
            itemStack.editMeta(itemMeta -> itemMeta.removeAttributeModifier(Attribute.ARMOR));

            AttributeUtils.appendAttribute(itemStack, Attribute.MAX_HEALTH, new NamespacedKey(RpgU.getInstance(), "max_health_" + slot), itemsArmor.get(material).hp, AttributeModifier.Operation.ADD_NUMBER, slot);
            AttributeUtils.addAttribute(itemStack, Attribute.ARMOR, new NamespacedKey(RpgU.getInstance(), "base_armor_" + slot), itemsArmor.get(material).armor, AttributeModifier.Operation.ADD_NUMBER, slot);
            AttributeUtils.addAttribute(itemStack, Attribute.ATTACK_DAMAGE, new NamespacedKey(RpgU.getInstance(), "base_attack_damage" + slot), itemsArmor.get(material).attack, AttributeModifier.Operation.ADD_SCALAR, slot);

            itemStack.editMeta(itemMeta -> itemMeta.removeAttributeModifier(Attribute.ARMOR_TOUGHNESS));
        }

        if (chainmailArmor.contains(material)) {itemStack.editMeta(itemMeta -> itemMeta.setRarity(ItemRarity.COMMON));}

        if (diamondArmor.contains(material)) {itemStack.editMeta(Damageable.class, itemMeta -> itemMeta.setMaxDamage(material.getMaxDurability() * 37 / 33));}

        if (diamondTools.contains(material)) {
            itemStack.editMeta(Damageable.class, itemMeta -> itemMeta.setMaxDamage(2031));

            AttributeUtils.addDefaultAttributes(itemStack);
            itemStack.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }
    }

    record HpAndArmor(double hp, double armor, double attack) {
    }
}

package me.udnek.rpgu.vanilla;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.itemscoreu.customattribute.AttributeUtils;
import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customcomponent.instance.CustomItemAttributesComponent;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customevent.CustomItemGeneratedEvent;
import me.udnek.itemscoreu.customevent.InitializationEvent;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.customitem.RepairData;
import me.udnek.itemscoreu.customitem.VanillaItemManager;
import me.udnek.itemscoreu.customregistry.InitializationProcess;
import me.udnek.itemscoreu.util.SelfRegisteringListener;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.instance.DeathProtectionPassive;
import me.udnek.rpgu.component.instance.ElytraActivator;
import me.udnek.rpgu.component.instance.GliderComponent;
import me.udnek.rpgu.component.instance.GoldenArmorPassive;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.util.Utils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class AttributeManaging extends SelfRegisteringListener {

    private static final EnumMap<Material, Stats> armorStats = new EnumMap<>(Material.class);
    private static final Set<Material> leatherArmor = new HashSet<>();
    private static final Set<Material> chainmailArmor = new HashSet<>();
    private static final Set<Material> goldenArmor = new HashSet<>();
    private static final Set<Material> diamondArmor = new HashSet<>();
    private static final Set<Material> diamondTools = new HashSet<>();
    private static final Set<Material> netheriteArmor = new HashSet<>();
    private static final Set<Material> netheriteTools = new HashSet<>();

    static {
        armorStats.put(Material.LEATHER_HELMET, new Stats(1, 0, 0));
        armorStats.put(Material.LEATHER_CHESTPLATE, new Stats(1, 0, 0));
        armorStats.put(Material.LEATHER_LEGGINGS, new Stats(1, 0, 0));
        armorStats.put(Material.LEATHER_BOOTS, new Stats(1, 0, 0));

        armorStats.put(Material.IRON_HELMET, new Stats(1, 1, 0.025));
        armorStats.put(Material.IRON_CHESTPLATE, new Stats(2, 1, 0.025));
        armorStats.put(Material.IRON_LEGGINGS, new Stats(2, 1, 0.025));
        armorStats.put(Material.IRON_BOOTS, new Stats(1, 1, 0.025));

        armorStats.put(Material.CHAINMAIL_HELMET, armorStats.get(Material.IRON_HELMET));
        armorStats.put(Material.CHAINMAIL_CHESTPLATE, armorStats.get(Material.IRON_CHESTPLATE));
        armorStats.put(Material.CHAINMAIL_LEGGINGS, armorStats.get(Material.IRON_LEGGINGS));
        armorStats.put(Material.CHAINMAIL_BOOTS, armorStats.get(Material.IRON_BOOTS));

        armorStats.put(Material.GOLDEN_HELMET, new Stats(2, 1, 0));
        armorStats.put(Material.GOLDEN_CHESTPLATE, new Stats(4, 1, 0));
        armorStats.put(Material.GOLDEN_LEGGINGS, new Stats(2, 1, 0));
        armorStats.put(Material.GOLDEN_BOOTS, new Stats(2, 1, 0));

        armorStats.put(Material.DIAMOND_HELMET, new Stats(2, 2, 0.05));
        armorStats.put(Material.DIAMOND_CHESTPLATE, new Stats(4, 5, 0.05));
        armorStats.put(Material.DIAMOND_LEGGINGS, new Stats(2, 4, 0.05));
        armorStats.put(Material.DIAMOND_BOOTS, new Stats(2, 1, 0.05));

        armorStats.put(Material.NETHERITE_HELMET, armorStats.get(Material.DIAMOND_HELMET));
        armorStats.put(Material.NETHERITE_CHESTPLATE, armorStats.get(Material.DIAMOND_CHESTPLATE));
        armorStats.put(Material.NETHERITE_LEGGINGS, armorStats.get(Material.DIAMOND_LEGGINGS));
        armorStats.put(Material.NETHERITE_BOOTS, armorStats.get(Material.DIAMOND_BOOTS));

        leatherArmor.add(Material.LEATHER_HELMET);
        leatherArmor.add(Material.LEATHER_CHESTPLATE);
        leatherArmor.add(Material.LEATHER_LEGGINGS);
        leatherArmor.add(Material.LEATHER_BOOTS);

        chainmailArmor.add(Material.CHAINMAIL_HELMET);
        chainmailArmor.add(Material.CHAINMAIL_CHESTPLATE);
        chainmailArmor.add(Material.CHAINMAIL_LEGGINGS);
        chainmailArmor.add(Material.CHAINMAIL_BOOTS);

        goldenArmor.add(Material.GOLDEN_HELMET);
        goldenArmor.add(Material.GOLDEN_CHESTPLATE);
        goldenArmor.add(Material.GOLDEN_LEGGINGS);
        goldenArmor.add(Material.GOLDEN_BOOTS);

        diamondArmor.add(Material.DIAMOND_HELMET);
        diamondArmor.add(Material.DIAMOND_CHESTPLATE);
        diamondArmor.add(Material.DIAMOND_LEGGINGS);
        diamondArmor.add(Material.DIAMOND_BOOTS);

        diamondTools.add(Material.DIAMOND_AXE);
        diamondTools.add(Material.DIAMOND_HOE);
        diamondTools.add(Material.DIAMOND_PICKAXE);
        diamondTools.add(Material.DIAMOND_SWORD);
        diamondTools.add(Material.DIAMOND_SHOVEL);

        netheriteArmor.add(Material.NETHERITE_HELMET);
        netheriteArmor.add(Material.NETHERITE_CHESTPLATE);
        netheriteArmor.add(Material.NETHERITE_LEGGINGS);
        netheriteArmor.add(Material.NETHERITE_BOOTS);

        netheriteTools.add(Material.NETHERITE_AXE);
        netheriteTools.add(Material.NETHERITE_HOE);
        netheriteTools.add(Material.NETHERITE_PICKAXE);
        netheriteTools.add(Material.NETHERITE_SWORD);
        netheriteTools.add(Material.NETHERITE_SHOVEL);
     }

    public AttributeManaging(@NotNull Plugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onInit(InitializationEvent event){
        if (event.getStep() == InitializationProcess.Step.BEFORE_VANILLA_MANAGER){
            for (Material item : armorStats.keySet()) {VanillaItemManager.getInstance().replaceVanillaMaterial(item);}
            for (Material item : diamondTools) {VanillaItemManager.getInstance().replaceVanillaMaterial(item);}
            /*for (Material item : Tag.ITEMS_SWORDS.getValues()) {VanillaItemManager.getInstance().replaceVanillaMaterial(item);}*/
            for (Material item : netheriteTools) {VanillaItemManager.getInstance().replaceVanillaMaterial(item);}
            VanillaItemManager.getInstance().replaceVanillaMaterial(Material.SPYGLASS);
            VanillaItemManager.getInstance().replaceVanillaMaterial(Material.BOW);
            VanillaItemManager.getInstance().replaceVanillaMaterial(Material.HEAVY_CORE);
            VanillaItemManager.getInstance().replaceVanillaMaterial(Material.TOTEM_OF_UNDYING);
            VanillaItemManager.getInstance().replaceVanillaMaterial(Material.ELYTRA);
        }
    }

    @EventHandler
    public void onItemGenerates(CustomItemGeneratedEvent event){
        ItemStack itemStack = event.getItemStack();

        if (!VanillaItemManager.isReplaced(itemStack))return;

        Material material = itemStack.getType();
        CustomItem customItem = event.getCustomItem();

        if (armorStats.containsKey(material)){applyDefaultArmorAttribute(itemStack, material);}

        if (leatherArmor.contains(material)) {
            itemStack.editMeta(Damageable.class, itemMeta -> itemMeta.setMaxDamage((int) (material.getMaxDurability() * 1.7)));
            event.setRepairData(new RepairData(Set.of(Items.FABRIC), Set.of(Material.LEATHER)));
        }

        if (chainmailArmor.contains(material)) {itemStack.editMeta(itemMeta -> itemMeta.setRarity(ItemRarity.COMMON));}


        if (goldenArmor.contains(material)){
            itemStack.editMeta(Damageable.class, itemMeta -> itemMeta.setMaxDamage((int) (material.getMaxDurability() * 15 / 7d * 0.9)));
            GoldenArmorPassive.applyPassive(material, customItem);
        }

        if (diamondArmor.contains(material)) {itemStack.editMeta(Damageable.class, itemMeta -> itemMeta.setMaxDamage(material.getMaxDurability() * 37 / 33));}

        if (diamondTools.contains(material)) {
            ItemStack netheriteTool = new ItemStack(Utils.replacePrefix(material, "netherite_"));

            itemStack.setData(DataComponentTypes.MAX_DAMAGE, Objects.requireNonNull(netheriteTool.
                    getData(DataComponentTypes.MAX_DAMAGE)));
            itemStack.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, Objects.requireNonNull(netheriteTool.
                    getData(DataComponentTypes.ATTRIBUTE_MODIFIERS)).showInTooltip(false));
            itemStack.setData(DataComponentTypes.TOOL, Objects.requireNonNull(netheriteTool.getData(DataComponentTypes.TOOL)));
        }

        if (netheriteArmor.contains(material) || netheriteTools.contains(material)){
            event.setRepairData(new RepairData(Material.DIAMOND, Material.NETHERITE_INGOT));
        }

        if (material == Material.BOW){
            event.setRepairData(new RepairData(Material.STRING));
        }

        /*if (Tag.ITEMS_SWORDS.getValues().contains(material)) {SwordDash.applyAbility(itemStack, customItem);}*/

        if (material == Material.SPYGLASS) {
            customItem.getComponents().set(new CustomItemAttributesComponent(new CustomAttributesContainer.Builder()
                    .add(Attributes.CAST_RANGE, 0.7, AttributeModifier.Operation.ADD_SCALAR, CustomEquipmentSlot.HAND)
                    .add(Attributes.CAST_RANGE, 0.3, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlots.ARTIFACTS)
                    .build()));
        }

        if (material == Material.HEAVY_CORE){
            customItem.getComponents().set(new CustomItemAttributesComponent(new CustomAttributesContainer.Builder()
                    .add(Attributes.CRITICAL_DAMAGE, 0.2, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlots.ARTIFACTS)
                    .build()));
        }

        if (material == Material.TOTEM_OF_UNDYING) {
            customItem.getComponents().set(new DeathProtectionPassive(Objects.requireNonNull(new ItemStack(Material.TOTEM_OF_UNDYING).getData(DataComponentTypes.DEATH_PROTECTION))));
            itemStack.setData(DataComponentTypes.MAX_STACK_SIZE, 64);
        }

        if (material == Material.ELYTRA) {
            customItem.getComponents().set(new ElytraActivator(CustomEquipmentSlot.CHEST, 15 * 20));
            customItem.getComponents().set(new GliderComponent(itemStack, 15 * 20));
        }
    }

    public static void applyDefaultArmorAttribute(@NotNull ItemStack target, @NotNull Material source) {
        applyDefaultArmorAttribute(target, source, true, true);
    }

    public static void applyDefaultArmorAttribute(@NotNull ItemStack target, @NotNull Material source, boolean addArmor, boolean addAttackDamage) {
        EquipmentSlotGroup slot = source.getEquipmentSlot().getGroup();
        AttributeUtils.appendAttribute(target, Attribute.MAX_HEALTH, new NamespacedKey(RpgU.getInstance(), "max_health_" + slot),
                armorStats.get(source).hp, AttributeModifier.Operation.ADD_NUMBER, slot);
        if (addArmor) {
            AttributeUtils.appendAttribute(target, Attribute.ARMOR, new NamespacedKey(RpgU.getInstance(), "base_armor_" + slot),
                    armorStats.get(source).armor, AttributeModifier.Operation.ADD_NUMBER, slot);
        }
        if (addAttackDamage) {
            AttributeUtils.appendAttribute(target, Attribute.ATTACK_DAMAGE, new NamespacedKey(RpgU.getInstance(), "base_attack_damage_" + slot),
                    armorStats.get(source).damage, AttributeModifier.Operation.ADD_SCALAR, slot);
        }
    }

    private record Stats(double hp, double armor, double damage){}
}

package me.udnek.rpgu.vanilla;

import com.destroystokyo.paper.event.player.PlayerReadyArrowEvent;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.PotionContents;
import me.udnek.coreu.custom.attribute.AttributeUtils;
import me.udnek.coreu.custom.attribute.CustomAttributesContainer;
import me.udnek.coreu.custom.component.instance.CustomAttributedItem;
import me.udnek.coreu.custom.equipment.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.event.CustomItemGeneratedEvent;
import me.udnek.coreu.custom.event.InitializationEvent;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.custom.item.RepairData;
import me.udnek.coreu.custom.item.VanillaItemManager;
import me.udnek.coreu.custom.registry.InitializationProcess;
import me.udnek.coreu.nms.Nms;
import me.udnek.coreu.rpgu.attribute.RPGUAttributes;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.coreu.util.ComponentU;
import me.udnek.coreu.util.SelfRegisteringListener;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.ability.instance.AllyBuffingAuraPassive;
import me.udnek.rpgu.component.ability.vanilla.*;
import me.udnek.rpgu.component.instance.ArrowComponent;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.util.Utils;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
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
        if (event.getStep() == InitializationProcess.Step.GLOBAL_INITIALIZATION){
            for (Material material : armorStats.keySet()) {VanillaItemManager.getInstance().replaceVanillaMaterial(material);}
            for (Material material : diamondTools) {VanillaItemManager.getInstance().replaceVanillaMaterial(material);}
            /*for (Material material : Tag.ITEMS_SWORDS.getValues()) {VanillaItemManager.getInstance().replaceVanillaMaterial(material);}*/
            for (Material material : netheriteTools) {VanillaItemManager.getInstance().replaceVanillaMaterial(material);}
            for (Material material : Tag.ITEMS_AXES.getValues()) {
                if (!VanillaItemManager.isDisabled(material)) VanillaItemManager.getInstance().replaceVanillaMaterial(material);
            }
            VanillaItemManager.getInstance().replaceVanillaMaterial(Material.SPYGLASS);
            VanillaItemManager.getInstance().replaceVanillaMaterial(Material.BOW);
            VanillaItemManager.getInstance().replaceVanillaMaterial(Material.MACE);
            VanillaItemManager.getInstance().replaceVanillaMaterial(Material.TRIDENT);
            VanillaItemManager.getInstance().replaceVanillaMaterial(Material.CROSSBOW);
            VanillaItemManager.getInstance().replaceVanillaMaterial(Material.SHIELD);
            VanillaItemManager.getInstance().replaceVanillaMaterial(Material.HEAVY_CORE);
            VanillaItemManager.getInstance().replaceVanillaMaterial(Material.TOTEM_OF_UNDYING);
            VanillaItemManager.getInstance().replaceVanillaMaterial(Material.ELYTRA);
            VanillaItemManager.getInstance().replaceVanillaMaterial(Material.ARROW);
            VanillaItemManager.getInstance().replaceVanillaMaterial(Material.SPECTRAL_ARROW);
            VanillaItemManager.getInstance().replaceVanillaMaterial(Material.TIPPED_ARROW);
            VanillaItemManager.getInstance().replaceVanillaMaterial(Material.FIREWORK_ROCKET);
        }
    }

    @EventHandler
    public void onItemGenerates(CustomItemGeneratedEvent event){
        ItemStack itemStack = event.getItemStack();

        if (!VanillaItemManager.isReplaced(itemStack)) return;

        Material material = itemStack.getType();
        CustomItem customItem = event.getCustomItem();

        if (armorStats.containsKey(material)){
            applyDefaultArmorAttribute(itemStack, material);
        }

        if (leatherArmor.contains(material)) {
            itemStack.setData(DataComponentTypes.MAX_DAMAGE, Objects.requireNonNull(itemStack.getData(DataComponentTypes.MAX_DAMAGE)) * 17 / 10);
            event.setRepairData(new RepairData(Set.of(Items.FABRIC, Items.WOLF_PELT), Set.of(Material.LEATHER)));
        }

        if (chainmailArmor.contains(material)) {itemStack.setData(DataComponentTypes.RARITY, ItemRarity.COMMON);}


        if (goldenArmor.contains(material)){
            ItemStack ironArmor = new ItemStack(Utils.replacePrefix(material, "iron_"));

            itemStack.setData(DataComponentTypes.ITEM_NAME, Component.translatable("item.rpgu.golden_armor"));
            itemStack.setData(DataComponentTypes.MAX_DAMAGE, Objects.requireNonNull(ironArmor.getData(DataComponentTypes.MAX_DAMAGE)) * 9 / 10);
            AllyBuffingAuraPassive.applyGoldenArmorBuff(material, customItem);
        }

        if (diamondArmor.contains(material)) {
            ItemStack netheriteArmor = new ItemStack(Utils.replacePrefix(material, "netherite_"));

            itemStack.setData(DataComponentTypes.RARITY, ItemRarity.UNCOMMON);
            itemStack.setData(DataComponentTypes.MAX_DAMAGE, Objects.requireNonNull(netheriteArmor.getData(DataComponentTypes.MAX_DAMAGE)));
        }

        if (diamondTools.contains(material)) {
            ItemStack netheriteTool = new ItemStack(Utils.replacePrefix(material, "netherite_"));

            itemStack.setData(DataComponentTypes.RARITY, ItemRarity.UNCOMMON);
            itemStack.setData(DataComponentTypes.MAX_DAMAGE, Objects.requireNonNull(netheriteTool.
                    getData(DataComponentTypes.MAX_DAMAGE)));
            itemStack.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, Objects.requireNonNull(netheriteTool.
                    getData(DataComponentTypes.ATTRIBUTE_MODIFIERS)));
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
            customItem.getComponents().set(new CustomAttributedItem(new CustomAttributesContainer.Builder()
                    .add(RPGUAttributes.ABILITY_CAST_RANGE, 0.7, AttributeModifier.Operation.ADD_SCALAR, CustomEquipmentSlot.HAND)
                    .add(RPGUAttributes.ABILITY_CAST_RANGE, 0.3, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlots.ARTIFACTS)
                    .build()));
        }

        if (material == Material.HEAVY_CORE){
            customItem.getComponents().set(new CustomAttributedItem(new CustomAttributesContainer.Builder()
                    .add(Attributes.CRITICAL_DAMAGE, 0.2, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlots.ARTIFACTS)
                    .build()));
        }

        if (material == Material.TOTEM_OF_UNDYING) {
            customItem.getComponents().getOrCreateDefault(RPGUComponents.PASSIVE_ABILITY_ITEM).getComponents()
                    .set(new DeathProtectionPassive(Objects.requireNonNull(Material.TOTEM_OF_UNDYING.getDefaultData(DataComponentTypes.DEATH_PROTECTION))));
            itemStack.setData(DataComponentTypes.MAX_STACK_SIZE, 64);
        }

        if (material == Material.ELYTRA) {
            customItem.getComponents().getOrCreateDefault(RPGUComponents.PASSIVE_ABILITY_ITEM).getComponents()
                            .set(GliderPassive.of(itemStack, 15*20));
        }

        if (material == Material.BOW) {
            customItem.getComponents().getOrCreateDefault(RPGUComponents.ACTIVE_ABILITY_ITEM).getComponents().set(new BowAbility());
        }

        if (material == Material.CROSSBOW) {
            customItem.getComponents().getOrCreateDefault(RPGUComponents.ACTIVE_ABILITY_ITEM).getComponents().set(new CrossbowAbility());
        }

        if (itemStack.hasData(DataComponentTypes.BLOCKS_ATTACKS)) {
            customItem.getComponents().getOrCreateDefault(RPGUComponents.ACTIVE_ABILITY_ITEM).getComponents().set(
                    new BlocksAttacksAbility(Objects.requireNonNull(itemStack.getData(DataComponentTypes.BLOCKS_ATTACKS))));
        }

        if (Tag.ITEMS_AXES.isTagged(material)) {
            if (!VanillaItemManager.isDisabled(itemStack)){
                customItem.getComponents().getOrCreateDefault(RPGUComponents.ACTIVE_ABILITY_ITEM).getComponents().set(new ShieldCrashingAbility());
            }
        }

        if (material ==  Material.MACE) {
            customItem.getComponents().getOrCreateDefault(RPGUComponents.ACTIVE_ABILITY_ITEM).getComponents().set(new MaceAbility());
        }

        if (material ==  Material.TRIDENT) {
            customItem.getComponents().getOrCreateDefault(RPGUComponents.ACTIVE_ABILITY_ITEM).getComponents().set(new TridentAbility());
        }

        if (material == Material.ARROW) {
            customItem.getComponents().set(new ArrowComponent() {
                @Override
                public @NotNull Component getIcon(@NotNull CustomItem customItem, @NotNull ItemStack itemStack) {
                    return Component.text("0").font(Key.key("rpgu:arrow")).color(NamedTextColor.WHITE)
                                    .decoration(TextDecoration.ITALIC, false);
                }
            });
        }

        if (material == Material.TIPPED_ARROW) {
            customItem.getComponents().set(new ArrowComponent() {
                @Override
                public void onBeingShoot(@NotNull CustomItem customItem, @NotNull ItemStack itemStack, @NotNull EntityShootBowEvent event) {
                    Arrow arrow = (Arrow) event.getProjectile();
                    PotionContents dataFirst = itemStack.getData(DataComponentTypes.POTION_CONTENTS);
                    if (dataFirst != null) {
                        arrow.setBasePotionType(dataFirst.potion());
                        if (dataFirst.customColor() != null) arrow.setColor(dataFirst.customColor());
                    }
                }
                @Override
                public @NotNull Component getIcon(@NotNull CustomItem customItem, @NotNull ItemStack itemStack) {
                    TextColor color = TextColor.color(Nms.get().getColorByEffects(Objects.requireNonNull(Objects.requireNonNull(
                            itemStack.getData(DataComponentTypes.POTION_CONTENTS)).potion()).getPotionEffects()).orElse(0));
                    return ComponentU.textWithNoSpace(-1, Component.text("3").color(color),16)
                            .append(Component.text("2").color(TextColor.color(-1)))
                            .decoration(TextDecoration.ITALIC, false).font(Key.key("rpgu:arrow"));
                }
            });
        }

        if (material == Material.SPECTRAL_ARROW) {
            customItem.getComponents().set(new ArrowComponent() {
                @Override
                public @NotNull Component getIcon(@NotNull CustomItem customItem, @NotNull ItemStack itemStack) {
                    return Component.text("1").font(Key.key("rpgu:arrow")).decoration(TextDecoration.ITALIC, false)
                            .color(TextColor.color(-1));
                }
            });
        }

        if (material == Material.FIREWORK_ROCKET) {
            customItem.getComponents().set(new ArrowComponent() {
                @Override
                public @NotNull ChoseArrowResult onChooseArrow(@NotNull CustomItem customItem, @NotNull PlayerReadyArrowEvent event) {
                    return event.getBow().getType() != Material.CROSSBOW ? ChoseArrowResult.DENY : ChoseArrowResult.ALLOW;
                }

                @Override
                public @NotNull Component getIcon(@NotNull CustomItem customItem, @NotNull ItemStack itemStack) {
                    return Component.text("4").font(Key.key("rpgu:arrow")).decoration(TextDecoration.ITALIC, false)
                            .color(TextColor.color(-1));
                }
            });
        }
    }

    public static void applyDefaultArmorAttribute(@NotNull ItemStack target, @NotNull Material source) {
        applyDefaultArmorAttribute(target, source, true, true);
    }

    public static void applyDefaultArmorAttribute(@NotNull ItemStack target, @NotNull Material source, boolean addArmor, boolean addAttackDamage) {
        target.unsetData(DataComponentTypes.ATTRIBUTE_MODIFIERS);
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

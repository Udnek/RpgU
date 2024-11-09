package me.udnek.rpgu.item.equipment.hungry_horror_armor;

import me.udnek.itemscoreu.customattribute.AttributeUtils;
import me.udnek.itemscoreu.customattribute.CustomAttribute;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.LoreBuilder;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.component.ArtifactComponent;
import me.udnek.rpgu.lore.AttributesLorePart;
import me.udnek.rpgu.mechanic.damaging.DamageInstance;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HungryHorrorHelmet extends ConstructableCustomItem implements HungryHorrorArmorProperties{
    @Override
    public @NotNull String getRawId() {return "hungry_horror_helmet";}

    @Override
    public @NotNull Material getMaterial() {return Material.DIAMOND_HELMET;}

    @Override
    public @Nullable LoreBuilder getLoreBuilder() {
        LoreBuilder loreBuilder = new LoreBuilder();
        AttributesLorePart attributesLorePart = new AttributesLorePart();
        loreBuilder.set(LoreBuilder.Position.ATTRIBUTES, attributesLorePart);
        attributesLorePart.addAttribute(CustomEquipmentSlot.HEAD, Component.translatable(getRawItemName()+".description.0").color(CustomAttribute.PLUS_COLOR));
        attributesLorePart.addAttribute(CustomEquipmentSlot.HEAD, Component.translatable(getRawItemName()+".description.1").color(NamedTextColor.GRAY));
        attributesLorePart.addAttribute(CustomEquipmentSlot.HEAD, Component.translatable(getRawItemName()+".description.2").color(NamedTextColor.GRAY));
        attributesLorePart.addAttribute(CustomEquipmentSlot.HEAD, Component.translatable(getRawItemName()+".description.3").color(NamedTextColor.GRAY));
        attributesLorePart.addAttribute(CustomEquipmentSlot.HEAD, Component.translatable(getRawItemName()+".description.4").color(NamedTextColor.GRAY));
        return loreBuilder;
    }

    @Override
    protected void modifyFinalItemMeta(@NotNull ItemMeta itemMeta) {
        super.modifyFinalItemMeta(itemMeta);
        AttributeUtils.appendAttribute(itemMeta, Attribute.MAX_HEALTH, new NamespacedKey(RpgU.getInstance(), "base_max_health_helmet"), 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD);
        itemMeta.removeAttributeModifier(Attribute.ARMOR);
        AttributeUtils.addAttribute(itemMeta, Attribute.ARMOR, new NamespacedKey(RpgU.getInstance(), "base_armor_helmet"), 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD);
        itemMeta.removeAttributeModifier(Attribute.ARMOR_TOUGHNESS);
        AttributeUtils.appendAttribute(itemMeta, Attribute.ATTACK_DAMAGE, new NamespacedKey(RpgU.getInstance(), "attack_damage_"), -0.5, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlotGroup.HEAD);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        setComponent(new HungryHorrorHelmetComponent());
    }

    public static class  HungryHorrorHelmetComponent implements ArtifactComponent{
        @Override
        public boolean isAppropriateSlot(@NotNull CustomEquipmentSlot slot) {
            return CustomEquipmentSlot.HEAD.test(slot);
        }

        @Override
        public void onPlayerAttacksWhenEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot, @NotNull DamageInstance damageInstance) {
            if (!damageInstance.isCritical()) return;

            PotionEffect potionEffect = player.getPotionEffect(PotionEffectType.STRENGTH);
            int applied;
            if (potionEffect == null) {applied = -1;}
            else { applied = potionEffect.getAmplifier();}
            player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 40, Math.min(applied+1, 4), false, true));
        }
    }
}
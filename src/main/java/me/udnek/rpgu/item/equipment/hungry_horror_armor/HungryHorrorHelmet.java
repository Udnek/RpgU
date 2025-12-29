package me.udnek.rpgu.item.equipment.hungry_horror_armor;

import io.papermc.paper.datacomponent.item.ItemAttributeModifiers;
import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.equipment.slot.CustomEquipmentSlot;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.rpgu.RpgU;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HungryHorrorHelmet extends HungryHorrorArmor {
    @Override
    public @NotNull String getRawId() {return "hungry_horror_helmet";}

    @Override
    public @NotNull Material getMaterial() {return Material.DIAMOND_HELMET;}

    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Mask of Hungry Horror", "Маска Голодного Ужаса");
    }

    @Override
    public @Nullable DataSupplier<ItemAttributeModifiers> getAttributeModifiers() {
        AttributeModifier modifierHealth = new AttributeModifier(new NamespacedKey(RpgU.getInstance(), "base_max_health_helmet"), 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD);
        AttributeModifier modifierArmor = new AttributeModifier(new NamespacedKey(RpgU.getInstance(), "base_armor_helmet"), 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD);
        AttributeModifier modifierAttack = new AttributeModifier(new NamespacedKey(RpgU.getInstance(), "attack_damage_helmet"), -0.5, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlotGroup.HEAD);
        ItemAttributeModifiers build = ItemAttributeModifiers.itemAttributes().addModifier(Attribute.MAX_HEALTH, modifierHealth)
                .addModifier(Attribute.ARMOR, modifierArmor).addModifier(Attribute.ATTACK_DAMAGE, modifierAttack).build();
        return DataSupplier.of(build);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        getComponents().getOrCreateDefault(RPGUComponents.PASSIVE_ABILITY_ITEM).getComponents()
                .set(new Passive(PotionEffectType.STRENGTH, CustomEquipmentSlot.HEAD));
    }
}
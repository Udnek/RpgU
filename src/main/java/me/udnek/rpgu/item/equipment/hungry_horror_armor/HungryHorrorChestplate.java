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

public class HungryHorrorChestplate extends HungryHorrorArmor {
    @Override
    public @NotNull Material getMaterial() {
        return Material.DIAMOND_CHESTPLATE;
    }

    @Override
    public @NotNull String getRawId() {
        return "hungry_horror_chestplate";
    }

    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Aegis of Hungry Horror", "Эгида Голодного Ужаса");
    }

    @Override
    public @Nullable DataSupplier<ItemAttributeModifiers> getAttributeModifiers() {
        AttributeModifier modifier = new AttributeModifier(new NamespacedKey(RpgU.getInstance(), "base_armor_helmet"), 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST);
        ItemAttributeModifiers build = ItemAttributeModifiers.itemAttributes().addModifier(Attribute.ARMOR, modifier).build();
        return DataSupplier.of(build);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        getComponents().getOrCreateDefault(RPGUComponents.PASSIVE_ABILITY_ITEM).getComponents()
                .set(new Passive(PotionEffectType.ABSORPTION, CustomEquipmentSlot.CHEST));
    }
}

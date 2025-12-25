package me.udnek.rpgu.item.equipment.hungry_horror_armor;

import io.papermc.paper.datacomponent.item.Equippable;
import me.udnek.coreu.custom.attribute.CustomAttribute;
import me.udnek.coreu.custom.equipmentslot.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.util.LoreBuilder;
import me.udnek.jeiu.component.HiddenItemComponent;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.lore.AttributesLorePart;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class HungryHorrorArmor extends ConstructableCustomItem {

    @Override
    public @Nullable List<ItemFlag> getTooltipHides() {return List.of(ItemFlag.HIDE_ATTRIBUTES);}

    @Override
    public @Nullable DataSupplier<Equippable> getEquippable() {
        Equippable build = Equippable.equippable(getMaterial().getEquipmentSlot()).assetId(new NamespacedKey(RpgU.getInstance(), "hungry_horror")).build();
        return DataSupplier.of(build);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(HiddenItemComponent.INSTANCE);
    }

    public @Nullable LoreBuilder getLoreBuilder(CustomEquipmentSlot slot) {
        LoreBuilder loreBuilder = new LoreBuilder();
        AttributesLorePart attributesLorePart = new AttributesLorePart();
        loreBuilder.set(LoreBuilder.Position.ATTRIBUTES, attributesLorePart);
        attributesLorePart.addAttribute(slot, Component.translatable(translationKey()+".description.0").color(CustomAttribute.PLUS_COLOR));
        attributesLorePart.addAttribute(slot, Component.translatable(translationKey()+".description.1").color(NamedTextColor.GRAY));
        attributesLorePart.addAttribute(slot, Component.translatable(translationKey()+".description.2").color(NamedTextColor.GRAY));
        attributesLorePart.addAttribute(slot, Component.translatable(translationKey()+".description.3").color(NamedTextColor.GRAY));
        attributesLorePart.addAttribute(slot, Component.translatable(translationKey()+".description.4").color(NamedTextColor.GRAY));
        return loreBuilder;
    }
}

package me.udnek.rpgu.item.artifact;

import me.udnek.itemscoreu.customattribute.CustomAttribute;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.LoreBuilder;
import me.udnek.rpgu.component.ArtifactComponent;
import me.udnek.rpgu.effect.Effects;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.lore.AttributesLorePart;
import me.udnek.rpgu.mechanic.damaging.DamageInstance;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ArcaneAccumulator extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "arcane_accumulator";}

    @Override
    public @NotNull Material getMaterial() {return Material.GUNPOWDER;}

    @Override
    public @Nullable LoreBuilder getLoreBuilder() {
        LoreBuilder loreBuilder = new LoreBuilder();
        AttributesLorePart attributesLorePart = new AttributesLorePart();
        loreBuilder.set(LoreBuilder.Position.ATTRIBUTES, attributesLorePart);
        attributesLorePart.addAttribute(EquipmentSlots.ARTIFACTS, Component.translatable(getRawItemName()+".description.0").color(CustomAttribute.PLUS_COLOR));
        attributesLorePart.addAttribute(EquipmentSlots.ARTIFACTS, Component.translatable(getRawItemName()+".description.1").color(CustomAttribute.PLUS_COLOR));
        return loreBuilder;
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        getComponents().set(new ArcaneAccumulatorComponent());
    }

    public static class ArcaneAccumulatorComponent implements ArtifactComponent {

        @Override
        public void onPlayerAttacksWhenEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot, @NotNull DamageInstance damageInstance) {
            if (damageInstance.getDamage().getMagical() == 0) return;
            int level = Effects.BONUS_AREA_OF_EFFECT.getAppliedLevel(player);
            if (level <= 7) {level += 1;}

            Effects.BONUS_AREA_OF_EFFECT.applyInvisible(player, 10 * 20, level);
        }
    }
}

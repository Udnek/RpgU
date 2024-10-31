package me.udnek.rpgu.item.artifact;

import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customcomponent.instance.CustomItemAttributesComponent;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.LoreBuilder;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.ArtifactComponent;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.item.RpgUCustomItem;
import me.udnek.rpgu.lore.AttributeLoreGenerator;
import me.udnek.rpgu.lore.AttributesLorePart;
import me.udnek.rpgu.mechanic.damaging.DamageEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class NautilusCore extends ConstructableCustomItem implements RpgUCustomItem {

    @Override
    public Integer getCustomModelData() {return 3104;}
    @Override
    public @NotNull Material getMaterial() {return Material.GUNPOWDER;}
    @Override
    public @NotNull String getRawId() {return "nautilus_core";}

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape("PPP","PNP","PPP");

        recipe.setIngredient('P', Material.PRISMARINE_SHARD);
        recipe.setIngredient('N', Material.NAUTILUS_SHELL);

        consumer.accept(recipe);
    }

    @Override
    public @Nullable LoreBuilder getLoreBuilder() {
        LoreBuilder loreBuilder = new LoreBuilder();
        AttributesLorePart attributesLorePart = new AttributesLorePart();
        loreBuilder.set(LoreBuilder.Position.ATTRIBUTES, attributesLorePart);
        attributesLorePart.addAttribute(EquipmentSlots.ARTIFACTS, Component.translatable(getRawItemName()+".description.0").color(AttributeLoreGenerator.PLUS_ATTRIBUTE_COLOR));
        return loreBuilder;
    }

    @Override
    public void initializeComponents() {
        setComponent(new NautilusCoreComponent());
        setComponent(new CustomItemAttributesComponent(new CustomAttributesContainer.Builder()
                .add(Attributes.MAGICAL_POTENTIAL, 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlots.ARTIFACTS)
                .add(Attributes.MELEE_MAGICAL_DAMAGE_MULTIPLIER, 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlots.ARTIFACTS)
                .build()
        ));
    }

    public static class NautilusCoreComponent implements ArtifactComponent {

        @Override
        public void onPlayerAttacksWhenEquipped(@NotNull CustomItem item, @NotNull Player player, me.udnek.itemscoreu.customequipmentslot.@NotNull CustomEquipmentSlot slot, @NotNull DamageEvent event) {
            if (!event.isCritical()) return;
            if (event.containsExtraFlag(new isMagicalCriticalApplied())) return;

            event.getDamage().multiplyMagical(1.5);
            event.addExtraFlag(new isMagicalCriticalApplied());
        }
    }


    private static class isMagicalCriticalApplied extends DamageEvent.ExtraFlag{}
}



















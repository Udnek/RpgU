package me.udnek.rpgu.item.utility;

import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.component.ability.passive.ConstructablePassiveAbilityComponent;
import me.udnek.rpgu.equipment.slot.UniversalInventorySlot;
import me.udnek.rpgu.lore.ability.PassiveAbilityLorePart;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TotemOfSavingItem extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "totem_of_saving";}

   /* @Override
    public void getLore(@NotNull Consumer<Component> consumer) {
        consumer.accept(Component.translatable(translationKey() + ".description.0").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GRAY));
        consumer.accept(Component.translatable(translationKey() + ".description.1").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GRAY));
    }*/

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem().add());
        recipe.shape(
                " P ",
                "LCL");

        RecipeChoice.MaterialChoice lapis = new RecipeChoice.MaterialChoice(Material.LAPIS_LAZULI);
        RecipeChoice.MaterialChoice chest = new RecipeChoice.MaterialChoice(Material.CHEST);
        RecipeChoice.MaterialChoice pumpkin = new RecipeChoice.MaterialChoice(Material.CARVED_PUMPKIN);
        recipe.setIngredient('L', lapis);
        recipe.setIngredient('C', chest);
        recipe.setIngredient('P', pumpkin);

        consumer.accept(recipe);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        getComponents().set(new TotemOfSavingLore());
    }

    public class TotemOfSavingLore extends ConstructablePassiveAbilityComponent<Object> {

        @Override
        public @NotNull CustomEquipmentSlot getSlot() {
            return CustomEquipmentSlot.DUMB_INVENTORY;
        }

        @Override
        public void addLoreLines(@NotNull PassiveAbilityLorePart componentable) {
            componentable.addAbilityDescription(Component.translatable(translationKey() + ".description.0"));
            super.addLoreLines(componentable);
        }

        @Override
        public @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot slot, @NotNull Object object) {
            return ActionResult.NO_COOLDOWN;
        }
    }
}

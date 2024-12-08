package me.udnek.rpgu.item.equipment;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.itemscoreu.customattribute.AttributeUtils;
import me.udnek.itemscoreu.customattribute.CustomAttributeModifier;
import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customcomponent.instance.CustomItemAttributesComponent;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.EquippableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class PhantomChestplate extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "phantom_chestplate";}

    @Override
    public @NotNull Material getMaterial() {return Material.DIAMOND_CHESTPLATE;}

    @Override
    public @Nullable Integer getMaxDamage() {return 150;}

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                " M ",
                "WSW");

        RecipeChoice.MaterialChoice membrane = new RecipeChoice.MaterialChoice(Material.PHANTOM_MEMBRANE);
        RecipeChoice.MaterialChoice string = new RecipeChoice.MaterialChoice(Material.STRING);
        RecipeChoice.ExactChoice wing = new RecipeChoice.ExactChoice(Items.PHANTOM_WING.getItem());
        recipe.setIngredient('M', membrane);
        recipe.setIngredient('S', string);
        recipe.setIngredient('W', wing);

        consumer.accept(recipe);
    }

    @Override
    public ItemFlag[] getTooltipHides() {return new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES};}

    @Override
    public @Nullable EquippableComponent getEquippable() {
        EquippableComponent equippable = new ItemStack(getMaterial()).getItemMeta().getEquippable();
        equippable.setSlot(getMaterial().getEquipmentSlot());
        equippable.setModel(new NamespacedKey(RpgU.getInstance(), "phantom"));
        return equippable;
    }

    @Override
    public void initializeAttributes(@NotNull ItemMeta itemMeta) {
        super.initializeAttributes(itemMeta);
        AttributeUtils.addAttribute(itemMeta, Attribute.MAX_HEALTH, new NamespacedKey(RpgU.getInstance(), "max_health_chestplate"), 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        CustomAttributeModifier attributeModifier = new CustomAttributeModifier(2, AttributeModifier.Operation.ADD_NUMBER, CustomEquipmentSlot.CHEST);
        getComponents().set(new CustomItemAttributesComponent(new CustomAttributesContainer.Builder().add(Attributes.MAGICAL_POTENTIAL, attributeModifier).build()));
    }
}


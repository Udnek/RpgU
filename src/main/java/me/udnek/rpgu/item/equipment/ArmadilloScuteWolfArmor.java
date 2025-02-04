package me.udnek.rpgu.item.equipment;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Equippable;
import me.udnek.itemscoreu.customattribute.AttributeUtils;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.rpgu.RpgU;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class ArmadilloScuteWolfArmor extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "armadillo_scute_wolf_armor";}

    @Override
    public void initializeAdditionalAttributes(@NotNull ItemStack itemStack) {
        super.initializeAdditionalAttributes(itemStack);
        AttributeUtils.appendAttribute(itemStack, Attribute.ARMOR, new NamespacedKey(RpgU.getInstance(), "base_armor_" + getRawId()),
                11, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.BODY);
    }

    @Override
    public @Nullable List<ItemFlag> getTooltipHides() {return List.of(ItemFlag.HIDE_ATTRIBUTES);}

    @Override
    public @Nullable DataSupplier<Integer> getMaxDamage() {return DataSupplier.of(Material.WOLF_ARMOR.getDefaultData(DataComponentTypes.MAX_DAMAGE));}

    @Override
    public @Nullable DataSupplier<Integer> getMaxStackSize() {
        return DataSupplier.of(null);
    }

    @Override
    public @Nullable DataSupplier<Equippable> getEquippable() {
        Equippable build = Equippable.equippable(Material.WOLF_ARMOR.getEquipmentSlot()).assetId(new NamespacedKey(NamespacedKey.MINECRAFT, "armadillo_scute")).build();
        return DataSupplier.of(build);
    }

    @Override
    public @Nullable String getRawItemName() {return Material.WOLF_ARMOR.translationKey();}
    
    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                "S  ",
                "SSS",
                "S S");

        recipe.setIngredient('S', new RecipeChoice.MaterialChoice(Material.ARMADILLO_SCUTE));

        consumer.accept(recipe);
    }
}

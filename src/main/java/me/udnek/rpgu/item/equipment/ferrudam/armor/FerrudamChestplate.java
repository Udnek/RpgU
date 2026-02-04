package me.udnek.rpgu.item.equipment.ferrudam.armor;

import me.udnek.coreu.custom.attribute.AttributeUtils;
import me.udnek.coreu.custom.recipe.choice.CustomCompatibleRecipeChoice;
import me.udnek.coreu.custom.recipe.choice.CustomSingleRecipeChoice;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.mechanic.machine.alloying.AlloyingRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class FerrudamChestplate extends FerrudamArmor {

    public FerrudamChestplate() {
        super(Material.DIAMOND_CHESTPLATE, "ferrudam_chestplate", "Ferrudam Chestplate", "Ферродамовый нагрудник");
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {
        var ingot = new CustomSingleRecipeChoice(Items.FERRUDAM_INGOT);

        AlloyingRecipe recipeAlloy = new AlloyingRecipe(
                getNewRecipeKey(),
                List.of(new CustomSingleRecipeChoice(Items.FERRUDAM_UPGRADE)),
                new CustomCompatibleRecipeChoice(Set.of(), Tag.ITEMS_COALS.getValues()),
                List.of(ingot, ingot, ingot, ingot),
                new CustomSingleRecipeChoice(Material.IRON_CHESTPLATE),
                getItem()
        );

        consumer.accept(recipeAlloy);
    }

    @Override
    protected void modifyFinalItemStack(@NotNull ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);
        AttributeUtils.appendAttribute(itemStack, Attribute.MAX_HEALTH, new NamespacedKey(RpgU.getInstance(), "base_max_health_chestplate"), 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST);
        AttributeUtils.removeAttribute(itemStack, Attribute.ARMOR);
        AttributeUtils.addAttribute(itemStack, Attribute.ARMOR, new NamespacedKey(RpgU.getInstance(), "base_armor_chestplate"), 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST);
        AttributeUtils.removeAttribute(itemStack, Attribute.ARMOR_TOUGHNESS);
    }
}

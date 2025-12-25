package me.udnek.rpgu.item.equipment;

import me.udnek.coreu.custom.attribute.AttributeUtils;
import me.udnek.coreu.custom.attribute.CustomAttributesContainer;
import me.udnek.coreu.custom.attribute.VanillaAttributesContainer;
import me.udnek.coreu.custom.component.instance.CustomItemAttributesComponent;
import me.udnek.coreu.custom.component.instance.VanillaAttributesComponent;
import me.udnek.coreu.custom.equipmentslot.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.RepairData;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.attribute.RpgUAttributeUtils;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class CeremonialDagger extends ConstructableCustomItem {

    public static final double BACKSTAB_DAMAGE_MULTIPLIER = 2;
    public static final double BACKSTAB_DAMAGE_MULTIPLIER_ARTIFACT =  0.75;

    @Override
    public @NotNull Material getMaterial() {
        return Material.DIAMOND_SWORD;
    }
    @Override
    public @NotNull String getRawId() {
        return "ceremonial_dagger";
    }
    @Override
    public @Nullable List<ItemFlag> getTooltipHides() {return List.of(ItemFlag.HIDE_ATTRIBUTES);}

    @Override
    public boolean addDefaultAttributes() {return true;}

    @Override
    public void initializeAdditionalAttributes(@NotNull ItemStack itemStack) {
        super.initializeAdditionalAttributes(itemStack);
        AttributeUtils.appendAttribute(itemStack, Attribute.SNEAKING_SPEED,
                new NamespacedKey(RpgU.getInstance(), getRawId()+"_sneak"), 2, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.MAINHAND);
    }

    @Override
    protected void modifyFinalItemStack(@NotNull ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);
        RpgUAttributeUtils.addSuitableAttribute(itemStack, Attribute.ATTACK_DAMAGE, null, -2);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(new CustomItemAttributesComponent(new CustomAttributesContainer.Builder()
                .add(Attributes.BACKSTAB_DAMAGE, BACKSTAB_DAMAGE_MULTIPLIER, AttributeModifier.Operation.ADD_SCALAR, CustomEquipmentSlot.MAIN_HAND)
                .add(Attributes.BACKSTAB_DAMAGE, BACKSTAB_DAMAGE_MULTIPLIER_ARTIFACT, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlots.ARTIFACTS)
                .build()));
        getComponents().set(new VanillaAttributesComponent(new VanillaAttributesContainer.Builder()
                .add(Attribute.SNEAKING_SPEED, new NamespacedKey(RpgU.getInstance(), getRawId()+"_sneak_artifact"),
                        0.5, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlots.ARTIFACTS)
                .build()));
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                "DSG",
                "GSD",
                " T ");

        recipe.setIngredient('D', new RecipeChoice.MaterialChoice(Material.DIAMOND));
        recipe.setIngredient('G', new RecipeChoice.MaterialChoice(Material.GOLD_INGOT));
        recipe.setIngredient('S', new RecipeChoice.MaterialChoice(Tag.ITEMS_STONE_TOOL_MATERIALS));
        recipe.setIngredient('T', new RecipeChoice.MaterialChoice(Material.STICK));

        consumer.accept(recipe);
    }

    @Override
    public @Nullable RepairData initializeRepairData() {
        Set<@NotNull Material> materials = new HashSet<>(Set.of(Material.GOLD_INGOT, Material.DIAMOND));
        materials.addAll(Tag.ITEMS_STONE_TOOL_MATERIALS.getValues());
        return new RepairData(Set.of(), materials);
    }
}





























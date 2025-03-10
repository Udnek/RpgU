package me.udnek.rpgu.item.equipment;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Equippable;
import io.papermc.paper.datacomponent.item.ItemAttributeModifiers;
import me.udnek.itemscoreu.customattribute.CustomAttributeModifier;
import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customcomponent.instance.CustomItemAttributesComponent;
import me.udnek.itemscoreu.customequipmentslot.slot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.RepairData;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class PhantomChestplate extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "phantom_chestplate";}

    @Override
    public @NotNull Material getMaterial() {return Material.DIAMOND_CHESTPLATE;}

    @Override
    public @Nullable DataSupplier<Integer> getMaxDamage() {return DataSupplier.of(150);}

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
    public @Nullable List<ItemFlag> getTooltipHides() {return List.of(ItemFlag.HIDE_ATTRIBUTES);}

    @Override
    public @Nullable DataSupplier<Equippable> getEquippable() {
        Equippable equippable = new ItemStack(getMaterial()).getData(DataComponentTypes.EQUIPPABLE);
        equippable = equippable.toBuilder().assetId(new NamespacedKey(RpgU.getInstance(), "phantom")).build();
        return DataSupplier.of(equippable);
    }

    @Override
    public @Nullable DataSupplier<ItemAttributeModifiers> getAttributeModifiers() {
        AttributeModifier modifier = new AttributeModifier(new NamespacedKey(RpgU.getInstance(), "max_health_chestplate"), 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST);
        ItemAttributeModifiers modifiers = ItemAttributeModifiers.itemAttributes().addModifier(Attribute.MAX_HEALTH, modifier).build();
        return DataSupplier.of(modifiers);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        CustomAttributeModifier attributeModifier = new CustomAttributeModifier(2, AttributeModifier.Operation.ADD_NUMBER, CustomEquipmentSlot.CHEST);
        getComponents().set(new CustomItemAttributesComponent(new CustomAttributesContainer.Builder().add(Attributes.MAGICAL_POTENTIAL, attributeModifier).build()));
    }

    @Override
    public @Nullable RepairData initializeRepairData() {
        return new RepairData(Set.of(Items.PHANTOM_WING), Set.of(Material.PHANTOM_MEMBRANE));
    }
}


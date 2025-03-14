package me.udnek.rpgu.item.equipment.doloire;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.itemscoreu.customattribute.AttributeUtils;
import me.udnek.itemscoreu.customattribute.CustomAttributeModifier;
import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customcomponent.instance.CustomItemAttributesComponent;
import me.udnek.itemscoreu.customequipmentslot.slot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.RepairData;
import me.udnek.itemscoreu.customrecipe.choice.CustomSingleRecipeChoice;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.mechanic.alloying.AlloyingRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class HeavyAmethystDoloire extends AmethystDoloire {

    @Override
    public @NotNull String getRawId() {
        return "heavy_amethyst_doloire";
    }

    @Override
    public @Nullable DataSupplier<ItemRarity> getRarity() {return DataSupplier.of(ItemRarity.EPIC);}

    @Override
    public void initializeAdditionalAttributes(@NotNull ItemStack itemStack) {
        super.initializeAdditionalAttributes(itemStack);
        AttributeUtils.appendAttribute(itemStack, Attribute.ATTACK_DAMAGE, null, 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);
        AttributeUtils.appendAttribute(itemStack, Attribute.ATTACK_SPEED, null, -0.2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);

    }

    @Override
    public void initializeComponents() {
        CustomAttributeModifier attribute = new CustomAttributeModifier(0.6,  AttributeModifier.Operation.ADD_NUMBER, CustomEquipmentSlot.MAIN_HAND);
        getComponents().set(new CustomItemAttributesComponent(new CustomAttributesContainer.Builder().add(Attributes.MELEE_MAGICAL_DAMAGE_MULTIPLIER, attribute).build()));

        GreatAmethystSwordComponent swordComponent = new GreatAmethystSwordComponent();
        swordComponent.getComponents().set(new AttributeBasedProperty(20*7, ComponentTypes.ABILITY_COOLDOWN));
        getComponents().set(swordComponent);
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        AlloyingRecipe recipe = new AlloyingRecipe(new NamespacedKey(
                RpgU.getInstance(),
                getRawId()),
                List.of(new CustomSingleRecipeChoice(Material.HEAVY_CORE)),
                new CustomSingleRecipeChoice(Items.BLAST_COAL),
                new CustomSingleRecipeChoice(Items.AMETHYST_DOLOIRE),
                getItem()
        );
        consumer.accept(recipe);
    }

    @Override
    public @Nullable DataSupplier<Integer> getMaxDamage() {
        return DataSupplier.of(Material.DIAMOND_SWORD.getDefaultData(DataComponentTypes.MAX_DAMAGE));
    }

    @Override
    public @Nullable RepairData initializeRepairData() {
        return new RepairData(Material.HEAVY_CORE);
    }
}
package me.udnek.rpgu.item.equipment.doloire;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.coreu.custom.attribute.AttributeUtils;
import me.udnek.coreu.custom.attribute.CustomAttributeModifier;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.component.instance.AutoGeneratingFilesItem;
import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.equipment.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.item.RepairData;
import me.udnek.coreu.custom.recipe.choice.CustomSingleRecipeChoice;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.coreu.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.mechanic.machine.alloying.AlloyingRecipe;
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
    public @NotNull String getRawId() {return "heavy_amethyst_doloire";}

    @Override
    public @Nullable DataSupplier<ItemRarity> getRarity() {return DataSupplier.of(ItemRarity.EPIC);}

    @Override
    public @Nullable TranslatableThing getTranslations() {return TranslatableThing.ofEngAndRu("Heavy Amethyst Doloire", "Тяжёлый аметистовый долуар");}

    @Override
    protected void modifyFinalItemStack(@NotNull ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);
        AttributeUtils.appendAttribute(itemStack, Attribute.ATTACK_DAMAGE, null, 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);
        //AttributeUtils.appendAttribute(itemStack, Attribute.ATTACK_SPEED, null, -0.2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);
    }

    @Override
    public void initializeComponents() {
        getComponents().set(AutoGeneratingFilesItem.HANDHELD_20X20);

        CustomAttributeModifier attribute = new CustomAttributeModifier(0.6,  AttributeModifier.Operation.ADD_NUMBER, CustomEquipmentSlot.MAIN_HAND);
        getComponents().getOrCreateDefault(CustomComponentType.CUSTOM_ATTRIBUTED_ITEM).addAttribute(
                Attributes.MELEE_MAGICAL_DAMAGE_MULTIPLIER, attribute
        );

        Ability ability = new Ability();
        ability.getComponents().set(new AttributeBasedProperty(20*7, RPGUComponents.ABILITY_COOLDOWN_TIME));
        getComponents().getOrCreateDefault(RPGUComponents.ACTIVE_ABILITY_ITEM).getComponents().set(ability);
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {
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
package me.udnek.rpgu.item.artifact.wreath;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.CustomModelData;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.rpgu.component.ability.instance.FlowerWreathPassive;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.function.Consumer;

public class FlowerWreath extends ConstructableCustomItem {

    private final EnumMap<Material, Integer> flowerColors = new EnumMap<>(Material.class);

    public FlowerWreath(){
        put(Material.DANDELION, TextColor.color(255, 236, 79));
        put(Material.POPPY, TextColor.color(237,48,44));
        put(Material.BLUE_ORCHID, TextColor.color(42,191,253));
        put(Material.ALLIUM, TextColor.color(210,166,246));
        put(Material.AZURE_BLUET, TextColor.color(250,255,148));
        put(Material.RED_TULIP, TextColor.color(237, 48, 44));
        put(Material.ORANGE_TULIP, TextColor.color(241,157,37));
        put(Material.WHITE_TULIP, TextColor.color(247,247,247));
        put(Material.PINK_TULIP, TextColor.color(246,226,255));
        put(Material.OXEYE_DAISY,  TextColor.color(255, 242, 143));
        put(Material.CORNFLOWER,  TextColor.color(70,106,235));
        put(Material.LILY_OF_THE_VALLEY,  TextColor.color(228,228,228));
        put(Material.PINK_PETALS, TextColor.color(246,161,212));
        put(Material.SUNFLOWER,  TextColor.color(245,186,39));
    }

    protected void put(@NotNull Material material, @NotNull TextColor color){
        flowerColors.put(material, color.value());
    }

    @Override
    public @NotNull String getRawId() {return "flower_wreath";}

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                "AAA",
                "A A",
                "AAA");

        RecipeChoice.MaterialChoice choices = new RecipeChoice.MaterialChoice(new ArrayList<>(flowerColors.keySet()));
        recipe.setIngredient('A', choices);

        consumer.accept(recipe);
    }

    @Override
    public @Nullable DataSupplier<CustomModelData> getCustomModelData() {
        return DataSupplier.of(CustomModelData.customModelData().addColor(Color.fromRGB(flowerColors.get(Material.DANDELION))).build());
    }

    @Override
    public ItemStack getItemFromCraftingMatrix(ItemStack result, ItemStack[] matrix, @NotNull Recipe recipe) {
        Color finalColor = Color.fromRGB(255, 255, 255);
        Color[] colors = new Color[8];
        int i = 0;
        for (ItemStack flower : matrix) {
            if (flower != null){
                colors[i] = getColorByFlower(flower.getType());
                i++;
            }
        }
        setColor(result, finalColor.mixColors(colors));
        return result;
    }

    public void setColor(@NotNull ItemStack itemStack, @NotNull Color color){
        itemStack.setData(DataComponentTypes.CUSTOM_MODEL_DATA, CustomModelData.customModelData().addColor(color).build());
    }

    public int getIntColorByFlower(@NotNull Material flower){
        return flowerColors.getOrDefault(flower, 0);
    }

    public @NotNull Color getColorByFlower(@NotNull Material flower){
        return Color.fromRGB(getIntColorByFlower(flower));
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().getOrCreateDefault(RPGUComponents.PASSIVE_ABILITY_ITEM).getComponents().set(FlowerWreathPassive.DEFAULT);
    }
}



















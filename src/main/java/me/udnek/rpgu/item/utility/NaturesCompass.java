package me.udnek.rpgu.item.utility;

import com.google.common.base.Function;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.LodestoneTracker;
import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.component.instance.AutoGeneratingFilesItem;
import me.udnek.coreu.custom.component.instance.RightClickableItem;
import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.equipment.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.equipment.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.RPGUActiveItem;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.coreu.rpgu.component.ability.active.RPGUConstructableActiveAbility;
import me.udnek.coreu.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.component.ability.Abilities;
import me.udnek.rpgu.component.ability.RPGUActiveTriggerableAbility;
import me.udnek.rpgu.item.Items;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.util.TriConsumer;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.util.BiomeSearchResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class NaturesCompass extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {
        return "natures_compass";
    }

    @Override
    public @NotNull Material getMaterial() {
        return Material.COMPASS;
    }

    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Nature's Compass", "Природный компас");
    }

    @Override
    public @Nullable DataSupplier<Boolean> getEnchantmentGlintOverride() {
        return DataSupplier.of(Boolean.FALSE);
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                "LML",
                "SCS",
                "LWL");

        recipe.setIngredient('L', new RecipeChoice.MaterialChoice(Tag.ITEMS_LEAVES));
        recipe.setIngredient('S', new RecipeChoice.MaterialChoice(Tag.ITEMS_SAPLINGS));
        recipe.setIngredient('M', new RecipeChoice.ExactChoice(Items.WEAK_MAGIC_CORE.getItem()));
        recipe.setIngredient('C', new RecipeChoice.MaterialChoice(Material.COMPASS));
        recipe.setIngredient('W', new RecipeChoice.MaterialChoice(Tag.ITEMS_LOGS));

        consumer.accept(recipe);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(new VanillaBehaviourDenier());
        getComponents().set(AutoGeneratingFilesItem.COMPASS_TWO_LAYERS);
        getComponents().getOrCreateDefault(RPGUComponents.ACTIVE_ABILITY_ITEM).getComponents().set(Ability.DEFAULT);
    }

    public static class VanillaBehaviourDenier implements RightClickableItem{

        @Override
        public void onRightClick(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event) {
            Block clickedBlock = event.getClickedBlock();
            if (clickedBlock == null) return;
            if (clickedBlock.getType() != Material.LODESTONE) return;
            event.setCancelled(true);
        }
    }

    public static class Ability extends RPGUConstructableActiveAbility<PlayerInteractEvent> implements RPGUActiveTriggerableAbility<PlayerInteractEvent> {

        public static final int SEARCH_RADIUS = 2000;

        public static final Ability DEFAULT = new Ability();

        public Ability(){
            getComponents().set(new AttributeBasedProperty(20, RPGUComponents.ABILITY_COOLDOWN_TIME));
        }

        @Override
        protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity entity, @NotNull UniversalInventorySlot slot, @NotNull PlayerInteractEvent event) {
            slot.modifyItem(new Function<ItemStack, ItemStack>() {
                @Override
                public ItemStack apply(ItemStack stack) {
                    if (stack == null) return null;
                    Instant start = Instant.now();
                    @Nullable BiomeSearchResult result = entity.getWorld()
                            .locateNearestBiome(entity.getLocation(), SEARCH_RADIUS, Biome.PALE_GARDEN);

                    RpgU.getInstance().getLogger().info("Nature's Compass search took: " + Duration.between(start, Instant.now()).toMillis());
                    RpgU.getInstance().getLogger().info("Nature's Compass result: " + result);

                    LodestoneTracker tracker;
                    Component customName = Objects.requireNonNull(stack.getData(DataComponentTypes.ITEM_NAME))
                            .decoration(TextDecoration.ITALIC, false);

                    if (result == null){
                        // MAKES COMPASS SPIN
                        tracker = LodestoneTracker.lodestoneTracker().build();
                    } else {
                        tracker = LodestoneTracker.lodestoneTracker(result.getLocation(), false);

                        customName = customName
                                .append(Component.text(" ("))
                                .append(Component.translatable(result.getBiome().translationKey()))
                                .append(Component.text(")"));
                    }
                    stack.setData(DataComponentTypes.LODESTONE_TRACKER, tracker);
                    stack.setData(DataComponentTypes.CUSTOM_NAME, customName);
                    return stack;
                }
            }, entity);
            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public void onRightClick(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event) {
            assert event.getHand() != null;
            activate(customItem, event.getPlayer(), new BaseUniversalSlot(event.getHand()), event);
        }

        @Override
        public void getEngAndRuProperties(TriConsumer<@NotNull String, @NotNull String, @NotNull List<Component>> Eng_Ru_Args) {
            super.getEngAndRuProperties(Eng_Ru_Args);
            Eng_Ru_Args.accept("Search Radius: %s blocks", "Радиус поиска: % блоков", List.of(Component.text(SEARCH_RADIUS)));
        }

        @Override
        public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
            return Pair.of(List.of("Searches for nearest specified biome"), List.of("Ищет указанный ближайщий биом"));
        }

        @Override
        public @NotNull CustomComponentType<? super RPGUActiveItem, ? extends CustomComponent<? super RPGUActiveItem>> getType() {
            return Abilities.NATURES_COMPASS;
        }
    }
}

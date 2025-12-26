package me.udnek.rpgu.item.utility;

import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.equipmentslot.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.equipmentslot.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.equipmentslot.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.coreu.rpgu.component.RPGUPassiveItem;
import me.udnek.coreu.rpgu.component.ability.passive.RPGUConstructablePassiveAbility;
import me.udnek.rpgu.component.ability.Abilities;
import me.udnek.rpgu.entity.EntityTypes;
import me.udnek.rpgu.entity.totem_of_saving.TotemOfSavingEntity;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class TotemOfSavingItem extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "totem_of_saving";}

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

        getComponents().getOrCreateDefault(RPGUComponents.PASSIVE_ABILITY_ITEM).getComponents().set(Passive.DEFAULT);
    }


    public static class Passive extends RPGUConstructablePassiveAbility<PlayerDeathEvent> {

        public static final Passive DEFAULT = new Passive();

        @Override
        protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot universalInventorySlot, @NotNull PlayerDeathEvent event) {
            Player player = event.getPlayer();
            ItemStack foundTotem = null;
            List<ItemStack> drops = event.getDrops();
            for (ItemStack itemStack : drops) {
                if (!(customItem.isThisItem(itemStack))) continue;
                foundTotem = itemStack;
                break;
            }
            if (foundTotem == null) return ActionResult.NO_COOLDOWN;
            drops.remove(foundTotem);
            foundTotem.subtract(1);
            if (foundTotem.getAmount() > 0) drops.add(foundTotem);
            Location location = player.getLocation();
            TotemOfSavingEntity totem = EntityTypes.TOTEM_OF_SAVING.spawnAndGet(location);
            totem.setItems(drops);
            drops.clear();

            if (location.getY() > location.getWorld().getMinHeight()) return ActionResult.NO_COOLDOWN;

            location.setY(location.getWorld().getMinHeight());
            location.setPitch(0);
            totem.getReal().teleport(location);
            Objects.requireNonNull(totem.getReal().getAttribute(Attribute.GRAVITY)).setBaseValue(0);
            totem.getReal().addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20*10, 0));
            return ActionResult.NO_COOLDOWN;
        }


        public void onDeath(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, @NotNull PlayerDeathEvent event) {
            activate(customItem, event.getPlayer(), slot, event);
        }

        @Override
        public @NotNull CustomEquipmentSlot getSlot() {
            return CustomEquipmentSlot.DUMB_INVENTORY;
        }

        @Override
        public void tick(@NotNull CustomItem customItem, @NotNull Player player, @NotNull BaseUniversalSlot baseUniversalSlot, int i) {

        }

        @Override
        public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
            return null;
        }

        @Override
        public @NotNull CustomComponentType<? super RPGUPassiveItem, ? extends CustomComponent<? super RPGUPassiveItem>> getType() {
            return Abilities.TOTEM_OF_SAVING;
        }

    }
}

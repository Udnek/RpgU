package me.udnek.rpgu.item.utility;

import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customequipmentslot.SingleSlot;
import me.udnek.itemscoreu.customequipmentslot.UniversalInventorySlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.Either;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.passive.ConstructablePassiveAbility;
import me.udnek.rpgu.entity.EntityTypes;
import me.udnek.rpgu.entity.TotemOfSavingEntity;
import me.udnek.rpgu.lore.ability.PassiveAbilityLorePart;
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

        getComponents().getOrCreateDefault(ComponentTypes.EQUIPPABLE_ITEM).addPassive(new TotemOfSavingComponent());
    }


    public class TotemOfSavingComponent extends ConstructablePassiveAbility<PlayerDeathEvent> {

        @Override
        public @NotNull CustomEquipmentSlot getSlot() {
            return CustomEquipmentSlot.DUMB_INVENTORY;
        }


        @Override
        public void addLoreLines(@NotNull PassiveAbilityLorePart componentable) {
            componentable.addFullAbilityDescription(TotemOfSavingItem.this, 1);
            super.addLoreLines(componentable);
        }

        @Override
        public @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull Either<UniversalInventorySlot, SingleSlot> slot, @NotNull PlayerDeathEvent event) {
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

        @Override
        public void onDeath(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, @NotNull PlayerDeathEvent event) {
            activate(customItem, event.getPlayer(), new Either<>(slot, null), event);
        }
    }
}

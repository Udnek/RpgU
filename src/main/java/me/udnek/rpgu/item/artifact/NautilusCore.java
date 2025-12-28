package me.udnek.rpgu.item.artifact;

import me.udnek.coreu.custom.attribute.CustomAttributesContainer;
import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.component.instance.CustomAttributedItem;
import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.equipment.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.equipment.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.equipment.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.coreu.rpgu.component.RPGUPassiveItem;
import me.udnek.coreu.rpgu.component.ability.passive.RPGUConstructablePassiveAbility;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.ability.Abilities;
import me.udnek.rpgu.component.ability.RPGUPassiveTriggerableAbility;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.mechanic.damaging.DamageEvent;
import me.udnek.rpgu.mechanic.damaging.DamageInstance;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class NautilusCore extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "nautilus_core";}

    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Nautilus Core", "Ядро наутилуса");
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape("PPP","PNP","PPP");

        recipe.setIngredient('P', Material.PRISMARINE_SHARD);
        recipe.setIngredient('N', Material.NAUTILUS_SHELL);

        consumer.accept(recipe);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().getOrCreateDefault(RPGUComponents.PASSIVE_ABILITY_ITEM).getComponents().set(Passive.DEFAULT);
        getComponents().set(new CustomAttributedItem(new CustomAttributesContainer.Builder()
                .add(Attributes.MELEE_MAGICAL_DAMAGE_MULTIPLIER, 0.15, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlots.ARTIFACTS)
                .build()
        ));
    }

    public static class Passive extends RPGUConstructablePassiveAbility<DamageEvent> implements RPGUPassiveTriggerableAbility<DamageEvent> {

        public static final Passive DEFAULT = new Passive();

        @Override
        public @NotNull CustomComponentType<? super RPGUPassiveItem, ? extends CustomComponent<? super RPGUPassiveItem>> getType() {
            return Abilities.NAUTILUS_CORE;
        }



        @Override
        protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot universalInventorySlot, @NonNull DamageEvent damageEvent) {
            DamageInstance damageInstance = damageEvent.getDamageInstance();
            if (!(damageInstance.getDamager() instanceof LivingEntity damager)) return ActionResult.NO_COOLDOWN;
            if (!damageInstance.isCritical()) return ActionResult.NO_COOLDOWN;
            if (damageInstance.containsExtraFlag(new isMagicalCriticalApplied())) return ActionResult.NO_COOLDOWN;

            damageInstance.getDamage().multiplyMagical(Attributes.CRITICAL_DAMAGE.calculate(damager));
            damageInstance.addExtraFlag(new isMagicalCriticalApplied());
            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public void onDamageDealt(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, @NotNull DamageEvent event) {
            activate(customItem, (LivingEntity) Objects.requireNonNull(event.getDamageInstance().getDamager()), slot, event);
        }

        @Override
        public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
            return Pair.of(List.of("Critical hits apply on Magical Damage"), List.of("Критические удары применяются к магическому урону"));
        }

        @Override
        public @NotNull CustomEquipmentSlot getSlot() {
            return EquipmentSlots.ARTIFACTS;
        }

        @Override
        public void tick(@NotNull CustomItem customItem, @NotNull Player player, @NotNull BaseUniversalSlot baseUniversalSlot, int i) {}

        private static class isMagicalCriticalApplied extends DamageInstance.ExtraFlag{}
    }

}



















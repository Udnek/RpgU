package me.udnek.rpgu.item.artifact.wreath;

import me.udnek.coreu.custom.attribute.CustomAttributeModifier;
import me.udnek.coreu.custom.attribute.CustomAttributesContainer;
import me.udnek.coreu.custom.attribute.CustomKeyedAttributeModifier;
import me.udnek.coreu.custom.attribute.VanillaAttributesContainer;
import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.component.instance.CustomAttributedItem;
import me.udnek.coreu.custom.component.instance.VanillaAttributedItem;
import me.udnek.coreu.custom.equipmentslot.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.equipmentslot.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.equipmentslot.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.coreu.rpgu.component.RPGUPassiveItem;
import me.udnek.coreu.rpgu.component.ability.passive.RPGUConstructablePassiveAbility;
import me.udnek.coreu.rpgu.component.ability.property.EffectsProperty;
import me.udnek.coreu.rpgu.component.ability.property.function.PropertyFunctions;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.ability.Abilities;
import me.udnek.rpgu.component.ability.RPGUPassiveTriggerableAbility;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.mechanic.damaging.DamageEvent;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class WitherWreath extends ConstructableCustomItem {

    @Override
    public @NotNull String getRawId() {return "wither_wreath";}

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                "WWW",
                "W W",
                "WWW");

        RecipeChoice.MaterialChoice wither = new RecipeChoice.MaterialChoice(Material.WITHER_ROSE);
        recipe.setIngredient('W', wither);

        consumer.accept(recipe);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        CustomKeyedAttributeModifier attributeDamage = new CustomKeyedAttributeModifier(new NamespacedKey(RpgU.getInstance(), "attack_damage_"
                + getRawId()), -0.3, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlots.ARTIFACTS);
        CustomKeyedAttributeModifier attributeHealth = new CustomKeyedAttributeModifier(new NamespacedKey(RpgU.getInstance(), "max_health_"
                + getRawId()), -2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlots.ARTIFACTS);
        getComponents().set(new VanillaAttributedItem(new VanillaAttributesContainer.Builder().add(Attribute.ATTACK_DAMAGE, attributeDamage)
                .add(Attribute.MAX_HEALTH, attributeHealth).build()));

        CustomAttributeModifier attribute = new CustomAttributeModifier(5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlots.ARTIFACTS);
        getComponents().set(new CustomAttributedItem(new CustomAttributesContainer.Builder().add(Attributes.MAGICAL_POTENTIAL, attribute).build()));

        getComponents().getOrCreateDefault(RPGUComponents.PASSIVE_ABILITY_ITEM).getComponents().set(Passive.DEFAULT);
    }

    public static class Passive extends RPGUConstructablePassiveAbility<DamageEvent> implements RPGUPassiveTriggerableAbility<DamageEvent> {

        public static final Passive DEFAULT = new Passive();

        public Passive(){
            getComponents().set(new EffectsProperty(new EffectsProperty.PotionData(
                    PotionEffectType.WITHER,
                    PropertyFunctions.CEIL(PropertyFunctions.ATTRIBUTE_WITH_BASE(Attributes.ABILITY_DURATION, 20*2.5)),
                    PropertyFunctions.CONSTANT(1)
            )));
        }

        @Override
        public @NotNull CustomEquipmentSlot getSlot() {
            return EquipmentSlots.ARTIFACTS;
        }

        @Override
        public void tick(@NotNull CustomItem customItem, @NotNull Player player, @NotNull BaseUniversalSlot baseUniversalSlot, int i) {}

        @Override
        protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot universalInventorySlot, @NotNull DamageEvent damageEvent) {
            if (!(damageEvent.getDamageInstance().getVictim() instanceof LivingEntity livingVictim)) return ActionResult.NO_COOLDOWN;
            getComponents().getOrException(RPGUComponents.ABILITY_EFFECTS).applyOn(livingEntity, livingVictim);
            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public void onDamageDealt(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, @NotNull DamageEvent event) {
            activate(customItem, (LivingEntity) Objects.requireNonNull(event.getDamageInstance().getDamager()), slot, event);
        }

        @Override
        public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
            return null;
        }

        @Override
        public @NotNull CustomComponentType<? super RPGUPassiveItem, ? extends CustomComponent<? super RPGUPassiveItem>> getType() {
            return Abilities.WITHER_WREATH;
        }
    }
}





























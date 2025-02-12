package me.udnek.rpgu.item.artifact.wreath;

import me.udnek.itemscoreu.customattribute.CustomAttributeModifier;
import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customattribute.CustomKeyedAttributeModifier;
import me.udnek.itemscoreu.customattribute.VanillaAttributesContainer;
import me.udnek.itemscoreu.customcomponent.instance.CustomItemAttributesComponent;
import me.udnek.itemscoreu.customcomponent.instance.VanillaAttributesComponent;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customequipmentslot.SingleSlot;
import me.udnek.itemscoreu.customequipmentslot.UniversalInventorySlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.ArtifactComponent;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.passive.ConstructablePassiveAbilityComponent;
import me.udnek.rpgu.component.ability.property.EffectsProperty;
import me.udnek.rpgu.component.ability.property.function.Functions;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.lore.ability.PassiveAbilityLorePart;
import me.udnek.rpgu.mechanic.damaging.DamageInstance;
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

        CustomKeyedAttributeModifier attributeDamage = new CustomKeyedAttributeModifier(new NamespacedKey(RpgU.getInstance(), "attack_damage_" + getRawId()), -0.3, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlots.ARTIFACTS);
        CustomKeyedAttributeModifier attributeHealth = new CustomKeyedAttributeModifier(new NamespacedKey(RpgU.getInstance(), "max_health_" + getRawId()), -2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlots.ARTIFACTS);
        getComponents().set(new VanillaAttributesComponent(new VanillaAttributesContainer.Builder().add(Attribute.ATTACK_DAMAGE, attributeDamage).add(Attribute.MAX_HEALTH, attributeHealth).build()));

        CustomAttributeModifier attribute = new CustomAttributeModifier(5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlots.ARTIFACTS);
        getComponents().set(new CustomItemAttributesComponent(new CustomAttributesContainer.Builder().add(Attributes.MAGICAL_POTENTIAL, attribute).build()));

        getComponents().set(new Artifact());
        getComponents().set(new Passive());
    }

    public class Passive extends ConstructablePassiveAbilityComponent<DamageInstance>{

        public Passive(){
            getComponents().set(new EffectsProperty(new EffectsProperty.PotionData(
                    PotionEffectType.WITHER,
                    Functions.CEIL(Functions.ATTRIBUTE(Attributes.ABILITY_DURATION, 20*2.5)),
                    Functions.CONSTANT(1)
            )));
        }

        @Override
        public void addLoreLines(@NotNull PassiveAbilityLorePart componentable) {
            componentable.addFullAbilityDescription(WitherWreath.this, 1);
            super.addLoreLines(componentable);
        }

        @Override
        public @NotNull CustomEquipmentSlot getSlot() {
            return EquipmentSlots.ARTIFACTS;
        }

        @Override
        public @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot slot,
                                            @NotNull DamageInstance damageInstance) {
            if (!(damageInstance.getVictim() instanceof LivingEntity livingVictim)) return ActionResult.NO_COOLDOWN;
            getComponents().getOrException(ComponentTypes.ABILITY_EFFECTS).applyOn(livingEntity, livingVictim);
            return ActionResult.FULL_COOLDOWN;
        }
    }

    public static class Artifact implements ArtifactComponent {
        @Override
        public void onPlayerAttacksWhenEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull SingleSlot slot, @NotNull DamageInstance damageInstance) {
            if (item.getComponents().getOrException(ComponentTypes.PASSIVE_ABILITY_ITEM) instanceof Passive passive){
                passive.action(item, player, new UniversalInventorySlot(slot.getSlot(player)), damageInstance);
            }
        }
    }
}





























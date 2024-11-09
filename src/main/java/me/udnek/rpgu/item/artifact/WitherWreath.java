package me.udnek.rpgu.item.artifact;

import me.udnek.itemscoreu.customattribute.CustomAttributeModifier;
import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customcomponent.instance.CustomItemAttributesComponent;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.ArtifactComponent;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.item.RpgUCustomItem;
import me.udnek.rpgu.mechanic.damaging.DamageInstance;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class WitherWreath extends ConstructableCustomItem implements RpgUCustomItem {

    @Override
    public @NotNull Material getMaterial() {return Material.GUNPOWDER;}

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

        CustomAttributeModifier attribute = new CustomAttributeModifier(6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlots.ARTIFACTS);
        setComponent(new CustomItemAttributesComponent(new CustomAttributesContainer.Builder().add(Attributes.MAGICAL_POTENTIAL, attribute).build()));

        setComponent(new WitherWreathComponent());
    }

    public static class WitherWreathComponent implements ArtifactComponent {
        @Override
        public void onPlayerAttacksWhenEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot, @NotNull DamageInstance event) {
            Entity victim = event.getVictim();

            if (!(victim instanceof LivingEntity livingEntity)) return;
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20*2,1));
        }
    }
}





























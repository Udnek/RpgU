package me.udnek.rpgu.item.equipment;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.itemscoreu.customattribute.CustomAttributeModifier;
import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customcomponent.instance.CustomItemAttributesComponent;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.item.RpgUCustomItem;
import me.udnek.rpgu.particle.BowParticles;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;



public class PhantomBow extends ConstructableCustomItem implements RpgUCustomItem, Listener {
    @Override
    public @NotNull Material getMaterial() {return Material.BOW;}
    @Override
    public @NotNull String getRawId() {return "phantom_bow";}

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                " WS",
                "M S",
                " WS");

        recipe.setIngredient('W', new RecipeChoice.ExactChoice(Items.PHANTOM_WING.getItem()));
        recipe.setIngredient('S', new RecipeChoice.MaterialChoice(Material.STRING));
        recipe.setIngredient('M', new RecipeChoice.MaterialChoice(Material.PHANTOM_MEMBRANE));

        consumer.accept(recipe);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        CustomAttributeModifier projectileSpeedAttribute = new CustomAttributeModifier(0.5, AttributeModifier.Operation.ADD_SCALAR, CustomEquipmentSlot.HAND);
        CustomAttributeModifier projectileDamageMultiplierAttribute = new CustomAttributeModifier(-0.25, AttributeModifier.Operation.ADD_SCALAR, CustomEquipmentSlot.HAND);

        setComponent(new CustomItemAttributesComponent(new CustomAttributesContainer.Builder().add(Attributes.PROJECTILE_SPEED, projectileSpeedAttribute).
                add(Attributes.PROJECTILE_DAMAGE_MULTIPLIER, projectileDamageMultiplierAttribute).build()));
    }

    @EventHandler
    public void onFire(EntityShootBowEvent event) {
        if (!isThisItem(event.getBow())) return;
        if (!(event.getProjectile() instanceof AbstractArrow arrow)) return;

        ParticleBuilder particleBuilder = new ParticleBuilder(Particle.ASH);
        particleBuilder.count(7);
        particleBuilder.extra(0);
        particleBuilder.offset(0.3, 0.3, 0.3);

        BowParticles.playParticleUntilGround(arrow, particleBuilder);
    }
}

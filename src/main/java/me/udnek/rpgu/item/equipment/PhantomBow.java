package me.udnek.rpgu.item.equipment;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.coreu.custom.attribute.CustomAttributeModifier;
import me.udnek.coreu.custom.attribute.CustomAttributesContainer;
import me.udnek.coreu.custom.component.instance.AutoGeneratingFilesItem;
import me.udnek.coreu.custom.component.instance.CustomAttributedItem;
import me.udnek.coreu.custom.equipmentslot.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.RepairData;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.particle.ParticleUtils;
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
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.Consumer;

public class PhantomBow extends ConstructableCustomItem implements Listener {

    public static final double SPEED_BUFF = 0.25;

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
        getComponents().set(AutoGeneratingFilesItem.BOW_20X20);
        CustomAttributeModifier projectileSpeedAttribute = new CustomAttributeModifier(SPEED_BUFF, AttributeModifier.Operation.ADD_SCALAR, CustomEquipmentSlot.ACTIVE_HAND);
        CustomAttributeModifier projectileDamageMultiplierAttribute = new CustomAttributeModifier(1/(1+SPEED_BUFF)-1, AttributeModifier.Operation.ADD_SCALAR, CustomEquipmentSlot.ACTIVE_HAND);

        getComponents().set(new CustomAttributedItem(new CustomAttributesContainer.Builder()
                .add(Attributes.PROJECTILE_SPEED, projectileSpeedAttribute)
                .add(Attributes.PROJECTILE_DAMAGE, projectileDamageMultiplierAttribute).build()));
    }

    @EventHandler
    public void onFire(EntityShootBowEvent event) {
        if (!isThisItem(event.getBow())) return;
        if (!(event.getProjectile() instanceof AbstractArrow arrow)) return;

        ParticleBuilder particleBuilder = new ParticleBuilder(Particle.ASH);
        particleBuilder.count(7);
        particleBuilder.extra(0);
        particleBuilder.offset(0.3, 0.3, 0.3);

        ParticleUtils.playUntilGround(arrow, particleBuilder);
    }

    @Override
    public @Nullable RepairData initializeRepairData() {
        return new RepairData(Set.of(Items.PHANTOM_WING), Set.of(Material.PHANTOM_MEMBRANE));
    }
}

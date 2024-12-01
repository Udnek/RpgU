package me.udnek.rpgu.item.equipment;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.itemscoreu.customattribute.CustomAttributeModifier;
import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customcomponent.instance.CustomItemAttributesComponent;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.nms.ConsumableAnimation;
import me.udnek.itemscoreu.nms.ConsumableComponent;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.attribute.RpgUAttributeUtils;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.ConstructableActiveAbilityComponent;
import me.udnek.rpgu.component.ability.RayTraceActiveAbility;
import me.udnek.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.component.ability.property.DamageProperty;
import me.udnek.rpgu.mechanic.damaging.Damage;
import me.udnek.rpgu.mechanic.damaging.DamageUtils;
import me.udnek.rpgu.mechanic.damaging.formula.MPBasedDamageFormula;
import me.udnek.rpgu.particle.AmethystSpikeParticle;
import me.udnek.rpgu.util.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.Consumer;

public class GreatAmethystSword extends ConstructableCustomItem {

    public static int CAST_TIME = (int) (0.75 * 20);
    private static final double MELEE_MAGICAL_DAMAGE_MULTIPLIER = 0.4;

    @Override
    public @NotNull Material getMaterial() {
        return Material.STONE_SWORD;
    }

    @Override
    public @NotNull String getRawId() {
        return "great_amethyst_sword";
    }

    @Override
    public ItemFlag[] getTooltipHides() {
        return new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES};
    }

    @Override
    public boolean getAddDefaultAttributes() {return true;}

    @Override
    public @Nullable NamespacedKey getItemModel() {
        return null;
    }

    @Override
    protected void modifyFinalItemStack(@NotNull ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);
        RpgUAttributeUtils.addSuitableAttribute(itemStack, Attribute.ATTACK_DAMAGE, null, 1);
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                " A ",
                "AСA",
                " B ");

        recipe.setIngredient('A', new RecipeChoice.MaterialChoice(Material.AMETHYST_SHARD));
        recipe.setIngredient('B', new RecipeChoice.MaterialChoice(Material.BONE));
        recipe.setIngredient('С', new RecipeChoice.MaterialChoice(Material.GUNPOWDER));

        consumer.accept(recipe);
    }

    @Override
    public @Nullable ConsumableComponent getConsumable() {
        ConsumableComponent component = new ConsumableComponent();
        component.setConsumeTicks(CAST_TIME);
        component.setAnimation(ConsumableAnimation.SPEAR);
        component.setHasConsumeParticles(false);
        component.setSound(null);
        return component;
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        CustomAttributeModifier attribute = new CustomAttributeModifier(MELEE_MAGICAL_DAMAGE_MULTIPLIER,  AttributeModifier.Operation.ADD_NUMBER, CustomEquipmentSlot.MAIN_HAND);
        getComponents().set(new CustomItemAttributesComponent(new CustomAttributesContainer.Builder().add(Attributes.MELEE_MAGICAL_DAMAGE_MULTIPLIER, attribute).build()));

        getComponents().set(new GreatAmethystSwordComponent());
    }

    public static class GreatAmethystSwordComponent extends ConstructableActiveAbilityComponent<PlayerItemConsumeEvent> implements RayTraceActiveAbility<PlayerItemConsumeEvent> {

        public static double BASE_RADIUS = 0.5;
        public static double BASE_DAMAGE = 1.5;

        public GreatAmethystSwordComponent() {
            getComponents().set(AttributeBasedProperty.from(20, ComponentTypes.ABILITY_COOLDOWN));
            getComponents().set(AttributeBasedProperty.from(15, ComponentTypes.ABILITY_CAST_RANGE));
            getComponents().set(AttributeBasedProperty.from(BASE_RADIUS, ComponentTypes.ABILITY_AREA_OF_EFFECT));
            getComponents().set(new DamageProperty(MPBasedDamageFormula.linearMageOnly(BASE_DAMAGE, 0.2)));
        }

        @Override
        public @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull Player player, @NotNull PlayerItemConsumeEvent playerItemConsumeEvent) {
            Location location = player.getLocation();
            Vector direction = location.getDirection();
            direction.setY(0).normalize();
            double aoe = getComponents().getOrException(ComponentTypes.ABILITY_AREA_OF_EFFECT).get(player);
            double castRange = getComponents().getOrException(ComponentTypes.ABILITY_CAST_RANGE).get(player);

            new BukkitRunnable(){
                int count = 0;
                @Override
                public void run() {
                    location.add(direction);
                    new AmethystSpikeParticle((float) aoe*2).play(location);
                    showRadius(new ParticleBuilder(Particle.SMALL_GUST).location(location), aoe, 5);
                    Collection<LivingEntity> nearbyLivingEntities = location.getNearbyLivingEntities(aoe);


                    for (LivingEntity livingEntity : nearbyLivingEntities){
                        System.out.println(livingEntity);
                        Damage damage = getComponents().getOrException(ComponentTypes.ABILITY_DAMAGE).get(Attributes.MAGICAL_POTENTIAL.calculate(livingEntity));
                        DamageUtils.damage(livingEntity, damage, player);

                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 2 * 20, 0));
                    }

                    count++;
                    if (1 + 2 * aoe * count > castRange) cancel();
                }
            }.runTaskTimer(RpgU.getInstance(), 0, 2);


            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public void onConsume(@NotNull CustomItem customItem, @NotNull PlayerItemConsumeEvent event) {
            event.setCancelled(true);
            action(customItem, event.getPlayer(), event);
        }
    }
}
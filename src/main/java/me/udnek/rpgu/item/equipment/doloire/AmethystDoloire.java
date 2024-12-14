package me.udnek.rpgu.item.equipment.doloire;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.itemscoreu.customattribute.AttributeUtils;
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
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.active.ConstructableActiveAbilityComponent;
import me.udnek.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.component.ability.property.DamageProperty;
import me.udnek.rpgu.component.ability.property.EffectsProperty;
import me.udnek.rpgu.component.ability.property.function.AttributeFunction;
import me.udnek.rpgu.component.ability.property.function.Functions;
import me.udnek.rpgu.effect.Effects;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.lore.ability.ActiveAbilityLorePart;
import me.udnek.rpgu.mechanic.damaging.Damage;
import me.udnek.rpgu.mechanic.damaging.DamageUtils;
import me.udnek.rpgu.component.ability.property.function.MPBasedDamageFunction;
import me.udnek.rpgu.particle.AmethystSpikeParticle;
import me.udnek.rpgu.particle.ParticleUtils;
import me.udnek.rpgu.util.Utils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class AmethystDoloire extends ConstructableCustomItem {

    public static int CAST_TIME = (int) (0.75 * 20);
    public static final double MELEE_MAGICAL_DAMAGE_MULTIPLIER = 0.4;

    @Override
    public @NotNull Material getMaterial() {
        return Material.STONE_SWORD;
    }
    @Override
    public @NotNull String getRawId() {return "amethyst_doloire";}
    @Override
    public ItemFlag[] getTooltipHides() {
        return new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES};
    }

    @Override
    public @Nullable ItemRarity getRarity() {return ItemRarity.UNCOMMON;}

    @Override
    public boolean getAddDefaultAttributes() {return true;}

    @Override
    public void initializeAttributes(@NotNull ItemMeta itemMeta) {
        super.initializeAttributes(itemMeta);
        AttributeUtils.appendAttribute(itemMeta, Attribute.ATTACK_DAMAGE, null, 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);
        AttributeUtils.appendAttribute(itemMeta, Attribute.ATTACK_SPEED, null, -0.4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                "CA ",
                "ASN",
                "S  ");

        recipe.setIngredient('C', new RecipeChoice.MaterialChoice(Material.AMETHYST_CLUSTER));
        recipe.setIngredient('A', new RecipeChoice.MaterialChoice(Material.AMETHYST_SHARD));
        recipe.setIngredient('S', new RecipeChoice.MaterialChoice(Material.STICK));
        recipe.setIngredient('N', new RecipeChoice.MaterialChoice(Material.NAUTILUS_SHELL));

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

    public class GreatAmethystSwordComponent extends ConstructableActiveAbilityComponent<PlayerItemConsumeEvent>{

        public static double BASE_RADIUS = 0.8;
        public static double BASE_DAMAGE = 1.5;

        public GreatAmethystSwordComponent() {
            getComponents().set(new AttributeBasedProperty(20*15, ComponentTypes.ABILITY_COOLDOWN));
            getComponents().set(new AttributeBasedProperty(10, ComponentTypes.ABILITY_CAST_RANGE));
            getComponents().set(new AttributeBasedProperty(BASE_RADIUS, ComponentTypes.ABILITY_AREA_OF_EFFECT));
            getComponents().set(new DamageProperty(MPBasedDamageFunction.linearMageOnly(BASE_DAMAGE, 0.2)));
            getComponents().set(new EffectsProperty(
                    new EffectsProperty.PotionData(PotionEffectType.SLOWNESS, Functions.CEIL(new AttributeFunction(Attributes.ABILITY_DURATION, 20d*3d)), Functions.CONSTANT(2))
            ));
        }

        @Override
        public void addLoreLines(@NotNull ActiveAbilityLorePart componentable) {
            componentable.addFullAbilityDescription((ConstructableCustomItem) Items.AMETHYST_DOLOIRE, 2);
            super.addLoreLines(componentable);
        }

        @Override
        public @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull Player player, @NotNull PlayerItemConsumeEvent playerItemConsumeEvent) {
            Location location = Utils.rayTraceBlockUnder(player);

            if (location == null) return ActionResult.PENALTY_COOLDOWN;

            final double radius = getComponents().getOrException(ComponentTypes.ABILITY_AREA_OF_EFFECT).get(player);
            final double castRange = getComponents().getOrException(ComponentTypes.ABILITY_CAST_RANGE).get(player);
            List<PotionEffect> potionEffects = getComponents().getOrException(ComponentTypes.ABILITY_EFFECTS).get(player);

            Vector direction = player.getLocation().getDirection();
            direction.setY(0).normalize().multiply(radius*2);

            Damage damage = getComponents().getOrException(ComponentTypes.ABILITY_DAMAGE).get(Attributes.MAGICAL_POTENTIAL.calculate(player));

            new BukkitRunnable(){
                int count = 0;
                @Override
                public void run() {
                    count++;
                    location.add(direction);
                    new AmethystSpikeParticle((float) radius*2f).play(location);
                    ParticleUtils.circle(new ParticleBuilder(Particle.DUST).color(Color.FUCHSIA).location(location), radius);
                    Collection<LivingEntity> nearbyLivingEntities = Utils.livingEntitiesInRadius(location, radius);

                    for (LivingEntity entity : nearbyLivingEntities){
                        potionEffects.forEach(entity::addPotionEffect);
                        DamageUtils.damage(entity, damage, player);
                    }

                    if (2*radius*count > castRange) cancel();
                }
            }.runTaskTimer(RpgU.getInstance(), 0, 1);


            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public void onConsume(@NotNull CustomItem customItem, @NotNull PlayerItemConsumeEvent event) {
            event.setCancelled(true);
            activate(customItem, event.getPlayer(), event);
        }
    }
}
package me.udnek.rpgu.item.equipment.doloire;

import com.destroystokyo.paper.ParticleBuilder;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Consumable;
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import me.udnek.coreu.custom.attribute.AttributeUtils;
import me.udnek.coreu.custom.attribute.CustomAttributeModifier;
import me.udnek.coreu.custom.attribute.CustomAttributesContainer;
import me.udnek.coreu.custom.component.instance.AutoGeneratingFilesItem;
import me.udnek.coreu.custom.component.instance.CustomItemAttributesComponent;
import me.udnek.coreu.custom.equipmentslot.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.equipmentslot.slot.CustomEquipmentSlot.Single;
import me.udnek.coreu.custom.equipmentslot.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.equipmentslot.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.custom.item.RepairData;
import me.udnek.coreu.util.Either;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.active.ConstructableActiveAbilityComponent;
import me.udnek.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.component.ability.property.DamageProperty;
import me.udnek.rpgu.component.ability.property.EffectsProperty;
import me.udnek.rpgu.component.ability.property.function.AttributeFunction;
import me.udnek.rpgu.component.ability.property.function.Functions;
import me.udnek.rpgu.component.ability.property.function.MPBasedDamageFunction;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.lore.ability.ActiveAbilityLorePart;
import me.udnek.rpgu.mechanic.damaging.Damage;
import me.udnek.rpgu.mechanic.damaging.DamageUtils;
import me.udnek.rpgu.particle.AmethystSpikeParticle;
import me.udnek.rpgu.particle.ParticleUtils;
import me.udnek.rpgu.util.Utils;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.*;
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
    public @Nullable List<ItemFlag> getTooltipHides() {
        return List.of(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
    }

    @Override
    public @Nullable DataSupplier<ItemRarity> getRarity() {return DataSupplier.of(ItemRarity.UNCOMMON);}

    @Override
    public boolean addDefaultAttributes() {return true;}

    @Override
    public void initializeAdditionalAttributes(@NotNull ItemStack itemStack) {
        super.initializeAdditionalAttributes(itemStack);
        getComponents().set(AutoGeneratingFilesItem.HANDHELD_20X20);
        AttributeUtils.appendAttribute(itemStack, Attribute.ATTACK_DAMAGE, null, 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);
        AttributeUtils.appendAttribute(itemStack, Attribute.ATTACK_SPEED, null, -0.4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);
    }

    @Override
    public @Nullable DataSupplier<Integer> getMaxDamage() {
        return DataSupplier.of(Material.IRON_SWORD.getDefaultData(DataComponentTypes.MAX_DAMAGE));
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
    public @Nullable DataSupplier<Consumable> getConsumable() {
        Consumable build = Consumable.consumable().consumeSeconds(CAST_TIME / 20f).animation(ItemUseAnimation.SPEAR).hasConsumeParticles(false)
                .sound(Registry.SOUNDS.getKeyOrThrow(Sound.INTENTIONALLY_EMPTY).key()).build();
        return DataSupplier.of(build);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        CustomAttributeModifier attribute = new CustomAttributeModifier(MELEE_MAGICAL_DAMAGE_MULTIPLIER,  AttributeModifier.Operation.ADD_NUMBER, CustomEquipmentSlot.MAIN_HAND);
        getComponents().set(new CustomItemAttributesComponent(new CustomAttributesContainer.Builder().add(Attributes.MELEE_MAGICAL_DAMAGE_MULTIPLIER, attribute).build()));

        getComponents().set(new GreatAmethystSwordComponent());
    }

    @Override
    public @Nullable RepairData initializeRepairData() {
        return new RepairData(Material.AMETHYST_SHARD);
    }

    public static class GreatAmethystSwordComponent extends ConstructableActiveAbilityComponent<PlayerItemConsumeEvent>{

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
            componentable.addFullAbilityDescription(Items.AMETHYST_DOLOIRE, 2);
            super.addLoreLines(componentable);
        }

        @Override
        public @NotNull ActionResult action(@NotNull CustomItem custom.Item, @NotNull LivingEntity livingEntity, @NotNull Either<UniversalInventorySlot, CustomEquipmentSlot.Single> slot,
                                            @NotNull PlayerItemConsumeEvent playerItemConsumeEvent) {
            Location location = Utils.rayTraceBlockUnder(livingEntity);

            if (location == null) return ActionResult.PENALTY_COOLDOWN;

            final double radius = getComponents().getOrException(ComponentTypes.ABILITY_AREA_OF_EFFECT).get(livingEntity);
            final double castRange = getComponents().getOrException(ComponentTypes.ABILITY_CAST_RANGE).get(livingEntity);
            List<PotionEffect> potionEffects = getComponents().getOrException(ComponentTypes.ABILITY_EFFECTS).get(livingEntity);

            Vector direction = livingEntity.getLocation().getDirection();
            direction.setY(0).normalize().multiply(radius*2);

            Damage damage = getComponents().getOrException(ComponentTypes.ABILITY_DAMAGE).get(Attributes.MAGICAL_POTENTIAL.calculate(livingEntity));

            new BukkitRunnable(){
                int count = 0;
                @Override
                public void run() {
                    count++;
                    location.add(direction);
                    new AmethystSpikeParticle((float) radius*2f).play(location);
                    ParticleUtils.circle(new ParticleBuilder(Particle.DUST).color(Color.FUCHSIA).location(location), radius);
                    Collection<LivingEntity> nearbyLivingEntities = Utils.livingEntitiesInRadiusIntersects(location, radius);

                    for (LivingEntity entity : nearbyLivingEntities){
                        potionEffects.forEach(entity::addPotionEffect);
                        DamageUtils.damage(entity, damage, livingEntity);
                    }

                    if (2*radius*count > castRange) cancel();
                }
            }.runTaskTimer(RpgU.getInstance(), 0, 1);


            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public void onConsume(@NotNull CustomItem custom.Item, @NotNull PlayerItemConsumeEvent event) {
            event.setCancelled(true);
            activate(custom.Item, event.getPlayer(), new Either<>(new BaseUniversalSlot(event.getHand()), null), event);
        }
    }
}
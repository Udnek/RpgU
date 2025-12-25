package me.udnek.rpgu.component;

import me.udnek.coreu.custom.component.ConstructableComponentType;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.entitylike.entity.CustomEntityType;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.custom.registry.CustomRegistries;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.ability.active.ActiveAbilityComponent;
import me.udnek.rpgu.component.ability.property.*;
import me.udnek.rpgu.component.ability.property.function.MPBasedDamageFunction;
import me.udnek.rpgu.component.ability.property.type.AbilityPropertyType;
import me.udnek.rpgu.component.ability.property.type.AttributeBasedPropertyType;
import me.udnek.rpgu.component.ability.property.type.ConstructableAbilityPropertyType;
import me.udnek.rpgu.component.instance.ArrowComponent;
import me.udnek.rpgu.component.instance.DamageResistent;
import me.udnek.rpgu.mechanic.damaging.Damage;
import org.bukkit.entity.Player;

public class ComponentTypes {

    public static final CustomComponentType<CustomItem, EquippableItemComponent> EQUIPPABLE_ITEM;
    public static final CustomComponentType<CustomItem, ActiveAbilityComponent<?>> ACTIVE_ABILITY_ITEM;
    public static final CustomComponentType<CustomItem, ArrowComponent> ARROW_ITEM;

    public static final CustomComponentType<CustomEntityType, DamageResistent> DAMAGE_RESISTENT_ENTITY;

    public static final AttributeBasedPropertyType ABILITY_COOLDOWN;
    public static final AttributeBasedPropertyType ABILITY_CAST_RANGE;
    public static final AttributeBasedPropertyType ABILITY_AREA_OF_EFFECT;
    public static final AttributeBasedPropertyType ABILITY_DURATION;
    public static final AbilityPropertyType<AbilityProperty<Player, Integer>> ABILITY_CAST_TIME;
    public static final AbilityPropertyType<AbilityProperty<Player, Double>> ABILITY_MISS_USAGE_COOLDOWN_MULTIPLIER;
    public static final AbilityPropertyType<AbilityProperty<Double, Damage>> ABILITY_DAMAGE;
    public static final AbilityPropertyType<EffectsProperty> ABILITY_EFFECTS;


    static {
        EQUIPPABLE_ITEM = register(new ConstructableComponentType("equippable_item", EquippableItemComponent.EMPTY, ConstructableEquippableItemComponent::new));
        ACTIVE_ABILITY_ITEM = register(new ConstructableComponentType("active_ability_item", ActiveAbilityComponent.DEFAULT));
        ARROW_ITEM = register(new ConstructableComponentType("arrow_item", ArrowComponent.DEFAULT));

        DAMAGE_RESISTENT_ENTITY = register(new ConstructableComponentType("damage_resistent", DamageResistent.DEFAULT));

        ABILITY_COOLDOWN = register(new AttributeBasedPropertyType("ability_cooldown", Attributes.COOLDOWN_TIME, -1, "ability.rpgu.cooldown", true));
        ABILITY_CAST_RANGE = register(new AttributeBasedPropertyType("ability_cast_range", Attributes.CAST_RANGE, -1, "ability.rpgu.cast_range"));
        ABILITY_AREA_OF_EFFECT = register(new AttributeBasedPropertyType("ability_area_of_effect", Attributes.AREA_OF_EFFECT, -1, "ability.rpgu.area_of_effect"));
        ABILITY_DURATION = register(new AttributeBasedPropertyType("ability_duration", Attributes.ABILITY_DURATION, -1, "ability.rpgu.duration", true));
        ABILITY_CAST_TIME = (AbilityPropertyType<AbilityProperty<Player, Integer>>) register(new ConstructableAbilityPropertyType("ability_cast_time", new CastTimeProperty(-1)));
        ABILITY_MISS_USAGE_COOLDOWN_MULTIPLIER = (AbilityPropertyType<AbilityProperty<Player, Double>>) register(new ConstructableAbilityPropertyType("ability_miss_usage_cooldown_multiplier", new MissUsageCooldownMultiplierProperty(0.3)));
        ABILITY_DAMAGE = (AbilityPropertyType<AbilityProperty<Double, Damage>>) register(new ConstructableAbilityPropertyType("ability_damage", new DamageProperty(new MPBasedDamageFunction(null, null))));
        ABILITY_EFFECTS = (AbilityPropertyType<EffectsProperty>) register(new ConstructableAbilityPropertyType("ability_effects", EffectsProperty.DEFAULT));
    }

    private static <T extends CustomComponentType<?, ?>> T register(T type){
        return CustomRegistries.COMPONENT_TYPE.register(RpgU.getInstance(), type);
    }
}

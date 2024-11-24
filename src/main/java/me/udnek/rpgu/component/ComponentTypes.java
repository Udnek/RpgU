package me.udnek.rpgu.component;

import me.udnek.itemscoreu.customcomponent.ConstructableComponentType;
import me.udnek.itemscoreu.customcomponent.CustomComponentType;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.customregistry.CustomRegistries;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.ability.ActiveAbilityComponent;
import me.udnek.rpgu.component.ability.ConstructableActiveAbilityComponent;
import me.udnek.rpgu.component.ability.property.CastTimeProperty;
import me.udnek.rpgu.component.ability.property.type.AttributeBasedPropertyType;
import me.udnek.rpgu.component.ability.property.type.PropertyType;

public class ComponentTypes {

    public static final CustomComponentType<CustomItem, EquippableItemComponent> EQUIPPABLE_ITEM;
    public static final CustomComponentType<CustomItem, ActiveAbilityComponent<?>> ACTIVE_ABILITY_ITEM;

    public static final AttributeBasedPropertyType ABILITY_COOLDOWN;
    public static final AttributeBasedPropertyType ABILITY_CAST_RANGE;
    public static final AttributeBasedPropertyType ABILITY_AREA_OF_EFFECT;
    public static final AttributeBasedPropertyType ABILITY_DURATION;
    public static final PropertyType<?> ABILITY_CAST_TIME;


    static {
        EQUIPPABLE_ITEM = register(new ConstructableComponentType("equippable_item", EquippableItemComponent.EMPTY));
        ACTIVE_ABILITY_ITEM = register(new ConstructableComponentType("active_ability_item", ConstructableActiveAbilityComponent.DEFAULT));

        ABILITY_COOLDOWN = (AttributeBasedPropertyType) register(new AttributeBasedPropertyType("ability_cooldown", -1, Attributes.COOLDOWN_TIME, "ability.rpgu.cooldown", true));
        ABILITY_CAST_RANGE = (AttributeBasedPropertyType) register(new AttributeBasedPropertyType("ability_cooldown", -1, Attributes.CAST_RANGE, "ability.rpgu.cast_range"));
        ABILITY_AREA_OF_EFFECT = (AttributeBasedPropertyType) register(new AttributeBasedPropertyType("ability_area_of_effect", -1, Attributes.AREA_OF_EFFECT, "ability.rpgu.area_of_effect"));
        ABILITY_DURATION = (AttributeBasedPropertyType) register(new AttributeBasedPropertyType("ability_duration", -1, Attributes.ABILITY_DURATION, "ability.rpgu.duration"));
        ABILITY_CAST_TIME = new PropertyType<>("ability_cast_time", new CastTimeProperty(-1));
    }

    private static CustomComponentType register(CustomComponentType type){
        return CustomRegistries.COMPONENT_TYPE.register(RpgU.getInstance(), type);
    }
}

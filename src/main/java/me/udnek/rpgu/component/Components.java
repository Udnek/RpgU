package me.udnek.rpgu.component;

import me.udnek.coreu.custom.component.ConstructableComponentType;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.entitylike.entity.CustomEntityType;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.custom.registry.CustomRegistries;
import me.udnek.coreu.rpgu.component.ability.RPGUItemAbility;
import me.udnek.coreu.rpgu.component.ability.property.function.PropertyFunctions;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.component.ability.property.DamageProperty;
import me.udnek.rpgu.component.instance.ArrowComponent;
import me.udnek.rpgu.component.instance.DamageResistant;
import me.udnek.rpgu.mechanic.damaging.Damage;

public class Components {

    public static final CustomComponentType<CustomItem, ArrowComponent>
            ARROW_ITEM = register(new ConstructableComponentType<>("arrow_item", ArrowComponent.DEFAULT));
    public static final CustomComponentType<CustomEntityType, DamageResistant>
            DAMAGE_RESISTANT_ENTITY = register(new ConstructableComponentType<>("damage_resistant_entity", DamageResistant.DEFAULT));
    public static final CustomComponentType<RPGUItemAbility<?>, DamageProperty>
            ABILITY_DAMAGE = register(new ConstructableComponentType<>("ability_damage", new DamageProperty(Damage.Type.PHYSICAL, PropertyFunctions.CONSTANT(0d))));



    private static <T extends CustomComponentType<?, ?>> T register(T type){
        return CustomRegistries.COMPONENT_TYPE.register(RpgU.getInstance(), type);
    }
}

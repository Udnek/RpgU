package me.udnek.rpgu.component;

import me.udnek.itemscoreu.customcomponent.ConstructableComponentType;
import me.udnek.itemscoreu.customcomponent.CustomComponentType;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.customregistry.CustomRegistries;
import me.udnek.rpgu.RpgU;

public class ComponentTypes {

    public static final CustomComponentType<CustomItem, EquippableItemComponent>
            EQUIPPABLE_ITEM = register(new ConstructableComponentType("equippable_item", EquippableItemComponent.DEFAULT));
    public static final CustomComponentType<CustomItem, VanillaAttributesComponent>
            VANILLA_ATTRIBUTES_ITEM = register(new ConstructableComponentType("vanilla_attributes_item", VanillaAttributesComponent.DEFAULT));

    private static CustomComponentType register(CustomComponentType type){
        return CustomRegistries.COMPONENT_TYPE.register(RpgU.getInstance(), type);
    }
}

package me.udnek.rpgu.component;

import me.udnek.itemscoreu.customcomponent.CustomComponent;
import me.udnek.itemscoreu.customcomponent.CustomComponentType;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.attribute.VanillaAttributesContainer;
import org.jetbrains.annotations.NotNull;

public class VanillaAttributesComponent implements CustomComponent<CustomItem> {

    public static final VanillaAttributesComponent DEFAULT = new VanillaAttributesComponent(VanillaAttributesContainer.empty());

    VanillaAttributesContainer container;

    public VanillaAttributesComponent(@NotNull VanillaAttributesContainer container){
        this.container = container;
    }

    public @NotNull VanillaAttributesContainer getAttributes(@NotNull CustomItem customItem) {return container;}

    @Override
    public @NotNull CustomComponentType<CustomItem, VanillaAttributesComponent> getType() {
        return ComponentTypes.VANILLA_ATTRIBUTES_ITEM;
    }

}

package me.udnek.rpgu.component;

import me.udnek.itemscoreu.customcomponent.CustomComponent;
import me.udnek.itemscoreu.customcomponent.CustomComponentType;
import me.udnek.itemscoreu.customitem.CustomItem;
import org.jetbrains.annotations.NotNull;

public class ActiveAbilityComponent implements CustomComponent<CustomItem> {

    public static final ActiveAbilityComponent DEFAULT = new ActiveAbilityComponent();

    @Override
    public @NotNull CustomComponentType<CustomItem, ?> getType() {
        return ComponentTypes.ACTIVE_ABILITY;
    }
}

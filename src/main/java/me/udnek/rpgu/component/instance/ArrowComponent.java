package me.udnek.rpgu.component.instance;

import com.destroystokyo.paper.event.player.PlayerReadyArrowEvent;
import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.rpgu.component.ComponentTypes;
import net.kyori.adventure.text.Component;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ArrowComponent extends CustomComponent<CustomItem> {
    ArrowComponent DEFAULT = new ArrowComponent(){

        @Override
        public @NotNull Component getIcon(@NotNull CustomItem customItem, @NotNull ItemStack itemStack) {
            return Component.empty();
        }
    };

    default void onBeingShoot(@NotNull CustomItem customItem, @NotNull ItemStack itemStack, @NotNull EntityShootBowEvent event) {}
    default @NotNull ChoseArrowResult onChooseArrow(@NotNull CustomItem customItem, @NotNull PlayerReadyArrowEvent event) {return ChoseArrowResult.ALLOW;}

    @NotNull Component getIcon(@NotNull CustomItem customItem, @NotNull ItemStack itemStack);

    enum ChoseArrowResult {
        ALLOW,
        DENY
    }

    @Override
    default @NotNull CustomComponentType<CustomItem, ?> getType() {
        return ComponentTypes.ARROW_ITEM;
    }
}

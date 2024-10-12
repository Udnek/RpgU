package me.udnek.rpgu.component;

import me.udnek.itemscoreu.customcomponent.CustomComponent;
import me.udnek.itemscoreu.customcomponent.CustomComponentType;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.mechanic.damaging.DamageEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface EquippableItemComponent extends CustomComponent<CustomItem> {

    EquippableItemComponent DEFAULT = new EquippableItemComponent() {
        @Override
        public boolean isAppropriateSlot(@NotNull CustomEquipmentSlot slot) {
            return false;
        }
    };

    boolean isAppropriateSlot(@NotNull CustomEquipmentSlot slot);
    //boolean isEquippedInAppropriateSlot(@NotNull Player player);
    default void onEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot, @NotNull ItemStack itemStack){}
    default void onUnequipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot, @NotNull ItemStack itemStack){}
    default void tickBeingEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot){}
    default void onPlayerAttacksWhenEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot, @NotNull DamageEvent event){}
    default void onPlayerReceivesDamageWhenEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot, @NotNull DamageEvent event){}
    default @Nullable Component getHudImage(@NotNull CustomItem item, @NotNull Player player){return null;}

    @Override
    @NotNull
    default CustomComponentType<CustomItem, ?> getType(){
        return ComponentTypes.EQUIPPABLE_ITEM;
    }
}

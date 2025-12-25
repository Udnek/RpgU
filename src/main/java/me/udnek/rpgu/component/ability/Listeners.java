package me.udnek.rpgu.component.ability;

import me.udnek.coreu.custom.equipmentslot.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.util.Either;
import me.udnek.coreu.util.SelfRegisteringListener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class Listeners extends SelfRegisteringListener {
    public Listeners(@NotNull Plugin plugin) {
        super(plugin);
    }


    // DEATH PROTECTION
//    @Override
//    public void onResurrect(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, boolean activatedBefore,
//                            @NotNull EntityResurrectEvent event) {
//        if (!activatedBefore) activate(customItem, event.getEntity(), true, new Either<>(slot, null), event);
//    }

    // ELYTRA ACTIVATOR

    // SWORD DASH

    // DOLOIRE

    // HUNGRY HORROR
}

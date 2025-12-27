package me.udnek.rpgu.component.ability;

import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.coreu.util.SelfRegisteringListener;
import me.udnek.rpgu.item.Items;
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

    // TODO сдесь листенер райт кликов
    // TODO для всего остального (тотем возражления, элитра, и т.д.) сделать тут личные листенеры и вызывать activate(). (как во FnafU кароче)
//    public onASDJasd(){
//        for (var rightClickableItem : Items.FABRIC.getComponents().get(RPGUComponents.ACTIVE_ABILITY_ITEM).getComponents().getAllTyped(RPGUActiveTriggerableAbility.class)) {
//            rightClickableItem.onRightClick(event);
//        }
//    }

    // ELYTRA ACTIVATOR

    // SWORD DASH

    // DOLOIRE

    // HUNGRY HORROR
}

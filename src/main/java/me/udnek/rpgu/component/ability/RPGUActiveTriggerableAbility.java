package me.udnek.rpgu.component.ability;

import me.udnek.coreu.rpgu.component.ability.RPGUItemAbility;
import me.udnek.coreu.rpgu.component.ability.active.RPGUItemActiveAbility;
import org.bukkit.event.player.PlayerInteractEvent;


// TODO этот класс должны имплиминтировать больщинство абилок, что бы потом из onRightClick() вызывать activate()
public interface RPGUActiveTriggerableAbility<Context> extends RPGUItemActiveAbility<Context> {

    void onRightClick(PlayerInteractEvent event);
}

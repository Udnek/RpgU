package me.udnek.rpgu.component.instance;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Consumable;
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.nms.Nms;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.active.ConstructableActiveAbilityComponent;
import me.udnek.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.component.ability.property.DamageProperty;
import me.udnek.rpgu.component.ability.property.function.MPBasedDamageFunction;
import me.udnek.rpgu.equipment.slot.UniversalInventorySlot;
import me.udnek.rpgu.lore.ability.ActiveAbilityLorePart;
import me.udnek.rpgu.mechanic.damaging.Damage;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class SwordDash {
    public static int CAST_TIME = (int) (0.75 * 20);

    public static void applyAbility(@NotNull ItemStack itemStack, @NotNull CustomItem customItem){
        Consumable build =  Consumable.consumable().animation(ItemUseAnimation.SPEAR).consumeSeconds(CAST_TIME / 20f).hasConsumeParticles(false)
                .sound(Registry.SOUNDS.getKeyOrThrow(Sound.INTENTIONALLY_EMPTY).key()).build();

        itemStack.setData(DataComponentTypes.CONSUMABLE, build);
        customItem.getComponents().set(new Ability());
    }

    static class Ability extends ConstructableActiveAbilityComponent<PlayerItemConsumeEvent> {

        public static double BASE_RADIUS = 1.5;
        public static double BASE_DAMAGE = 1.5;

        public Ability() {
            getComponents().set(new AttributeBasedProperty(20*15, ComponentTypes.ABILITY_COOLDOWN));
            getComponents().set(new AttributeBasedProperty(15, ComponentTypes.ABILITY_CAST_RANGE));
            getComponents().set(new AttributeBasedProperty(BASE_RADIUS, ComponentTypes.ABILITY_AREA_OF_EFFECT));
            getComponents().set(new DamageProperty(MPBasedDamageFunction.linearMageOnly(BASE_DAMAGE, 0.2)));
        }

        @Override
        public void addLoreLines(@NotNull ActiveAbilityLorePart componentable) {
            componentable.addAbilityDescription(Component.translatable("ability.rpgu.swords.ability.0"));
            super.addLoreLines(componentable);
        }

        @Override
        public @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot slot,
                                            @NotNull PlayerItemConsumeEvent playerItemConsumeEvent) {
            if (!(livingEntity instanceof  Player player)) return ActionResult.FULL_COOLDOWN;
            Location locationStart = player.getLocation();

            final double radius = getComponents().getOrException(ComponentTypes.ABILITY_AREA_OF_EFFECT).get(player);
            final double castRange = getComponents().getOrException(ComponentTypes.ABILITY_CAST_RANGE).get(player);

            Vector direction = player.getLocation().getDirection();
            direction.setY(0).normalize().multiply(40);


            Damage damage = getComponents().getOrException(ComponentTypes.ABILITY_DAMAGE).get(Attributes.MAGICAL_POTENTIAL.calculate(player));
            Nms.get().setSpinAttack(player, 2 * 20, 1, customItem.getItem());

            player.setVelocity(direction);


            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public void onConsume(@NotNull CustomItem customItem, @NotNull PlayerItemConsumeEvent event) {
            event.setCancelled(true);
            activate(customItem, event.getPlayer(), event);
        }
    }
}

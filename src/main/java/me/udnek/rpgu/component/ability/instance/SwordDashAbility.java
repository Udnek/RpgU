package me.udnek.rpgu.component.ability.instance;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Consumable;
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.equipmentslot.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.nms.Nms;
import me.udnek.coreu.rpgu.component.RPGUActiveItem;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.coreu.rpgu.component.ability.active.RPGUConstructableActiveAbility;
import me.udnek.coreu.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.coreu.rpgu.component.ability.property.function.PropertyFunctions;
import me.udnek.rpgu.component.Components;
import me.udnek.rpgu.component.ability.Abilities;
import me.udnek.rpgu.component.ability.property.DamageProperty;
import me.udnek.rpgu.mechanic.damaging.Damage;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SwordDashAbility extends RPGUConstructableActiveAbility<PlayerItemConsumeEvent> {

    public static final SwordDashAbility DEFAULT = new SwordDashAbility(){
//        @Override
//        protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot universalInventorySlot, @NotNull PlayerItemConsumeEvent playerItemConsumeEvent) {
//            return ActionResult.NO_COOLDOWN;
//        }
    };

    public static final int CAST_TIME = (int) (0.75 * 20);
    public static final double BASE_RADIUS = 1.5;
    public static final double BASE_DAMAGE = 1.5;

    public static void applyAbility(@NotNull ItemStack itemStack, @NotNull CustomItem customItem){
        Consumable build =  Consumable.consumable().animation(ItemUseAnimation.SPEAR).consumeSeconds(CAST_TIME / 20f).hasConsumeParticles(false)
                .sound(Registry.SOUNDS.getKeyOrThrow(Sound.INTENTIONALLY_EMPTY).key()).build();

        itemStack.setData(DataComponentTypes.CONSUMABLE, build);
        customItem.getComponents().getOrCreateDefault(RPGUComponents.ACTIVE_ABILITY_ITEM).getComponents().set(new SwordDashAbility());
    }

    public SwordDashAbility() {
        getComponents().set(new AttributeBasedProperty(20*15, RPGUComponents.ABILITY_COOLDOWN_TIME));
        getComponents().set(new AttributeBasedProperty(15, RPGUComponents.ABILITY_CAST_RANGE));
        getComponents().set(new AttributeBasedProperty(BASE_RADIUS, RPGUComponents.ABILITY_AREA_OF_EFFECT));
        getComponents().set(new DamageProperty(Damage.Type.PHYSICAL, PropertyFunctions.CONSTANT(3d)));
    }

    @Override
    protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot universalInventorySlot, @NotNull PlayerItemConsumeEvent playerItemConsumeEvent) {
        if (!(livingEntity instanceof  Player player)) return ActionResult.FULL_COOLDOWN;
        Location locationStart = player.getLocation();

        final double radius = getComponents().getOrException(RPGUComponents.ABILITY_AREA_OF_EFFECT).get(player);
        final double castRange = getComponents().getOrException(RPGUComponents.ABILITY_CAST_RANGE).get(player);

        Vector direction = player.getLocation().getDirection();
        direction.setY(0).normalize().multiply(40);


        Damage damage = getComponents().getOrException(Components.ABILITY_DAMAGE).get(player);
        Nms.get().setSpinAttack(player, 2 * 20, 1, customItem.getItem());

        player.setVelocity(direction);


        return ActionResult.FULL_COOLDOWN;
    }

    @Override
    public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
        return null;
    }


    @Override
    public @NotNull CustomComponentType<? super RPGUActiveItem, ? extends CustomComponent<? super RPGUActiveItem>> getType() {
        return Abilities.SWORD_DASH;
    }
}

package me.udnek.rpgu.item.utility;

import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.equipment.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.equipment.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.equipment.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.nms.Nms;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.coreu.rpgu.component.RPGUPassiveItem;
import me.udnek.coreu.rpgu.component.ability.passive.RPGUConstructablePassiveAbility;
import me.udnek.coreu.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.component.ability.Abilities;
import me.udnek.rpgu.component.ability.RPGUPassiveTriggerableAbility;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.*;
import java.util.function.Consumer;

@NullMarked
public class SoulCage extends ConstructableCustomItem {
    @Override
    public String getRawId() {
        return "soul_cage";
    }

    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Soul Cage", "Клетка душь");
    }

    @Override
    protected void generateRecipes(Consumer<Recipe> consumer) {
        //TODO
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        getComponents().getOrCreateDefault(RPGUComponents.PASSIVE_ABILITY_ITEM).getComponents().set(Passive.DEFAULT);
    }


    public static class Passive extends RPGUConstructablePassiveAbility<EntityDeathEvent> implements RPGUPassiveTriggerableAbility<EntityDeathEvent> {

        public static final Passive DEFAULT = new Passive();

        private final Map<Player, List<String>> entitiesToResurrect = new HashMap<>();

        public Passive() {
            getComponents().set(new AttributeBasedProperty(5 * 20, RPGUComponents.ABILITY_COOLDOWN_TIME));
        }

        @Override
        protected ActionResult action(CustomItem customItem, LivingEntity livingEntity, UniversalInventorySlot universalInventorySlot, EntityDeathEvent event) {
            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public void onTamedEntityDeath(CustomItem customItem, UniversalInventorySlot slot, EntityDeathEvent event) {
            LivingEntity entity = event.getEntity();
            Player player = (Player) Objects.requireNonNull(((Tameable) entity).getOwner());

            event.setCancelled(true);
            event.setReviveHealth(Objects.requireNonNull(entity.getAttribute(Attribute.MAX_HEALTH)).getValue());

            entitiesToResurrect.computeIfAbsent(player, k -> new ArrayList<>()).add(Nms.get().serializeEntity(entity));
            entity.remove();

            activate(customItem, player, slot, event);
        }

        @Override
        public CustomEquipmentSlot getSlot() {
            return CustomEquipmentSlot.DUMB_INVENTORY;
        }

        @Override
        public void tick(CustomItem customItem, Player player, BaseUniversalSlot baseUniversalSlot, int i) {
            if (getCurrentCooldown(customItem, player) > 0) return;
            List<String> entities = entitiesToResurrect.get(player);
            if (entities == null) return;
            for (String bytes : entities) {
                LivingEntity entity = (LivingEntity) Nms.get().deserializeEntity(bytes, player.getWorld());
                if (entity == null) throw new RuntimeException("SoulCage: Could not deserialize entity");
                entity.setHealth(Objects.requireNonNull(entity.getAttribute(Attribute.MAX_HEALTH)).getValue());
                entity.setVelocity(new Vector());
                entity.spawnAt(entity.getLocation());
            }
            entitiesToResurrect.remove(player);
        }

        @Override
        public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
            return Pair.of(List.of(""), List.of(""));//TODO
        }

        @Override
        public CustomComponentType<? super RPGUPassiveItem, ? extends CustomComponent<? super RPGUPassiveItem>> getType() {
            return Abilities.SOUL_CAGE;
        }

    }
}

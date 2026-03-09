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
import me.udnek.coreu.util.LoreBuilder;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.component.ability.Abilities;
import me.udnek.rpgu.component.ability.RPGUPassiveTriggerableAbility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.object.ObjectContents;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

        private static final NamespacedKey SOUL_CAGE_KEY = new NamespacedKey(RpgU.getInstance(), "soul_cage_entities");

        public Passive() {
            getComponents().set(new AttributeBasedProperty(100 * 20, RPGUComponents.ABILITY_COOLDOWN_TIME));
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

            List<String> list = getStoredEntities(player);
            list.add(Nms.get().serializeEntity(entity));
            setStoredEntities(player, list);

            entity.remove();

            slot.modifyItem(input -> {
                LoreBuilder loreBuilder = new LoreBuilder();
                getLore(loreBuilder);
                int position = 130;
                loreBuilder.add(position, Component.translatable("item.rpgu.soul_cage.save_entities").color(NamedTextColor.GREEN));
                for (String entityNBT : list) {
                    position++;
                    Entity deserializeEntity = Nms.get().deserializeEntity(entityNBT, player.getWorld());
                    if (deserializeEntity == null) {throw new RuntimeException("SoulCage: Could not deserialize entity");}
                    loreBuilder.add(position,
                            Component.space().append(Component.object(
                                    ObjectContents.sprite(
                                            NamespacedKey.minecraft( "items"),
                                            NamespacedKey.minecraft("item/" + deserializeEntity.getType().getKey().getKey() + "_spawn_egg")
                                    )
                            ).append(deserializeEntity.name()).color(NamedTextColor.WHITE)
                    ));
                }
                loreBuilder.buildAndApply(input);
                return input;
            }, player);

            activate(customItem, player, slot, event);
        }

        @Override
        public CustomEquipmentSlot getSlot() {
            return CustomEquipmentSlot.DUMB_INVENTORY;
        }

        @Override
        public void tick(CustomItem customItem, Player player, BaseUniversalSlot baseUniversalSlot, int i) {
            if (getCurrentCooldown(customItem, player) > 0) return;
            List<String> entities = getStoredEntities(player);
            if (entities.isEmpty()) return;
            for (String entityNBT : entities) {
                LivingEntity entity = (LivingEntity) Nms.get().deserializeEntity(entityNBT, player.getWorld());
                if (entity == null) throw new RuntimeException("SoulCage: Could not deserialize entity");
                entity.setHealth(Objects.requireNonNull(entity.getAttribute(Attribute.MAX_HEALTH)).getValue());
                entity.setVelocity(new Vector());
                entity.spawnAt(entity.getLocation());
            }
            setStoredEntities(player, new ArrayList<>());

            baseUniversalSlot.modifyItem(input -> {
                LoreBuilder loreBuilder = new LoreBuilder();
                getLore(loreBuilder);
                loreBuilder.buildAndApply(input);
                return input;
            }, player);
        }



        private List<String> getStoredEntities(Player player) {
            PersistentDataContainer pdc = player.getPersistentDataContainer();
            List<String> list = pdc.getOrDefault(SOUL_CAGE_KEY, PersistentDataType.LIST.strings(), new ArrayList<>());
            return new ArrayList<>(list);
        }

        private void setStoredEntities(Player player, List<String> entities) {
            PersistentDataContainer pdc = player.getPersistentDataContainer();
            if (entities.isEmpty()) {
                pdc.remove(SOUL_CAGE_KEY);
            } else {
                pdc.set(SOUL_CAGE_KEY, PersistentDataType.LIST.strings(), entities);
            }
        }

        @Override
        public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
            return Pair.of(List.of("67"), List.of("42"));//TODO
        }

        @Override
        public CustomComponentType<? super RPGUPassiveItem, ? extends CustomComponent<? super RPGUPassiveItem>> getType() {
            return Abilities.SOUL_CAGE;
        }

    }
}

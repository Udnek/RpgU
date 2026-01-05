package me.udnek.rpgu.item.utility;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.keys.StructureKeys;
import io.papermc.paper.registry.keys.StructureTypeKeys;
import io.papermc.paper.registry.keys.tags.StructureTagKeys;
import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.equipment.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.equipment.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.custom.item.ItemUtils;
import me.udnek.coreu.nms.Nms;
import me.udnek.coreu.rpgu.component.RPGUActiveItem;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.coreu.rpgu.component.ability.active.RPGUConstructableActiveAbility;
import me.udnek.coreu.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.component.ability.Abilities;
import me.udnek.rpgu.component.ability.RPGUActiveTriggerableAbility;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.util.TriConsumer;
import org.bukkit.Bukkit;
import org.bukkit.Tag;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.generator.structure.Structure;
import org.bukkit.generator.structure.StructureType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapCursor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class FoldedMap extends ConstructableCustomItem {

    @NotNull
    protected final Key structure;

    public FoldedMap(@NotNull Structure structure) {
        this.structure = RegistryAccess.registryAccess()
                .getRegistry(RegistryKey.STRUCTURE).getKeyOrThrow(structure);
    }

    @Override
    public @NotNull String getRawId() {
        return "folded_map_to_"+structure.value();
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().getOrCreateDefault(RPGUComponents.ACTIVE_ABILITY_ITEM).getComponents().set(new Ability(structure));
    }

    public static class Ability
            extends RPGUConstructableActiveAbility<PlayerInteractEvent>
            implements RPGUActiveTriggerableAbility<PlayerInteractEvent> {

        public static final int SEARCH_RADIUS = 10_000;

        public static final Ability DEFAULT = new Ability(
                RegistryAccess.registryAccess().getRegistry(RegistryKey.STRUCTURE).getKeyOrThrow(Structure.BURIED_TREASURE)
        ){
            @Override
            protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot universalInventorySlot, @NotNull PlayerInteractEvent event) {
                throwCanNotChangeDefault();
                return ActionResult.NO_COOLDOWN;
            }
        };

        protected Key structureKey;

        public Ability(@NotNull Key structureKey){
            // validation
            RegistryAccess.registryAccess().getRegistry(RegistryKey.STRUCTURE).getOrThrow(structureKey);
            this.structureKey = structureKey;
            getComponents().set(new AttributeBasedProperty(20*2, RPGUComponents.ABILITY_COOLDOWN_TIME));
        }

        @Override
        protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot slot, @NotNull PlayerInteractEvent event) {
            if (!(livingEntity instanceof Player player)) return ActionResult.NO_COOLDOWN;

            @Nullable ItemStack map = Nms.get().generateExplorerMap(
                    livingEntity.getLocation(),
                    Set.of(structureKey),
                    SEARCH_RADIUS,
                    false,
                    MapCursor.Type.BANNER_LIME,
                    (byte) 4);

            if (map == null) return ActionResult.NO_COOLDOWN;
            slot.takeItem(1, livingEntity);
            ItemUtils.givePriorityToSlot(slot, player, map);
            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public void onRightClick(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event) {
            activate(customItem, event.getPlayer(), new BaseUniversalSlot(Objects.requireNonNull(event.getHand())), event);
        }

        @Override
        public void getEngAndRuProperties(TriConsumer<@NotNull String, @NotNull String, @NotNull List<Component>> Eng_Ru_Args) {
            super.getEngAndRuProperties(Eng_Ru_Args);
            Eng_Ru_Args.accept("Search Radius: %s", "Радиус поиска: %s", List.of(Component.text(SEARCH_RADIUS)));
        }

        @Override
        public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
            return Pair.of(List.of("Unfolds map"), List.of("Разворачивает карту"));
        }

        @Override
        public @NotNull CustomComponentType<? super RPGUActiveItem, ? extends CustomComponent<? super RPGUActiveItem>> getType() {
            return Abilities.FOLDED_MAP;
        }
    }
}

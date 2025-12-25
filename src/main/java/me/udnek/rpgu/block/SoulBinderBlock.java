package me.udnek.rpgu.block;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.component.instance.RightClickableBlock;
import me.udnek.coreu.custom.entitylike.block.CustomBlockType;
import me.udnek.coreu.custom.entitylike.block.constructabletype.DisplayBasedConstructableBlockType;
import me.udnek.coreu.nms.Nms;
import me.udnek.coreu.serializabledata.SerializableData;
import me.udnek.coreu.serializabledata.SerializableDataManager;
import me.udnek.coreu.util.Either;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.item.Items;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.TileState;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoulBinderBlock extends DisplayBasedConstructableBlockType implements Listener {

    @Override
    public @NotNull ItemStack getFakeDisplay() {
        return Items.SOUL_BINDER.getItem();
    }

    @Override
    public @NotNull String getRawId() {
        return "soul_binder";
    }

    @Override
    public @Nullable Either<LootTable, List<ItemStack>> getLoot() {
        return new Either<>(null, List.of(Items.SOUL_BINDER.getItem()));
    }

    @Override
    public @NotNull Material getBreakSpeedBaseBlock() {
        return Material.IRON_BLOCK;
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(new RightClickableBlock() {

            final RightClickableBlock superComponent = getComponents().getOrException(CustomComponentType.RIGHT_CLICKABLE_BLOCK);

            @Override
            public void onRightClick(@NotNull CustomBlockType customBlockType, @NotNull PlayerInteractEvent event) {
                if (event.getPlayer().isSneaking()) {
                    superComponent.onRightClick(customBlockType, event);
                    return;
                }
                event.setCancelled(true);
                if (event.getPlayer().getGameMode() == GameMode.SPECTATOR) return;

                event.getPlayer().swingHand(EquipmentSlot.HAND);
                SoulBinderData oldData = SerializableDataManager.read(new SoulBinderData(), RpgU.getInstance(), event.getPlayer());
                if (event.getClickedBlock().getLocation().equals(oldData.getLocation())) return;

                event.getPlayer().sendMessage(Component.translatable("block.minecraft.set_spawn"));
                SoulBinderData soulBinderData = new SoulBinderData(event.getClickedBlock().getLocation());
                SerializableDataManager.write(soulBinderData, RpgU.getInstance(), event.getPlayer());
                playEffect(event.getClickedBlock().getLocation());
            }
        });
    }

    public void playEffect(@NotNull Location location){
        ParticleBuilder builder = new ParticleBuilder(Particle.SOUL);
        Location centerLocation = location.toCenterLocation();
        builder.location(centerLocation);
        builder.offset(0.4, 0.4, 0.4);
        builder.count(15);
        builder.extra(0);
        builder.spawn();
        centerLocation.getWorld().playSound(centerLocation, Sound.BLOCK_RESPAWN_ANCHOR_SET_SPAWN, SoundCategory.BLOCKS, 1, 1);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
        if (event.isBedSpawn() || event.isAnchorSpawn()) return;
        SoulBinderData data = new SoulBinderData();
        SerializableDataManager.read(data, RpgU.getInstance(), event.getPlayer());
        Location location = data.getLocation();
        if (location == null) return;
        if (CustomBlockType.get(location.getBlock()) != this) return;
        Location standUpLocation = Nms.get().findAnchorStandUpLocation(EntityType.PLAYER, location);
        if (standUpLocation == null) return;
        event.setRespawnLocation(standUpLocation);
        playEffect(location);
    }

    @Override
    public void load(@NotNull TileState tileState) {}

    @Override
    public void unload(@NotNull TileState tileState) {}

    public static class SoulBinderData implements SerializableData{

        protected Location location = null;

        public SoulBinderData(){}

        public SoulBinderData(@NotNull Location location){
            this.location = location;
        }

        public @Nullable Location getLocation() {
            return location;
        }

        @Override
        public @NotNull String serialize() {
            return SerializableData.serializeMap(location.serialize());
        }

        @Override
        public void deserialize(@Nullable String s) {
            if (s != null) location = Location.deserialize(SerializableData.deserializeMap(s));
        }

        @Override
        public @NotNull String getDataName() {
            return "soul_binder";
        }
    }
}

package me.udnek.rpgu.entity;

import io.papermc.paper.event.player.PlayerItemFrameChangeEvent;
import me.udnek.coreu.custom.entitylike.entity.ConstructableCustomEntityType;
import me.udnek.coreu.custom.entitylike.entity.CustomEntityType;
import me.udnek.rpgu.item.Items;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class InvisibleItemFrameType extends ConstructableCustomEntityType<ItemFrame> implements Listener {

    @Override
    public String getRawId() {
        return "invisible_item_frame";
    }

    @Override
    protected ItemFrame spawnNewEntity(Location location) {
        ItemFrame itemFrame = super.spawnNewEntity(location);
        itemFrame.setGlowing(true);
        location.getWorld().playSound(location, Sound.ENTITY_ITEM_FRAME_PLACE, SoundCategory.BLOCKS, 1, 1);
        return itemFrame;
    }

    @EventHandler
    public void onItemChange(PlayerItemFrameChangeEvent event){
        ItemFrame entity = event.getItemFrame();
        if (CustomEntityType.get(entity) != EntityTypes.INVISIBLE_ITEM_FRAME) return;
        switch (event.getAction()) {
            case PLACE, ROTATE -> {
                entity.setGlowing(false);
                entity.setInvisible(true);
            }
            case REMOVE -> {
                entity.setGlowing(true);
                entity.setInvisible(false);
            }
        }
    }

    @EventHandler
    public void onDeath(HangingBreakEvent event){
        Hanging entity = event.getEntity();
        if (CustomEntityType.get(entity) != EntityTypes.INVISIBLE_ITEM_FRAME) return;

        Location location = entity.getLocation().clone().toCenterLocation();
        World world = location.getWorld();
        world.dropItemNaturally(location, Items.INVISIBLE_FRAME_ITEM.getItem());
        world.playSound(location, Sound.ENTITY_ITEM_FRAME_BREAK, SoundCategory.BLOCKS, 1, 1);

        entity.remove();
        event.setCancelled(true);
    }

    @Override
    public EntityType getVanillaType() {
        return EntityType.ITEM_FRAME;
    }

    @Override
    public void load(Entity entity) {}

    @Override
    public void unload(Entity entity) {}
}

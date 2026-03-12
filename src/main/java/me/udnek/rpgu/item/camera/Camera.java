package me.udnek.rpgu.item.camera;


import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.coreu.custom.component.instance.RightClickableItem;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.nms.Nms;
import me.udnek.coreu.nms.NmsUtils;
import net.kyori.adventure.key.Key;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.awt.Color;

@NullMarked
public class Camera extends ConstructableCustomItem {

    @Override
    public String getRawId() {
        return "camera";
    }

    @Nullable
    @Override
    public DataSupplier<Key> getItemModel() {
        return DataSupplier.of(Material.SPYGLASS.getDefaultData(DataComponentTypes.ITEM_MODEL));
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(new RightClickableItem() {
            @Override
            public void onRightClick(CustomItem customItem, PlayerInteractEvent playerInteractEvent) {
                Player player = playerInteractEvent.getPlayer();

                MapView mapView = Bukkit.getMap(0);
                if (mapView == null) return;

                for (MapRenderer renderer : mapView.getRenderers()) {
                    mapView.removeRenderer(renderer);
                }

                MapRenderer photoRenderer = new PhotoRenderer();
                mapView.addRenderer(photoRenderer);
                // unlocks so renderer can render
                mapView.setLocked(false);

                ItemStack photoItemStack = new ItemStack(Material.FILLED_MAP);
                MapMeta mapMeta = (MapMeta) photoItemStack.getItemMeta();
                mapMeta.setMapView(mapView);
                photoItemStack.setItemMeta(mapMeta);

                player.getInventory().addItem(photoItemStack);
            }
        });
    }

    private static class PhotoRenderer extends MapRenderer {

        boolean rendered = false;

        @Override
        public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
            if (rendered) return;
            rendered = true;

            final float fov = 75f;
            final double halfPlaneSize = Math.tan(Math.toRadians(fov)/2d);

            Location location = player.getEyeLocation();
            Vector direction = new Vector();
            for (int y = 0; y < 128; y++) {
                for (int x = 0; x < 128; x++) {

                    direction.setX((x-64)/64f *halfPlaneSize);
                    direction.setZ(1);
                    direction.setY((y-64)/64f *halfPlaneSize);

                    direction.rotateAroundX(Math.toRadians(location.getPitch()));
                    direction.rotateAroundY(-Math.toRadians(location.getYaw())); // rotating counter-clockwise

                    Vector vecColor = CameraUtils.trace(x, y, location, direction);
                    Color color = CameraUtils.vectorToColor(vecColor);

                    Nms.get().setMapColorWithSave(mapView, 127-x, 127-y, color); // inverting y, cause canvas 0 in top-left
                    mapCanvas.setPixelColor(127-x, 127-y, color); // so client receive dynamically
                }
            }

            // locks and saves
            mapView.setLocked(true);
        }
    }
}














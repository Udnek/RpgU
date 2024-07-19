package me.udnek.rpgu.item.camera;

import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.utils.RightClickable;
import me.udnek.rpgu.item.abstraction.RpgUCustomItem;
import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.bukkit.util.noise.PerlinNoiseGenerator;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Camera extends ConstructableCustomItem implements RightClickable, RpgUCustomItem {
    @Override
    public Integer getCustomModelData() {
        return 0;
    }

    @Override
    public Material getMaterial() {
        return Material.IRON_INGOT;
    }

    @Override
    public String getRawId() {
        return "camera";
    }

    public static Color getSkyColor(Location location){
        long time = location.getWorld().getTime();
        if (time >= 13_000 && time <= 22_300){
            if (PerlinNoiseGenerator.getNoise(location.getDirection().getX()*30f, location.getDirection().getY()*30f, time*0.002f) > 0.7){
                return new Color(1f, 1f, 1f);
            }
        }

        float multiplier = (float) (Math.sin((time/24_000f*Math.PI*2))+1)/2;
        return new Color(0.6f * multiplier, 0.6f* multiplier, 1f*multiplier);
    }

    public static Color traceLocation(Location location){
        return traceLocation(location, location.getDirection());
    }

    public static Color traceLocation(Location location, Vector direction){
        final double maxDistance = 200d;

        RayTraceResult rayTraceResult = location.getWorld().rayTraceBlocks(
                location,
                direction,
                maxDistance,
                FluidCollisionMode.ALWAYS,
                true);

        if (rayTraceResult == null) return getSkyColor(location);

        Block block = rayTraceResult.getHitBlock();
        //if (block == null) new Color(0);

        float blueAddendum = 0f;
        if (block.getType() == Material.WATER){
            RayTraceResult waterRayTraceResult = location.getWorld().rayTraceBlocks(
                    location,
                    direction,
                    maxDistance,
                    FluidCollisionMode.NEVER,
                    true);

            if (waterRayTraceResult != null){
                rayTraceResult = waterRayTraceResult;
                blueAddendum = 190f;
                block = rayTraceResult.getHitBlock();
            }

        }

        //int colorOfMaterial = NMSTest.getColorOfMaterial(block.getType());
        int colorOfMaterial = 0;
        float lightLevel = (block.getLocation().add(rayTraceResult.getHitBlockFace().getDirection()).getBlock().getLightLevel()+1)/16f;
        float distanceAddendum = (float) (location.distance(block.getLocation()) / maxDistance)*50;
        //float stroke = (float) Math.cos(rayTraceResult.getHitPosition().distanceSquared(block.getLocation().toCenterLocation().toVector()));
        //Bukkit.getLogger().info(String.valueOf(stroke));

        float multipliers = lightLevel;

        Color color = new Color(colorOfMaterial);
        int red = (int) Math.min(color.getRed() * multipliers + distanceAddendum, 255);
        int green = (int) Math.min(color.getGreen() * multipliers + distanceAddendum, 255);
        int blue = (int) Math.min((color.getBlue() + blueAddendum)* multipliers + distanceAddendum, 255);
        color = new Color(red, green, blue);

        //block.setType(Material.DIAMOND_BLOCK);
        //location.getWorld().setBlockData(block.getLocation(), block.getBlockData());

        return color;
    }


    @Override
    public void onRightClicks(PlayerInteractEvent playerInteractEvent) {
        Player player = playerInteractEvent.getPlayer();

        MapView mapView = Bukkit.getMap(2);

        for (MapRenderer renderer : mapView.getRenderers()) {
            mapView.removeRenderer(renderer);
        }

        MapRenderer mapRenderer = new MapRenderer() {
            private boolean rendered = false;

            @Override
            public void render(@NotNull MapView mapView, @NotNull MapCanvas mapCanvas, @NotNull Player player) {
                if (rendered) return;
                rendered = true;

                Location location = player.getEyeLocation();
                Location frameLocation = location.clone();
                Vector frameDirection = location.getDirection();

                final float fovMultiplayer = 90f;

                for (int y = 0; y < 128; ++y) {
                    for (int x = 0; x < 128; ++x) {

/*                        frameDirection = location.getDirection().clone();

                        frameDirection.setX(frameDirection.getX() + (y-64)/64f);
                        frameDirection.setZ(frameDirection.getZ() + (y-64)/64f);
                        frameDirection.setY(frameDirection.getY() + (y-64)/64f);*/

                        //frameDirection.rotateAroundZ((y-64)/64f*Math.sin(frameDirection.getX()));
                        //frameDirection.rotateAroundX((y-64)/64f*Math.cos(frameDirection.getZ()));
                        //frameDirection.rotateAroundY((x-64)/64f);



                        frameLocation.setYaw(
                                (float) (location.getYaw() + (Math.asin((x-64)/64f))/Math.PI*2*fovMultiplayer)
                        );
                        frameLocation.setPitch(
                                (float) (location.getPitch() + (Math.asin((y-64)/64f))/Math.PI*2*fovMultiplayer)
                        );
                        //frameDirection.multiply(new Vector(frameDirection.getX(), frameDirection.getY(), frameDirection.getZ()));
                        //frameDirection
                        //        .rotateAroundAxis(new Vector((x-64)*0.1, (y-64)*0.1, 1), Math.toRadians(90));
                        //Bukkit.getLogger().info(location.getPitch() + " " +location.getYaw());


                        Color color = traceLocation(frameLocation);
                        mapCanvas.setPixelColor(x, y, color);
                    }
                }

            }
        };

        mapView.addRenderer(mapRenderer);
        mapView.setLocked(true);

        ItemStack photoItemStack = new ItemStack(Material.FILLED_MAP);
        MapMeta itemMeta = (MapMeta) photoItemStack.getItemMeta();
        itemMeta.setMapView(mapView);
        photoItemStack.setItemMeta(itemMeta);

        player.getInventory().addItem(photoItemStack);
    }
}














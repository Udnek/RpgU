package me.udnek.rpgu.item.camera;


import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.coreu.custom.component.instance.RightClickableItem;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.nms.Nms;
import me.udnek.coreu.util.LogUtils;
import me.udnek.rpgu.RpgU;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

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

                MapRenderer mapRenderer = new MapRenderer() {
                    private boolean rendered = false;

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

                                Vector vecColor = trace(x, y, location, direction);
                                Color color = vectorToColor(vecColor);
                                mapCanvas.setPixelColor(127-x, 127-y, color); // inverting y, cause canvas 0 in top-left
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
        });
    }


    @SuppressWarnings("PointlessArithmeticExpression")
    public static Vector getSkyColor(Location location, Vector direction) {
        long time = location.getWorld().getTime();
        // night
        if (13_000 <= time && time <= 22_300){
            // stars
            if (PerlinNoiseGenerator.getNoise(direction.getX()*30f, direction.getY()*30f, time*0.002f) > 0.7){
                return new Vector(1f, 1f, 1f);
            }
        }

        float mul = (float) (Math.sin((time/24_000f*Math.PI*2))+1)/2;

        return new Vector(110f/255*mul, 177f/255*mul, 255f/255*mul);
    }

    public static Vector rgbToColor(int rgb){
        return new Vector(
                (rgb >> 16 & 255)/255f,
                (rgb >> 8 & 255)/255f,
                (rgb >> 0 & 255)/255f);
    }

    public static float checkComponent(String name, double c){
        if (!(0 <= c && c <= 1)){
            LogUtils.log(RpgU.getInstance(), name + " exceeded limit: " + c, NamedTextColor.YELLOW);
            return Math.clamp((float) c, 0f, 1f);
        }
        return (float) c;
    }

    public static Color vectorToColor(Vector rgb){
        return new Color(
                checkComponent("red", rgb.getX()),
                checkComponent("blue", rgb.getY()),
                checkComponent("green", rgb.getZ())
        );
    }

    public static Vector mapColor(Block block){
        return rgbToColor(Nms.get().getMapColor(block.getType()));
    }

    public static Vector trace(int x, int y, Location location, Vector direction){
        if ((x+y) % 2 == 0){ // adding transparent things
            return trace(x, y, location, direction, FluidCollisionMode.ALWAYS, false, new HashSet<>());
        }
        return trace(x, y, location, direction, FluidCollisionMode.ALWAYS, true, new HashSet<>());
    }

    public static Vector trace(int x, int y, Location location, Vector direction, FluidCollisionMode fluidCollision, boolean ignorePassable, Set<Material> ignoreCollision){
        final double maxDistance = 200d;

        RayTraceResult traceResult = location.getWorld().rayTraceBlocks(
                location,
                direction,
                maxDistance,
                fluidCollision,
                ignorePassable,
                b -> !ignoreCollision.contains(b.getType())
        );

        if (traceResult == null) return getSkyColor(location, direction);

        Block block = traceResult.getHitBlock();
        BlockFace blockFace = traceResult.getHitBlockFace();
        assert blockFace != null;
        assert block != null;

        if (block.getType() == Material.WATER){
           return trace(x, y, location, direction, FluidCollisionMode.NEVER, ignorePassable, ignoreCollision).multiply(0.3)
                   .add(
                           rgbToColor(Nms.get().getBiomeWrapper(block.getBiome()).waterColor()).multiply(0.7)// water transparency
                   );
        }
        Vector color = mapColor(block);
        if (ignorePassable){ // so block must be solid
            if (color.isZero() || block.getType().isTransparent()){
                ignoreCollision.add(block.getType());
                return trace(x, y, location, direction, fluidCollision, true, ignoreCollision); // trying to trace solid
            }
        }

        applyShading(color, block, blockFace);
        return color;
//        // adding transparent and passable things
//        if ((x+y) % 2 == 0) {
//            RayTraceResult passableTrace = location.getWorld().rayTraceBlocks(
//                    location,
//                    direction,
//                    maxDistance,
//                    FluidCollisionMode.NEVER,
//                    false,
//                    b -> !ignoreCollision.contains(b.getType())
//            );
//            if (passableTrace == null) return color;
//            Block passableBlock = passableTrace.getHitBlock();
//            assert passableBlock != null;
//            if (!passableBlock.isPassable()) return color;
//            Vector passableColor = mapColor(passableBlock);
//            assert passableTrace.getHitBlockFace() != null;
//            applyShading(passableColor, passableBlock, passableTrace.getHitBlockFace());
//
//            color.multiply(0.5).add(passableColor.multiply(0.5));
//        } else {
//            if (color.isZero()) { // transparent
//                ignoreCollision.add(block.getType());
//                return trace(x, y, location, direction, fluidCollision, ignorePassable, ignoreCollision);
//            }
//        }
    }

    public static void applyShading(Vector color, Block block, BlockFace face){
        applyLightShading(color, block, face);
        applyFaceShading(color, face);
    }

    public static void applyBiomeShading(Vector color, Block block){
        Nms.get().getBiomeWrapper(block.getBiome()).
    }
    public static void applyLightShading(Vector color, Block block, BlockFace face){
        Block relative = block.getRelative(face);
        float light = relative.getLightLevel();
        color.multiply(light/15f);
    }
    public static void applyFaceShading(Vector color, BlockFace face){
        double mul = switch (face){
            case UP -> 1;
            case DOWN -> 0.6;
            case NORTH, SOUTH -> 0.85; // slightly
            case EAST, WEST -> 0.75; // more
            default -> 1;
        };
        color.multiply(mul);
    }
}














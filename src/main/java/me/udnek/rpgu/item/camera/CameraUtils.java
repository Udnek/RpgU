package me.udnek.rpgu.item.camera;

import me.udnek.coreu.nms.Nms;
import me.udnek.coreu.util.LogUtils;
import me.udnek.rpgu.RpgU;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.bukkit.util.noise.PerlinNoiseGenerator;
import org.jspecify.annotations.NullMarked;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

@NullMarked
public class CameraUtils {

    public static Vector getSkyColor(Location location, Vector direction) {
        long time = location.getWorld().getTime();
        // night
        if (13_000 <= time && time <= 22_300){
            // stars
            double noise = PerlinNoiseGenerator.getNoise(
                    direction.getX(),
                    direction.getY(),
                    direction.getZ(),
                    1,
                    10,
                    1
            );
            noise = Math.clamp((noise+1)/2.0, 0, 1); // floating point precision i assume
            if (noise > 0.7) {
                return new Vector(1f, 0f, 0f);
            } else {
                return new Vector(noise, noise, noise);
            }
        }

        double dayTimeMultiplier = transform((float) Math.sin(time/24_000f * Math.PI*2),
                0, 1, 0.4f, 1);

        Vector skyColor = rgbToVector(Nms.get().getBiomeWrapper(location.getWorld().getBiome(location)).skyColor());
        return skyColor.multiply(dayTimeMultiplier);
    }

    public static Vector rgbToVector(int rgb){
        return new Vector(
                (rgb >> 16 & 255)/255f,
                (rgb >> 8 & 255)/255f,
                (rgb >> 0 & 255)/255f);
    }

    public static float checkComponent(String name, double c){
        if (!(0 <= c && c <= 1)){
            LogUtils.log(RpgU.getInstance(), name + " exceeded limit: " + c, NamedTextColor.YELLOW);
            return 1;
            //return Math.clamp((float) c, 0f, 1f);
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
        return rgbToVector(Nms.get().getMapColor(block.getType()));
    }

    public static Vector trace(int x, int y, Location location, Vector direction){
        if ((x+y) % 2 == 0){ // adding transparent things
            return trace(x, y, location, direction, FluidCollisionMode.ALWAYS, false, new HashSet<>());
        }
        return trace(x, y, location, direction, FluidCollisionMode.ALWAYS, true, new HashSet<>());
    }

    public static Vector trace(int x, int y, Location location, Vector direction, FluidCollisionMode fluidCollision, boolean ignorePassable, Set<Material> ignoreCollision){
        final double maxDistance = 1000d;

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
            Vector behindWater = trace(x, y, location, direction, FluidCollisionMode.NEVER, ignorePassable, ignoreCollision);
            Vector water = rgbToVector(Nms.get().getBiomeWrapper(block.getBiome()).waterColor());
            applyLightShading(water, block, blockFace);
            return behindWater.multiply(0.5).add(water.multiply(0.5)); // water transparency = 50%
        }

        if (ignorePassable && isTransparent(block)){ // so block must be solid but it is transparent
            ignoreCollision.add(block.getType());
            return trace(x, y, location, direction, fluidCollision, true, ignoreCollision); // trying to trace solid
        }

        Vector color = biomeOrMapColor(block);
        applyShading(color, block, blockFace);
        applyAmbientOcclusion(color, block, blockFace, traceResult.getHitPosition().toLocation(block.getWorld()));
        return color;
    }

    public static boolean isTransparent(Block block){
        return block.getType().isTransparent();
    }

    public static void applyAmbientOcclusion(Vector color, Block block, BlockFace face, Location hitPos){
        double ao = CameraAo.ambientOcclusion(block, face, hitPos);
        color.multiply(transform((float) ao, 0f, 1f, 0.6f, 1f));
    }

    public static void applyShading(Vector color, Block block, BlockFace face){
        applyLightShading(color, block, face);
        applyFaceShading(color, face);
    }

    public static Vector biomeOrMapColor(Block block){
        Material material = block.getType();
        return switch (material) {
            case GRASS_BLOCK, TALL_GRASS, SHORT_GRASS, LARGE_FERN, FERN, SUGAR_CANE, BUSH -> {
                int rgb = Nms.get().getBiomeWrapper(block.getBiome()).grassColor(block.getX(), block.getZ());
                yield rgbToVector(rgb).multiply(rgbToVector(NamedTextColor.GRAY.value())); // darken color
            }
            case OAK_LEAVES, JUNGLE_LEAVES, ACACIA_LEAVES, DARK_OAK_LEAVES, MANGROVE_LEAVES, VINE -> {
                int rgb = Nms.get().getBiomeWrapper(block.getBiome()).foliageColor();
                yield rgbToVector(rgb).multiply(rgbToVector(NamedTextColor.GRAY.value())); // darken color
            }
            case LEAF_LITTER -> {
                int rgb = Nms.get().getBiomeWrapper(block.getBiome()).dryFoliageColor();
                yield rgbToVector(rgb).multiply(rgbToVector(NamedTextColor.GRAY.value())); // darken color
            }
            // SEA
            case SEAGRASS, TALL_SEAGRASS -> rgbToVector(NamedTextColor.DARK_GREEN.value());
            case KELP_PLANT, KELP -> rgbToVector(NamedTextColor.GREEN.value());
            // TORCHES
            case TORCH, WALL_TORCH -> rgbToVector(NamedTextColor.YELLOW.value());
            case REDSTONE_TORCH, REDSTONE_WALL_TORCH -> rgbToVector(NamedTextColor.RED.value());
            case SOUL_TORCH, SOUL_WALL_TORCH -> rgbToVector(NamedTextColor.AQUA.value());
            // MISC
            case GLASS -> rgbToVector(NamedTextColor.WHITE.value());
            case LAVA -> rgbToVector(TextColor.color(1f, 165/255f, 0f).value());
            case MYCELIUM -> rgbToVector(0x6f5f59);
            default -> mapColor(block);

            // TODO POTS
            // TODO ANVIL
            // TODO FIX WATERLOGGED
            // TODO FIX NETHER
            // TODO FIRE SIZE
        };
    }

    public static void applyLightShading(Vector color, Block block, BlockFace face){
        Block relative = block.getRelative(face);
        float light = relative.getLightLevel();
        color.multiply(transform(light, 0, 15, 0.4f, 1));
    }

    public static void applyFaceShading(Vector color, BlockFace face){
        double mul = switch (face){
            case UP -> 1;
            case DOWN -> 0.7;
            case NORTH, SOUTH -> 0.90; // slightly
            case EAST, WEST -> 0.80; // more
            default -> 1;
        };
        color.multiply(mul);
    }

    public static float transform(float value, float minFrom, float maxFrom, float minTo, float maxTo){
        return  (value - minFrom) // zero origin
                /(maxFrom - minFrom)  // normalize
                *(maxTo - minTo)  //stretch
                + minTo; // origin
    }
}

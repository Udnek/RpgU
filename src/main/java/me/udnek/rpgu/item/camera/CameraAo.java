package me.udnek.rpgu.item.camera;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class CameraAo {

    public static boolean isOccluding(Location location){
        if (!location.isChunkLoaded()) return false;
        return location.getBlock().getType().isOccluding();
    }

    public static Location relative(Location location, BlockFace face){
        return location.clone().add(face.getModX(), face.getModY(), face.getModZ());
    }

    public static double cornerAmbientOcclusion(Location center, BlockFace u, BlockFace v, int uMul, int vMul){
        u = (uMul == -1 ? u.getOppositeFace() : u);
        v = (vMul == -1 ? v.getOppositeFace() : v);

        double sideU = isOccluding(relative(center, u)) ? 1 : 0;
        double sideV = isOccluding(relative(center, v)) ? 1 : 0;
        double diagonal = isOccluding(relative(relative(center, u), v)) ? 1 : 0;

        return (3.0 - (sideU+sideV+diagonal))/3.0;
    }

    public static double ambientOcclusion(Block hitBlock, BlockFace face, Location hitPos){
        BlockFace u, v;
        double uFrac, vFrac;
        switch (face){
            case UP, DOWN -> {
                 u = BlockFace.SOUTH; // +z
                 v = BlockFace.EAST; // +x
                 uFrac = hitBlock.getZ() - hitPos.z();
                 vFrac = hitBlock.getX() - hitPos.x();
            }
            case NORTH, SOUTH -> {
                u = BlockFace.UP; // +y
                v = BlockFace.EAST; // +x
                uFrac = hitBlock.getY() - hitPos.y();
                vFrac = hitBlock.getX() - hitPos.x();
            }
            case EAST, WEST -> {
                u = BlockFace.UP; // +y
                v = BlockFace.SOUTH; // +z
                uFrac = hitBlock.getY() - hitPos.y();
                vFrac = hitBlock.getZ() - hitPos.z();
            }
            default -> throw new RuntimeException("Incorrect face: " + face);
        }
        uFrac = Math.abs(uFrac); // fixes on negative coordinates?
        vFrac = Math.abs(vFrac); //

        Location center = hitBlock.getRelative(face).getLocation();
        double q1 = cornerAmbientOcclusion(center, u, v, -1, -1); // -u -v
        double q2 = cornerAmbientOcclusion(center, u, v, 1, -1); // +u -v
        double q3 = cornerAmbientOcclusion(center, u, v, 1, 1); // +u +v
        double q4 = cornerAmbientOcclusion(center, u, v, -1, 1); // -u +v

        // bilineal interpolation
        return bilinearInterpolate(q1, q2, q3, q4, uFrac, vFrac);
    }

    public static double bilinearInterpolate(double q11, double q21, double q22, double q12,
                               double x, double y){
        double x1 = (1-x)*q11 + x*q21;
        double x2 = (1-x)*q12 + x*q22;
        return      (1-y)*x1  + y*x2; // for some reason (y)...(1-y) works, but not (1-y)...(y)
    }
}


















package me.udnek.rpgu.mechanic.enchanting;

import com.google.common.base.Preconditions;
import net.kyori.adventure.sound.SoundStop;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Utils {

    public static @NotNull List<@NotNull Block> getBlocksInCuboid(@NotNull Location center, int xRadius, int yRadius, int zRadius, @NotNull Predicate<@NotNull Block> predicate){
        Preconditions.checkArgument(xRadius >= 0 && yRadius >= 0 && zRadius >= 0, "Radius must be not negative");
        Location clone = center.clone();
        List<Block> result = new ArrayList<>();
        World world = center.getWorld();
        for (double i = center.x()-xRadius; i <= center.x()+xRadius; i++) {
            for (double j = center.y()-yRadius; j <= center.y()+yRadius; j++) {
                for (double k = center.z()-zRadius; k <= center.z()+zRadius; k++) {
                    clone.set(i, j, k);
                    Block block = world.getBlockAt(clone);
                    if (predicate.test(block)) result.add(block);
                }
            }
        }
        return result;
    }

    public static @NotNull List<@NotNull Block> getBlocksInCuboid(@NotNull Location center, int radius){
        return getBlocksInCuboid(center, radius, block -> true);
    }

    public static @NotNull List<@NotNull Block> getBlocksInCuboid(@NotNull Location center, int radius, @NotNull Predicate<@NotNull Block> predicate){
        return getBlocksInCuboid(center, radius, radius, radius, predicate);
    }
}

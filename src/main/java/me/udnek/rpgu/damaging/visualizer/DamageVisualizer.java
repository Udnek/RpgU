package me.udnek.rpgu.damaging.visualizer;


import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.Utils;
import me.udnek.rpgu.damaging.Damage;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;

public class DamageVisualizer {

    public static final int duration = 20;
    public static final int stayDuration = 8;
    public static final int interpolationDuration = 10;

    public static final double redDamage = 20;
    public static final double blackDamage = 100;
    public static final float blackDamageDarkness = 0.5f;

    public static final TextColor zeroDamageColor = TextColor.color(0.7f, 0.7f, 0.7f);
    public static final TextColor subDamageColor = TextColor.color(0.7f, 0.7f, 0.7f);

    public static void visualize(Damage damage, Location location){
        TextDisplay display = (TextDisplay) location.getWorld().spawnEntity(location, EntityType.TEXT_DISPLAY);

        new BukkitRunnable() {
            @Override
            public void run() {
                display.remove();
            }
        }.runTaskLater(RpgU.getInstance(), duration);

        display.setShadowed(true);
        display.setBackgroundColor(Color.fromARGB(0,0,0,0));
        display.setBillboard(Display.Billboard.CENTER);
        display.text(generateText(damage));

        display.setInterpolationDuration(interpolationDuration);

        new BukkitRunnable() {
            @Override
            public void run() {
                Transformation transformation = display.getTransformation();
                transformation.getTranslation().set(0, 1, 0);
                display.setTransformation(transformation);

                display.setInterpolationDelay(0);

            }
        }.runTaskLater(RpgU.getInstance(), stayDuration);
    }

    public static void visualize(Damage damage, Entity entity){
        if (entity instanceof LivingEntity){
            visualize(damage, ((LivingEntity) entity).getEyeLocation().add(0, 0.4, 0));
            return;
        }
        visualize(damage, entity.getLocation());

    }


    public static Component generateText(Damage damage){
        double physicalDamage = damage.getPhysicalDamage();
        double magicalDamage = damage.getMagicalDamage();
        double mainDamage = damage.getTotalDamage();
        TextColor damageColor = getDamageColor(mainDamage);

        Component text =
                Component.text(Utils.roundDoubleValueToTwoDigits(mainDamage)).color(damageColor).decorate(TextDecoration.BOLD)
                        .appendNewline()
                        .append(Component.text(
                                "("
                                +Utils.roundDoubleValueToTwoDigits(physicalDamage)+
                                "+"
                                +Utils.roundDoubleValueToTwoDigits(magicalDamage)+
                                ")").color(subDamageColor).font(Key.key("minecraft:uniform")).decoration(TextDecoration.BOLD, false));

        return text;
    }

    private static TextColor getDamageColor(double mainDamage) {
        float red, green, blue;
        if (mainDamage == 0) return zeroDamageColor;
        if (mainDamage <= redDamage){
            float normalized = (float) Math.min(mainDamage /redDamage, 1);
            red = Math.min(normalized*3f, 1f);
            green = Math.min(3f*(1-normalized), 1);
        } else {
            red = (float) (1 - Math.min( (mainDamage -redDamage)/(blackDamage-redDamage), blackDamageDarkness));
            green = 0;
        }
        blue = 0;

        return TextColor.color(red, green, blue);
    }
}

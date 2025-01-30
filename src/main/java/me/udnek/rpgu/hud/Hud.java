package me.udnek.rpgu.hud;

import me.udnek.itemscoreu.customhud.CustomHud;
import me.udnek.itemscoreu.customhud.CustomHudManager;
import me.udnek.itemscoreu.util.ComponentU;
import me.udnek.itemscoreu.util.Utils;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class Hud implements CustomHud {

    private static final Key HEALTH_FONT = new NamespacedKey(RpgU.getInstance(), "hud/health");
    private static final Key AIR_FONT = new NamespacedKey(RpgU.getInstance(), "hud/air");
    private static final Key FOOD_FONT = new NamespacedKey(RpgU.getInstance(), "hud/food");
    private static final Key SATURATION_FONT = new NamespacedKey(RpgU.getInstance(), "hud/food_saturation");
    private static final Key HEALTH_ABSORPTION_FONT = new NamespacedKey(RpgU.getInstance(), "hud/health_absorption");
    private static final Key PHYSICAL_DEFENSE_FONT = new NamespacedKey(RpgU.getInstance(), "hud/physical_defense");
    private static final Key MAGICAL_DEFENSE_FONT = new NamespacedKey(RpgU.getInstance(), "hud/magical_defense");
    private static final Key HEALTH_NUMBER_FONT = new NamespacedKey(RpgU.getInstance(), "hud/health_number");

    private static final TextColor PHYSICAL_DEFENSE_COLOR = NamedTextColor.WHITE;
    private static final TextColor MAGICAL_DEFENSE_COLOR = NamedTextColor.LIGHT_PURPLE;

    private static final String BASE_PREFIX = "hud.rpgu.stat.level.";
    private static final Component BACKGROUND = Component.translatable("hud.rpgu.stat.background");
    private static final Component ICON = Component.translatable("hud.rpgu.stat.icon");
    private static final Component OVERLAY = Component.translatable("hud.rpgu.stat.overlay");

    private static Hud instance;
    private Hud(){}
    public static @NotNull Hud getInstance() {
        if (instance == null) instance = new Hud();
        return instance;
    }

    @Override
    public @NotNull Component getText(@NotNull Player player) {
        if (player.getGameMode().isInvulnerable()) return Component.empty();
        return ComponentU.textWithNoSpaceDefaultFont(
                -91,
                health(player).append(defense(player)),
                0)
                .append(ComponentU.textWithNoSpaceDefaultFont(10, food(player), 0))
                .append(air(player));
    }

    public @NotNull Component food(@NotNull Player player){
        Component food = Component.translatable(BASE_PREFIX +((int) (Math.ceil(player.getFoodLevel()/20d*77))));
        Component saturation = Component.translatable(BASE_PREFIX+((int) (Math.ceil(player.getSaturation()/20d*72))));

        TextColor iconColor = NamedTextColor.WHITE;
        TextColor barColor;
        if (player.hasPotionEffect(PotionEffectType.HUNGER)) barColor = TextColor.fromHexString("#374b1f");
        else barColor = TextColor.fromHexString("#9d6d43");
        return BACKGROUND.color(ComponentU.NO_SHADOW_COLOR).font(FOOD_FONT)
                .append(food.color(barColor).font(FOOD_FONT))
                .append(saturation.color(NamedTextColor.WHITE).font(SATURATION_FONT))
                .append(ICON.color(iconColor)).append(OVERLAY).color(ComponentU.NO_SHADOW_COLOR).font(FOOD_FONT);
    }

    private @NotNull Component air(@NotNull Player player){
        int maximumAir = player.getMaximumAir();
        int remainingAir = Math.max(player.getRemainingAir(), 0);
        if (remainingAir >= maximumAir) return Component.empty();
        Component text = Component.translatable("hud.rpgu.air.level."+
                ((int) (Math.ceil((double) (maximumAir-remainingAir) /maximumAir*13d))));
        return text.font(AIR_FONT).color(ComponentU.NO_SHADOW_COLOR);
    }
    
    private @NotNull Component defense(@NotNull Player player){
        double mr = Attributes.MAGICAL_RESISTANCE.calculate(player);
        Component magical = Component.translatable(BASE_PREFIX+((int) (Math.ceil(mr*72))));

        double pr = Attributes.PHYSICAL_RESISTANCE.calculate(player);
        Component physical = Component.translatable(BASE_PREFIX+((int) (Math.ceil(pr*71))));

        return BACKGROUND.color(ComponentU.NO_SHADOW_COLOR).font(PHYSICAL_DEFENSE_FONT)
                .append(physical.color(PHYSICAL_DEFENSE_COLOR).font(PHYSICAL_DEFENSE_FONT))
                .append(magical.color(MAGICAL_DEFENSE_COLOR).font(MAGICAL_DEFENSE_FONT))
                .append(ICON.append(OVERLAY).color(ComponentU.NO_SHADOW_COLOR).font(PHYSICAL_DEFENSE_FONT));
    }

    private @NotNull Component health(@NotNull Player player){
        double maxHealth = player.getAttribute(Attribute.MAX_HEALTH).getValue();
        double health = player.getHealth();
        Component healthImage = Component.translatable(BASE_PREFIX+((int) (Math.ceil(health/maxHealth*74))));
        Component absorptionImage;
        if (player.getAbsorptionAmount() > 0){
            double maxAbsorption = player.getAttribute(Attribute.MAX_ABSORPTION).getValue();
            absorptionImage = Component.translatable(BASE_PREFIX+((int) (Math.ceil(player.getAbsorptionAmount()/maxAbsorption*74))));
        } else absorptionImage = Component.empty();

        String rawText = Utils.roundToTwoDigits(health) + "/" + Utils.roundToTwoDigits(maxHealth) + "+" + Utils.roundToTwoDigits(Attributes.HEALTH_REGENERATION.calculate(player));

        int dots = StringUtils.countMatches(rawText, ".");
        int size = (rawText.length()-dots)*4 + dots*2 -1;

        Component text = ComponentU.textWithNoSpaceDefaultFont(
                40-size/2,
                Component.text(rawText).font(HEALTH_NUMBER_FONT).color(NamedTextColor.WHITE),
                size);

        TextColor color;
        if (player.getNoDamageTicks() > 0) color = TextColor.fromHexString("#ff6e6e");
        else if (player.getFreezeTicks() > 0) color = TextColor.fromHexString("#84bcff");
        else if (player.hasPotionEffect(PotionEffectType.POISON)) color = TextColor.fromHexString("#947818");
        else if (player.hasPotionEffect(PotionEffectType.WITHER)) color = TextColor.fromHexString("#341a1a");
        else color = TextColor.color(1f, 0f, 0f);

        return wrap(healthImage.append(absorptionImage.font(HEALTH_ABSORPTION_FONT).color(ComponentU.NO_SHADOW_COLOR)), HEALTH_FONT, color).append(text);
    }

    private @NotNull Component wrap(@NotNull Component text, @NotNull Key font, @NotNull TextColor color){
        return BACKGROUND.color(ComponentU.NO_SHADOW_COLOR)
                .append(text.append(ICON).color(color))
                .append(OVERLAY.color(ComponentU.NO_SHADOW_COLOR))
                .font(font);
    }

    public void register(){
        CustomHudManager.getInstance().addTicket(RpgU.getInstance(), this);
    }
}

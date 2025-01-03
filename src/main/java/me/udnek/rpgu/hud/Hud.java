package me.udnek.rpgu.hud;

import me.udnek.itemscoreu.customhud.CustomHud;
import me.udnek.itemscoreu.customhud.CustomHudManager;
import me.udnek.itemscoreu.util.ComponentU;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class Hud implements CustomHud {

    private final static Key HEALTH_FONT = new NamespacedKey(RpgU.getInstance(), "hud/health");
    private final static Key HEALTH_ABSORPTION_FONT = new NamespacedKey(RpgU.getInstance(), "hud/health_absorption");
    private final static Key PHYSICAL_DEFENSE_FONT = new NamespacedKey(RpgU.getInstance(), "hud/physical_defense");
    private final static Key MAGICAL_DEFENSE_FONT = new NamespacedKey(RpgU.getInstance(), "hud/magical_defense");
    private final static Key HEALTH_NUMBER_FONT = new NamespacedKey(RpgU.getInstance(), "hud/health_number");
    private final static TextColor PHYSICAL_DEFENSE_COLOR = NamedTextColor.WHITE;
    private final static TextColor MAGICAL_DEFENSE_COLOR = NamedTextColor.LIGHT_PURPLE;

    @Override
    public @NotNull Component getText(@NotNull Player player) {
        return ComponentU.textWithNoSpaceDefaultFont(
                -90,
                health(player).append(armor(player)).append(magicalDefense(player)),
                0);
    }

    private @NotNull Component magicalDefense(@NotNull Player player){
        double resistance = Attributes.MAGICAL_RESISTANCE.calculate(player);
        Component text = Component.translatable("hud.rpgu.stat.level."+((int) (Math.ceil(resistance*72))));
        return wrap(text, MAGICAL_DEFENSE_FONT, MAGICAL_DEFENSE_COLOR);
    }

    private @NotNull Component armor(@NotNull Player player){
        double resistance = Attributes.PHYSICAL_RESISTANCE.calculate(player);
        Component text = Component.translatable("hud.rpgu.stat.level."+((int) (Math.ceil(resistance*71))));
        return wrap(text, PHYSICAL_DEFENSE_FONT, PHYSICAL_DEFENSE_COLOR);
    }

    private @NotNull Component health(@NotNull Player player){
        double maxHealth = player.getAttribute(Attribute.MAX_HEALTH).getValue();
        double health = player.getHealth();
        Component healthImage = Component.translatable("hud.rpgu.stat.level."+((int) (Math.ceil(health/maxHealth*74))));
        Component absorptionImage;
        if (player.getAbsorptionAmount() > 0){
            double maxAbsorption = player.getAttribute(Attribute.MAX_ABSORPTION).getValue();
            absorptionImage = Component.translatable("hud.rpgu.stat.level."+((int) (Math.ceil(player.getAbsorptionAmount()/maxAbsorption*74))));
        } else absorptionImage = Component.empty();

        String rawText = ((int) Math.ceil(health)) + "/" + ((int) Math.ceil(maxHealth) + "+" + ((int) Math.ceil(Attributes.HEALTH_REGENERATION.calculate(player))));
        int size = rawText.length()*4-1;
        Component text = ComponentU.textWithNoSpaceDefaultFont(
                40-size/2,
                Component.text(rawText).font(HEALTH_NUMBER_FONT).color(NamedTextColor.WHITE),
                size);

        TextColor color = TextColor.color(1f, 0f, 0f);
        if (player.getNoDamageTicks() > 0) color = NamedTextColor.WHITE;
        else if (player.getFreezeTicks() > 0) color = TextColor.fromHexString("#84bcff");
        else if (player.hasPotionEffect(PotionEffectType.POISON)) color = TextColor.fromHexString("#947818");
        else if (player.hasPotionEffect(PotionEffectType.WITHER)) color = TextColor.fromHexString("#341a1a");

        return wrap(healthImage.append(absorptionImage.font(HEALTH_ABSORPTION_FONT).color(ComponentU.NO_SHADOW_COLOR)), HEALTH_FONT, color).append(text);
    }

    private @NotNull Component wrap(@NotNull Component text, @NotNull Key font, @NotNull TextColor color){
        return Component.translatable("hud.rpgu.stat.background").color(ComponentU.NO_SHADOW_COLOR)
                .append(text.color(color))
                .append(Component.translatable("hud.rpgu.stat.overlay").color(ComponentU.NO_SHADOW_COLOR))
                .font(font);
    }

    public void register(){
        CustomHudManager.getInstance().addTicket(RpgU.getInstance(), this);
    }
}

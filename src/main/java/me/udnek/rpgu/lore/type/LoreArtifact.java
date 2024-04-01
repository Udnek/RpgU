package me.udnek.rpgu.lore.type;

import me.udnek.rpgu.lore.TranslationKeys;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class LoreArtifact{

    public static final TextColor titleColor = TextColor.color(170, 170, 170);
    public static final TextColor mainColor = TextColor.color(85, 85, 255);

    public static Component whenEquippedAsArtifactLine(){
        return Component.translatable(TranslationKeys.whenEquippedAsArtifact)
                .color(titleColor).decoration(TextDecoration.ITALIC, false);
    }

    public static Component toInfoStyle(Component component){
        return Component.translatable(TranslationKeys.equipmentDescriptionLine, component).color(mainColor).decoration(TextDecoration.ITALIC, false);
    }
}

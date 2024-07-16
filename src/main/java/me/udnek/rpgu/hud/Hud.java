package me.udnek.rpgu.hud;

import me.udnek.itemscoreu.customhud.CustomHud;
import me.udnek.itemscoreu.customhud.CustomHudManager;
import me.udnek.itemscoreu.utils.ComponentU;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.equipment.Equippable;
import me.udnek.rpgu.equipment.PlayerEquipment;
import me.udnek.rpgu.equipment.PlayerEquipmentDatabase;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.MainHand;

public class Hud implements CustomHud {

    private final static TextColor NO_SHADOW_COLOR = TextColor.fromHexString("#4e5c24");
    private final static Key FONT = new NamespacedKey(RpgU.getInstance(), "artifact");
    private final static int OFFSET_BETWEEN_IMAGES = 16 + 3;
    private final static int RIGHT_HANDED_OFFSET = 74;
    private final static int LEFT_HANDED_OFFSET = -109;

    @Override
    public Component getText(Player player) {
        // TODO: 6/18/2024 FOR ALL EQUIPMENT

        PlayerEquipment equipment = PlayerEquipmentDatabase.get(player);
        if (equipment.isEmpty()) return Component.empty();

        Component joinedImage = Component.empty();
        int totalImagesSize = 0;

        for (Equippable equippable : equipment.getFullEquipment().values()) {
            if (equippable.getHudImages(player) == null) continue;
            for (Component image : equippable.getHudImages(player)) {
                joinedImage = joinedImage.append(ComponentU.space(OFFSET_BETWEEN_IMAGES)).append(image);
                totalImagesSize += OFFSET_BETWEEN_IMAGES;
            }
        }

        int offset = RIGHT_HANDED_OFFSET;
        if (player.getMainHand() == MainHand.LEFT) offset = LEFT_HANDED_OFFSET-totalImagesSize;

        joinedImage = ComponentU.textWithNoSpace(offset, joinedImage, totalImagesSize);

        return joinedImage.color(NO_SHADOW_COLOR).font(FONT);
/*        PlayerEquipment equipment = PlayerEquipmentDatabase.get(player);

        if (!equipment.hasAnyArtifact()) return Component.empty();

        ArtifactItem item0 = equipment.getArtifactFirst();
        ArtifactItem item1 = equipment.getArtifactSecond();
        ArtifactItem item2 = equipment.getArtifactThird();




        Component hudImage = ComponentU.textWithNoSpace(
                offset,
                Component.translatable(TranslationKeys.artifactHud),
                62
        );

        offset += 3;
        Component result =
                generateArtifactComponent(player, item0, offset).append(
                generateArtifactComponent(player, item1, offset + 16 + 4)).append(
                generateArtifactComponent(player, item2, offset + (16 + 4) * 2));

        return applyFontAndColor(hudImage.append(result));*/
    }


/*    private Component applyFontAndColor(Component text){
        return text.font(FONT).color(NO_SHADOW_COLOR);
    }
    private Component generateArtifactComponent(Player player, ArtifactItem item, int offset){
        if (item == null) return Component.empty();
        //Component cooldown  = cooldownToComponent(item.getHudCooldownProgress(player));
        //Component image = item.getArtifactImage();
        // TODO: 6/18/2024 FIX
        return ComponentU.textWithNoSpace(offset, Component.empty(), 0);
        //Component image =
        //return ComponentU.textWithNoSpace(offset, image.append(cooldown), 16);
    }
    private Component cooldownToComponent(float cd){
        cd *= 16;
        return Component.translatable(TranslationKeys.artifactCooldown + Math.round(cd));
    }*/

    public void register(){
        CustomHudManager.addTicket(RpgU.getInstance(), this);
    }
}

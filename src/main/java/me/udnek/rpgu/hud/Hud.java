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
        if (player.getMainHand() == MainHand.LEFT) offset = LEFT_HANDED_OFFSET - totalImagesSize;

        joinedImage = ComponentU.textWithNoSpace(offset, joinedImage, totalImagesSize);

        return joinedImage.color(NO_SHADOW_COLOR).font(FONT);
    }

    public void register(){
        CustomHudManager.addTicket(RpgU.getInstance(), this);
    }
}

package me.udnek.rpgu.hud;

import me.udnek.itemscoreu.customequipmentslot.SingleSlot;
import me.udnek.itemscoreu.customhud.CustomHud;
import me.udnek.itemscoreu.customhud.CustomHudManager;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.ComponentU;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.EquippableItemComponent;
import me.udnek.rpgu.equipment.PlayerEquipment;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.MainHand;
import org.jetbrains.annotations.NotNull;

public class Hud implements CustomHud {

    private final static Key FONT = new NamespacedKey(RpgU.getInstance(), "artifact");
    private final static int OFFSET_BETWEEN_IMAGES = 16 + 3;
    private final static int RIGHT_HANDED_OFFSET = 74;
    private final static int LEFT_HANDED_OFFSET = -109;

    Component joinedImage = Component.empty();
    int totalImagesSize;

    @Override
    public @NotNull Component getText(Player player) {
        PlayerEquipment equipment = PlayerEquipment.get(player);
        if (equipment.isEmpty()) return Component.empty();

        joinedImage = Component.empty();
        totalImagesSize = 0;

        equipment.getEquipment(new PlayerEquipment.EquipmentConsumer() {
            @Override
            public void accept(@NotNull SingleSlot slot, @NotNull CustomItem customItem) {
                EquippableItemComponent equippable = customItem.getComponentOrDefault(ComponentTypes.EQUIPPABLE_ITEM);
                Component hudImage = equippable.getHudImage(customItem, player);
                if (hudImage == null) return;
                joinedImage.append(ComponentU.space(OFFSET_BETWEEN_IMAGES)).append(hudImage);
                totalImagesSize += OFFSET_BETWEEN_IMAGES;
            }
        });

        if (totalImagesSize == 0) return Component.empty();
        int offset = (player.getMainHand() == MainHand.LEFT) ? LEFT_HANDED_OFFSET - totalImagesSize : RIGHT_HANDED_OFFSET;
        joinedImage = ComponentU.textWithNoSpace(offset, joinedImage, totalImagesSize-1);
        return joinedImage.color(ComponentU.NO_SHADOW_COLOR).font(FONT);
    }

    public void register(){
        CustomHudManager.addTicket(RpgU.getInstance(), this);
    }
}

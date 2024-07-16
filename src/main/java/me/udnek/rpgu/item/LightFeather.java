package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.customplayerdata.CustomPlayerDatabase;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.item.abstraction.ArtifactItem;
import me.udnek.rpgu.lore.LoreUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.TriState;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LightFeather extends ConstructableCustomItem implements ArtifactItem {

    private static final NamespacedKey featherEquippedNamespace = new NamespacedKey(RpgU.getInstance(), "light_feather_equipped");

    @Override
    public Material getMaterial() {
        return Material.GUNPOWDER;
    }
    @Override
    public String getRawId() {
        return "light_feather";
    }

    @Override
    protected void modifyFinalItemStack(ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);
        LoreUtils.generateFullLoreAndApply(itemStack);
    }


/*    @Override
    public void onEquipped(Player player, ItemStack itemStack) {
        player.sendMessage(Component.text("LIGHT FEATHER EQUIPED").color(TextColor.color(0f, 1f, 0f)));
        CustomPlayerDatabase.getData(player).setValue(featherEquippedNamespace, true);
        player.setAllowFlight(true);
        player.setFlyingFallDamage(TriState.TRUE);
        //CustomPlayerDatabase.printToConsole();
    }

    @Override
    public void onUnequipped(Player player, ItemStack itemStack) {
        player.sendMessage(Component.text("LIGHT FEATHER UNEQUIPED").color(TextColor.color(1f, 0f, 0f)));
        CustomPlayerDatabase.getData(player).setValue(featherEquippedNamespace, false);
        player.setAllowFlight(false);
        //CustomPlayerDatabase.printToConsole();
    }

    public static void isEquipped(Player player){
        CustomPlayerDatabase.getData(player).getBooleanValue(featherEquippedNamespace);
    }*/

    @Override
    public Integer getCustomModelData() {
        return 3100;
    }
}

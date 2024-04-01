package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customitem.CustomModelDataItem;
import me.udnek.itemscoreu.customplayerdata.CustomPlayerDatabase;
import me.udnek.itemscoreu.utils.TranslateUtils;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.item.abstracts.ArtifactItem;
import me.udnek.rpgu.lore.LoreConstructor;
import me.udnek.rpgu.lore.TranslationKeys;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.TriState;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class LightFeather extends CustomModelDataItem implements ArtifactItem {

    private static final NamespacedKey featherEquippedNamespace = new NamespacedKey(RpgU.getInstance(), "light_feather_equipped");

    @Override
    public Material getMaterial() {
        return Material.GUNPOWDER;
    }

    @Override
    protected String getRawDisplayName() {
        return TranslationKeys.itemPrefix + getItemName();
    }

    @Override
    protected String getItemName() {
        return "light_feather";
    }

    @Override
    protected void modifyFinalItemMeta(ItemMeta itemMeta) {
        super.modifyFinalItemMeta(itemMeta);
        List<Component> lore = TranslateUtils.getTranslated("item.rpgu.light_feather.description.0");
        LoreConstructor loreConstructor = new LoreConstructor();
        loreConstructor.setArtifactInformation(lore);
        loreConstructor.apply(itemMeta);
    }

    @Override
    public void onBeingEquipped(Player player, ItemStack itemStack) {
        player.sendMessage(Component.text("LIGHT FEATHER EQUIPED").color(TextColor.color(0f, 1f, 0f)));
        CustomPlayerDatabase.getData(player).setValue(featherEquippedNamespace, true);
        player.setAllowFlight(true);
        player.setFlyingFallDamage(TriState.TRUE);
        //CustomPlayerDatabase.printToConsole();
    }

    @Override
    public void onBeingUnequipped(Player player, ItemStack itemStack) {
        player.sendMessage(Component.text("LIGHT FEATHER UNEQUIPED").color(TextColor.color(1f, 0f, 0f)));
        CustomPlayerDatabase.getData(player).setValue(featherEquippedNamespace, false);
        player.setAllowFlight(false);
        //CustomPlayerDatabase.printToConsole();
    }

    public static void isEquipped(Player player){
        CustomPlayerDatabase.getData(player).getBooleanValue(featherEquippedNamespace);
    }

    @Override
    public int getCustomModelData() {
        return 3100;
    }
}

package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customitem.CustomModelDataItem;
import me.udnek.itemscoreu.utils.TranslateUtils;
import me.udnek.rpgu.equipment.PlayerWearingEquipmentTask;
import me.udnek.rpgu.item.abstracts.ArtifactItem;
import me.udnek.rpgu.lore.LoreConstructor;
import me.udnek.rpgu.lore.TranslationKeys;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LifeCrystal extends CustomModelDataItem implements ArtifactItem {

    public final static int duration = 20*5 + PlayerWearingEquipmentTask.DELAY;

    @Override
    public int getCustomModelData() {
        return 3101;
    }

    @Override
    protected void modifyFinalItemMeta(ItemMeta itemMeta) {
        super.modifyFinalItemMeta(itemMeta);
        LoreConstructor loreConstructor = new LoreConstructor();
        loreConstructor.setArtifactInformation(TranslateUtils.getTranslated(
                "item.rpgu.life_crystal.description.0",
                "item.rpgu.life_crystal.description.1",
                "item.rpgu.life_crystal.description.2"));
        loreConstructor.apply(itemMeta);
    }

    @Override
    public void onWhileBeingEquipped(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, duration, 4, true, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, duration, 1, true, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, duration, 1, true, true));
    }

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
        return "life_crystal";
    }
}

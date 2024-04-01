package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customitem.CustomModelDataItem;
import me.udnek.rpgu.damaging.DamageEvent;
import me.udnek.rpgu.item.abstracts.ArmorItem;
import me.udnek.rpgu.lore.TranslationKeys;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HungryHorrorChestplate extends CustomModelDataItem implements ArmorItem {
    @Override
    public int getCustomModelData() {
        return 3100;
    }

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_CHESTPLATE;
    }


    @Override
    protected void modifyFinalItemMeta(ItemMeta itemMeta) {
        super.modifyFinalItemMeta(itemMeta);
        ((ArmorMeta) itemMeta).setTrim(new ArmorTrim(TrimMaterial.LAPIS, Registry.TRIM_PATTERN.get(new NamespacedKey("rpgu", "hungry_horror"))));
        itemMeta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
    }

    @Override
    public void onPlayerAttacksWhenEquipped(Player player, DamageEvent damageEvent) {

        if (!damageEvent.getEvent().isCritical()) return;

        PotionEffect potionEffect = player.getPotionEffect(PotionEffectType.ABSORPTION);
        int applied;
        if (potionEffect == null) {applied = -1;}
        else { applied = potionEffect.getAmplifier();}

        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 40, Math.min(applied+1, 4), false, true));
        //for (int lvl = 0; lvl <= Math.min(applied+1, 4) ; lvl++) {
        //    player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, (4-lvl)*40+20, lvl, false, true));
        //}

    }

    @Override
    protected String getRawDisplayName() {
        return TranslationKeys.itemPrefix + getItemName();
    }

    @Override
    protected String getItemName() {
        return "hungry_horror_chestplate";
    }
}

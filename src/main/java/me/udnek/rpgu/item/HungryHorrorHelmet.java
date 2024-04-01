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

public class HungryHorrorHelmet extends CustomModelDataItem implements ArmorItem {
    @Override
    public int getCustomModelData() {
        return 3100;
    }

    @Override
    protected void modifyFinalItemMeta(ItemMeta itemMeta) {
        super.modifyFinalItemMeta(itemMeta);
        super.modifyFinalItemMeta(itemMeta);
        ((ArmorMeta) itemMeta).setTrim(new ArmorTrim(TrimMaterial.LAPIS, Registry.TRIM_PATTERN.get(new NamespacedKey("rpgu", "hungry_horror"))));
        itemMeta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
    }

    @Override
    public void onPlayerAttacksWhenEquipped(Player player, DamageEvent damageEvent) {
        damageEvent.getDamage().multiplyPhysicalDamage(0.5);

        if (!damageEvent.getEvent().isCritical()) return;

        PotionEffect potionEffect = player.getPotionEffect(PotionEffectType.INCREASE_DAMAGE);
        int applied;
        if (potionEffect == null) {applied = -1;}
        else { applied = potionEffect.getAmplifier();}
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40, Math.min(applied+1, 4), false, true));
    }

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_HELMET;
    }

    @Override
    protected String getRawDisplayName() {
        return TranslationKeys.itemPrefix + getItemName();
    }

    @Override
    protected String getItemName() {
        return "hungry_horror_helmet";
    }
}

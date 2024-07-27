package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.rpgu.item.abstraction.ArmorItem;
import me.udnek.rpgu.lore.LoreUtils;
import me.udnek.rpgu.mechanic.damaging.DamageEvent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HungryHorrorChestplate extends ConstructableCustomItem implements ArmorItem {
    @Override
    public Integer getCustomModelData() {
        return 3100;
    }

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_CHESTPLATE;
    }

    @Override
    public String getRawId() {
        return "hungry_horror_chestplate";
    }

    @Override
    public ItemFlag[] getTooltipHides() {
        return new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES};
    }

    @Override
    public ArmorTrim getArmorTrim() {
        return new ArmorTrim(TrimMaterial.LAPIS, Registry.TRIM_PATTERN.get(new NamespacedKey("rpgu", "hungry_horror")));
    }

    @Override
    protected void modifyFinalItemStack(ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);
        LoreUtils.generateFullLoreAndApply(itemStack);
    }

    @Override
    public void onPlayerAttacksWhenEquipped(Player player, CustomEquipmentSlot slot, DamageEvent event) {
        if (!event.isCritical()) return;

        PotionEffect potionEffect = player.getPotionEffect(PotionEffectType.ABSORPTION);
        int applied;
        if (potionEffect == null) {applied = -1;}
        else { applied = potionEffect.getAmplifier();}

        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 40, Math.min(applied+1, 4), false, true));
        //for (int lvl = 0; lvl <= Math.min(applied+1, 4) ; lvl++) {
        //    player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, (4-lvl)*40+20, lvl, false, true));
        //}
    }
}

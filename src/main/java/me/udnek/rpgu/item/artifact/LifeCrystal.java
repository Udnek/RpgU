/*
package me.udnek.rpgu.item.artifact;

import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.rpgu.equipment.PlayerWearingEquipmentTask;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.item.abstraction.ArtifactItem;
import me.udnek.rpgu.lore.LoreUtils;
import me.udnek.rpgu.util.ExtraDescribed;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class LifeCrystal extends ConstructableCustomItem implements ExtraDescribed, ArtifactItem {

    public final static int duration = 20*5 + PlayerWearingEquipmentTask.DELAY;

    @Override
    public Integer getCustomModelData() {
        return 3101;
    }
    @Override
    public String getRawId() {
        return "life_crystal";
    }

    @Override
    public Integer getMaxStackSize() {return 1;}

    @Override
    protected void modifyFinalItemStack(ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);
        LoreUtils.generateFullLoreAndApply(itemStack);
    }

    @Override
    public void tickBeingEquipped(Player player, CustomEquipmentSlot slot) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, duration, 4, true, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, duration, 1, true, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, duration, 1, true, true));
    }

    @Override
    public Material getMaterial() {
        return Material.GUNPOWDER;
    }


    @Override
    public Map<CustomEquipmentSlot, Pair<Integer, Integer>> getExtraDescription() {
        return ExtraDescribed.getSimple(EquipmentSlots.ARTIFACT, 3);
    }
}
*/

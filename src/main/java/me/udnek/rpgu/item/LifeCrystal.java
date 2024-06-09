package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.rpgu.attribute.equipmentslot.EquipmentSlots;
import me.udnek.rpgu.equipment.PlayerWearingEquipmentTask;
import me.udnek.rpgu.item.abstraction.ArtifactItem;
import me.udnek.rpgu.item.abstraction.ExtraDescriptionItem;
import me.udnek.rpgu.item.abstraction.RpgUCustomItem;
import me.udnek.rpgu.lore.LoreUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import oshi.util.tuples.Pair;

import java.util.Map;

public class LifeCrystal extends ArtifactItem implements ExtraDescriptionItem {

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
    public void tickBeingEquipped(Player player) {
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
        return ExtraDescriptionItem.getSimple(EquipmentSlots.ARTIFACT, 3);
    }
}

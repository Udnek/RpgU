package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customattribute.AttributeUtils;
import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.mechanic.damaging.DamageEvent;
import me.udnek.rpgu.item.abstraction.ArmorItem;
import me.udnek.rpgu.lore.LoreUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HungryHorrorHelmet extends ConstructableCustomItem implements ArmorItem {
    @Override
    public Integer getCustomModelData() {
        return 3100;
    }
    @Override
    public String getRawId() {
        return "hungry_horror_helmet";
    }

    @Override
    public ItemFlag[] getTooltipHides() {
        return new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ARMOR_TRIM};
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
    public boolean getAddDefaultAttributes() {return true;}

    @Override
    public Map<Attribute, List<AttributeModifier>> getAttributes() {
        HashMap<Attribute, List<AttributeModifier>> map = new HashMap<>();
        map.put(Attribute.GENERIC_ATTACK_DAMAGE, Collections.singletonList(
                new AttributeModifier(AttributeUtils.UUIDFromSeed("A smdfmos"), "", -0.5, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlotGroup.HEAD)
        ));
        return map;
    }

    @Override
    public void onPlayerAttacksWhenEquipped(Player player, CustomEquipmentSlot slot, DamageEvent event) {
        if (!event.isCritical()) return;

        PotionEffect potionEffect = player.getPotionEffect(PotionEffectType.STRENGTH);
        int applied;
        if (potionEffect == null) {applied = -1;}
        else { applied = potionEffect.getAmplifier();}
        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 40, Math.min(applied+1, 4), false, true));
    }

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_HELMET;
    }


}

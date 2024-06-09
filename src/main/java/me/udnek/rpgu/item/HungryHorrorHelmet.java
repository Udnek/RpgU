package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlots;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.damaging.DamageEvent;
import me.udnek.rpgu.item.abstraction.ArmorItem;
import me.udnek.rpgu.item.abstraction.ExtraDescriptionItem;
import me.udnek.rpgu.item.abstraction.RpgUCustomItem;
import me.udnek.rpgu.lore.LoreUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import oshi.util.tuples.Pair;

import java.util.Map;

public class HungryHorrorHelmet extends ArmorItem {
    private final CustomAttributesContainer container = new CustomAttributesContainer.Builder()
            .add(Attributes.MAGICAL_DAMAGE, -0.5, AttributeModifier.Operation.MULTIPLY_SCALAR_1, CustomEquipmentSlots.HEAD)
            .build();
    @Override
    public Integer getCustomModelData() {
        return 3100;
    }
    @Override
    public String getRawId() {
        return "hungry_horror_helmet";
    }

    @Override
    protected ItemFlag[] getTooltipHides() {
        return new ItemFlag[]{ItemFlag.HIDE_ARMOR_TRIM, ItemFlag.HIDE_ATTRIBUTES};
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
    public CustomAttributesContainer getDefaultCustomAttributes() {
        return container;
    }

    @Override
    public void onPlayerAttacksWhenEquipped(Player player, DamageEvent damageEvent) {
        if (!damageEvent.getHandlerEvent().isCritical()) return;

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

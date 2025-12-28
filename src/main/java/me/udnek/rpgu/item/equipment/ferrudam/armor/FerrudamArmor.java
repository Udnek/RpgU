package me.udnek.rpgu.item.equipment.ferrudam.armor;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.coreu.custom.attribute.AttributeUtils;
import me.udnek.coreu.custom.item.RepairData;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.item.AbstractArmor;
import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class FerrudamArmor extends AbstractArmor {


    public FerrudamArmor(@NotNull Material material, @NotNull String id, @NotNull String engName, @NotNull String ruName) {
        super(material, id, "ferrudam", engName, ruName);
    }

    @Override
    public @Nullable RepairData initializeRepairData() {return new RepairData(Items.FERRUDAM_INGOT);}

    @Override
    protected void modifyFinalItemStack(@NotNull ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);
        EquipmentSlot slot = Objects.requireNonNull(getMaterial().getDefaultData(DataComponentTypes.EQUIPPABLE)).slot();
        AttributeUtils.appendAttribute(itemStack, Attribute.ATTACK_DAMAGE, new NamespacedKey(RpgU.getInstance(), "base_attack_damage_" + slot),
                0.04, AttributeModifier.Operation.ADD_SCALAR, slot.getGroup());
    }
}

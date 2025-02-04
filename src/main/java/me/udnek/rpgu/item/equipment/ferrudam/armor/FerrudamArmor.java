package me.udnek.rpgu.item.equipment.ferrudam.armor;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Equippable;
import me.udnek.itemscoreu.customattribute.AttributeUtils;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.RepairData;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.item.Items;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class FerrudamArmor extends ConstructableCustomItem {

    @Override
    public @Nullable List<ItemFlag> getTooltipHides() {return List.of(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});}

    @Override
    public boolean addDefaultAttributes() {return true;}

    @Override
    public @Nullable RepairData initializeRepairData() {return new RepairData(Items.FERRUDAM_INGOT);}


    @Override
    public @Nullable DataSupplier<Equippable> getEquippable() {
        Equippable equippable = Equippable
                .equippable(getMaterial().getEquipmentSlot())
                .assetId(new NamespacedKey(RpgU.getInstance(), "ferrudam")).build();
        return DataSupplier.of(equippable);
    }

    @Override
    public void initializeAdditionalAttributes(@NotNull ItemStack itemStack) {
        super.initializeAdditionalAttributes(itemStack);
        EquipmentSlot slot = getMaterial().getDefaultData(DataComponentTypes.EQUIPPABLE).slot();
        AttributeUtils.appendAttribute(itemStack, Attribute.ATTACK_DAMAGE, new NamespacedKey(RpgU.getInstance(), "base_attack_damage_" + slot),
                0.04, AttributeModifier.Operation.ADD_SCALAR, slot.getGroup());
    }
}

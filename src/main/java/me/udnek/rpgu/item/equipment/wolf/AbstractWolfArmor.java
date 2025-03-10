package me.udnek.rpgu.item.equipment.wolf;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Equippable;
import me.udnek.itemscoreu.customattribute.CustomAttributeModifier;
import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customcomponent.instance.CustomItemAttributesComponent;
import me.udnek.itemscoreu.customequipmentslot.slot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.RepairData;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.item.Items;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractWolfArmor extends ConstructableCustomItem {

    @Override
    public @Nullable List<ItemFlag> getTooltipHides() {return List.of(ItemFlag.HIDE_ATTRIBUTES);}

    @Override
    public @Nullable DataSupplier<Equippable> getEquippable() {
        Equippable build = new ItemStack(getMaterial()).getData(DataComponentTypes.EQUIPPABLE).toBuilder()
                .assetId(new NamespacedKey(RpgU.getInstance(), "wolf")).build();
        return DataSupplier.of(build);
    }

    @Override
    public @Nullable DataSupplier<Integer> getMaxDamage() {
        return DataSupplier.of((int) (getMaterial().getDefaultData(DataComponentTypes.MAX_DAMAGE) / 3.7));
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        CustomAttributeModifier attributeModifier = new CustomAttributeModifier(1, AttributeModifier.Operation.ADD_NUMBER, CustomEquipmentSlot.getFromVanilla(getEquippable().get().slot().getGroup()));
        getComponents().set(new CustomItemAttributesComponent(new CustomAttributesContainer.Builder().add(Attributes.MAGICAL_POTENTIAL, attributeModifier).build()));
    }

    @Override
    public @Nullable RepairData initializeRepairData() {
        return new RepairData(Items.WOLF_PELT);
    }
}

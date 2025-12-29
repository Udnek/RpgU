package me.udnek.rpgu.item.equipment.wolf;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.coreu.custom.attribute.CustomAttributeModifier;
import me.udnek.coreu.custom.attribute.CustomAttributesContainer;
import me.udnek.coreu.custom.component.instance.CustomAttributedItem;
import me.udnek.coreu.custom.equipment.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.item.RepairData;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.item.AbstractArmor;
import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class AbstractWolfArmor extends AbstractArmor {

    public AbstractWolfArmor(@NotNull Material material, @NotNull String id, @NotNull String engName, @NotNull String ruName) {
        super(material, id, "wolf", engName, ruName);
    }

    @Override
    public @Nullable DataSupplier<Integer> getMaxDamage() {
        return DataSupplier.of((int) (Objects.requireNonNull(getMaterial().getDefaultData(DataComponentTypes.MAX_DAMAGE)) / 3.7));
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        CustomAttributeModifier attributeModifier = new CustomAttributeModifier(1, AttributeModifier.Operation.ADD_NUMBER,
                CustomEquipmentSlot.getFromVanilla(Objects.requireNonNull(Objects.requireNonNull(getEquippable()).get()).slot().getGroup()));
        getComponents().set(new CustomAttributedItem(new CustomAttributesContainer.Builder().add(Attributes.MAGICAL_POTENTIAL, attributeModifier).build()));
    }

    @Override
    public @Nullable RepairData initializeRepairData() {
        return new RepairData(Items.WOLF_PELT);
    }
}

package me.udnek.rpgu.item.equipment.grim;

import io.papermc.paper.datacomponent.item.Equippable;
import io.papermc.paper.datacomponent.item.ItemAttributeModifiers;
import me.udnek.coreu.custom.attribute.AttributeUtils;
import me.udnek.coreu.custom.attribute.CustomAttributeModifier;
import me.udnek.coreu.custom.attribute.CustomAttributesContainer;
import me.udnek.coreu.custom.component.instance.CustomAttributedItem;
import me.udnek.coreu.custom.equipment.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.RepairData;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class GrimArmor extends ConstructableCustomItem {

    @Override
    public @Nullable DataSupplier<Equippable> getEquippable() {
        Equippable build = Equippable.equippable(getMaterial().getEquipmentSlot()).assetId(new NamespacedKey(RpgU.getInstance(), "grim")).build();
        return DataSupplier.of(build);
    }

    @Override
    public @Nullable DataSupplier<ItemRarity> getRarity() {
        return DataSupplier.of(ItemRarity.UNCOMMON);
    }

    @Override
    public @Nullable DataSupplier<ItemAttributeModifiers> getAttributeModifiers() {
        return DataSupplier.of(null);
    }

    @Override
    protected void modifyFinalItemStack(@NotNull ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);
        Stats stats = getStats();
        EquipmentSlotGroup slot = getEquippable().get().slot().getGroup();
        AttributeUtils.addAttribute(itemStack, Attribute.MAX_HEALTH, new NamespacedKey(RpgU.getInstance(), slot+"_max_health"), stats.maxHp, AttributeModifier.Operation.ADD_NUMBER, slot);
        AttributeUtils.addAttribute(itemStack, Attribute.ARMOR, new NamespacedKey(RpgU.getInstance(), slot+"_armor"), stats.maxHp, AttributeModifier.Operation.ADD_NUMBER, slot);
    }


    @Override
    public void initializeComponents() {
        super.initializeComponents();

        Stats stats = getStats();

        CustomEquipmentSlot slot = CustomEquipmentSlot.getFromVanilla(getEquippable().get().slot().getGroup());
        CustomAttributeModifier MP = new CustomAttributeModifier(stats.magicalPotential, AttributeModifier.Operation.ADD_NUMBER, slot);
        CustomAttributeModifier MD = new CustomAttributeModifier(stats.magicalDefense, AttributeModifier.Operation.ADD_NUMBER, slot);
        CustomAttributeModifier DM = new CustomAttributeModifier(stats.damageMultiplier, AttributeModifier.Operation.ADD_NUMBER, slot);
        getComponents().set(new CustomAttributedItem(new CustomAttributesContainer.Builder()
                .add(Attributes.MAGICAL_POTENTIAL, MP)
                .add(Attributes.MAGICAL_DEFENSE_MULTIPLIER, MD)
                .add(Attributes.MELEE_MAGICAL_DAMAGE_MULTIPLIER, DM)
                .build())
        );
    }

    @Override
    public @Nullable RepairData initializeRepairData() {
        return new RepairData(Material.BONE);
    }

    public abstract @NotNull Stats getStats();

    public record Stats(double magicalPotential, double magicalDefense, double damageMultiplier, double maxHp, double armor){}

}

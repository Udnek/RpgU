package me.udnek.rpgu.item.equipment.ferrudam.armor;

import me.udnek.itemscoreu.customcomponent.instance.RepairableWithCustomItem;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItemProperties;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.components.EquippableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public abstract class FerrudamArmor extends ConstructableCustomItem {

    @Override
    public ItemFlag[] getTooltipHides() {return new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES};}
    @Override
    public boolean getAddDefaultAttributes() {return true;}

    @Override
    public void getRepairMaterials(@NotNull Consumer<Material> consumer) {
        consumer.accept(Material.DIAMOND);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(new RepairableWithCustomItem(Set.of(Items.FERRUDAM_INGOT)));
    }

    @Override
    @Nullable
    public EquippableComponent getEquippable() {
        EquippableComponent equippable = new ItemStack(getMaterial()).getItemMeta().getEquippable();
        equippable.setSlot(getMaterial().getEquipmentSlot());
        equippable.setModel(new NamespacedKey(RpgU.getInstance(), "ferrudam"));
        return equippable;
    }
}

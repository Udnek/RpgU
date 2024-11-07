package me.udnek.rpgu.item.techincal;

import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.jeiu.component.ComponentTypes;
import me.udnek.rpgu.RpgU;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TechnicalInventoryFiller extends ConstructableCustomItem{
    @Override
    public void initializeComponents() {
        super.initializeComponents();
        setComponent(ComponentTypes.TECHNICAL_ITEM.getDefault());
    }

    @Override
    public @Nullable NamespacedKey getItemModel() {
        return new NamespacedKey(RpgU.getInstance(), "empty");
    }

    @Override
    protected void modifyFinalItemMeta(ItemMeta itemMeta) {
        ((LeatherArmorMeta) itemMeta).setColor(Color.WHITE);
    }

    @Override
    public @NotNull String getRawId() {
        return "technical_inventory_filler";
    }
    @Override
    public boolean getHideTooltip() {return true;}
    @Override
    public @NotNull Material getMaterial() {
        return Material.LEATHER_HELMET;
    }
}

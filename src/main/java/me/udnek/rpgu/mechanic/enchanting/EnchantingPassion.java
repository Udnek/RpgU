package me.udnek.rpgu.mechanic.enchanting;

import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.item.Items;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;

public class EnchantingPassion {

    private final HashMap<CustomItem, EnchantmentsContainer> data = new HashMap<>();

    private static EnchantingPassion instance;
    private EnchantingPassion(){}
    public static EnchantingPassion instance() {
        if (instance == null) instance = new EnchantingPassion();
        return instance;
    }

    static {
        EnchantingPassion instance = instance();
        instance.add(Items.FABRIC, Enchantment.POWER);
        instance.add(Items.HEAVY_AMETHYST_DOLOIRE, Enchantment.SHARPNESS);
    }


    
    public void add(@NotNull CustomItem customItem, @NotNull Enchantment ...enchantments){
        if (data.containsKey(customItem)){
            EnchantmentsContainer enchantmentsContainer = data.get(customItem);
            Arrays.stream(enchantments).forEach(enchantmentsContainer::add);
        } else {
            data.put(customItem, new EnchantmentsContainer(enchantments));
        }
    }
    
    public @NotNull EnchantmentsContainer get(@NotNull ItemStack itemStack){
        CustomItem customItem = CustomItem.get(itemStack);
        if (customItem != null) return get(customItem);
        return new EnchantmentsContainer();
    }
    public @NotNull EnchantmentsContainer get(@NotNull CustomItem item){
        return data.getOrDefault(item, new EnchantmentsContainer());
    }
}

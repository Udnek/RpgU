package me.udnek.rpgu.enchanting;

import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.utils.CustomItemUtils;
import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class EnchantingPassion {

    private static HashMap<Material, EnchantmentsContainer> materialsMap = new HashMap<>();
    private static HashMap<CustomItem, EnchantmentsContainer> customItemsMap = new HashMap<>();

    static {
        add(Material.CONDUIT, Enchantment.CHANNELING, Enchantment.WATER_WORKER);
        add(Material.GOLDEN_APPLE, Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.PROTECTION_FIRE);
        add(Material.NETHERITE_INGOT, Enchantment.DAMAGE_ALL, Enchantment.ARROW_DAMAGE);
        add(Items.lightFeather, Enchantment.PROTECTION_FALL);
        add(Material.ENDER_EYE, Enchantment.MENDING);
    }

    public static EnchantmentsContainer mix(List<ItemStack> itemStacks){
        EnchantmentsContainer enchantmentsContainer = new EnchantmentsContainer();
        for (ItemStack itemStack : itemStacks) {
            CustomItem customItem = CustomItemUtils.getFromItemStack(itemStack);
            if (customItem != null){
                enchantmentsContainer.add(get(customItem));
            }
            else {
                enchantmentsContainer.add(get(itemStack.getType()));
            }
        }
        return enchantmentsContainer;
    }

    public static EnchantmentsContainer mix(ItemStack ...itemStacks){
        return mix(Arrays.asList(itemStacks));

    }





    public static EnchantmentsContainer get(ItemStack itemStack){
        if (CustomItemUtils.isCustomItem(itemStack)){
            return get(CustomItemUtils.getFromItemStack(itemStack));
        }
        return get(itemStack.getType());
    }

    public static EnchantmentsContainer get(Material material){
        return materialsMap.getOrDefault(material, new EnchantmentsContainer());
    }
    public static EnchantmentsContainer get(CustomItem customItem){
        return customItemsMap.getOrDefault(customItem, new EnchantmentsContainer());
    }

    public static void add(Material material, Enchantment ...enchantment){
        if (materialsMap.containsKey(material)){
            EnchantmentsContainer enchantmentsContainer = materialsMap.get(material);
            enchantmentsContainer.add(enchantment);
            return;
        }
        materialsMap.put(material, new EnchantmentsContainer(enchantment));
    }

    public static void add(CustomItem customItem, Enchantment ...enchantment){
        if (customItemsMap.containsKey(customItem)){
            EnchantmentsContainer enchantmentsContainer = customItemsMap.get(customItem);
            enchantmentsContainer.add(enchantment);
            return;
        }
        customItemsMap.put(customItem, new EnchantmentsContainer(enchantment));
    }

}

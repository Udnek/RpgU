package me.udnek.rpgu.attribute;

import me.udnek.itemscoreu.customattribute.ConstructableCustomAttribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EnchantmentCalculatorAttribute extends ConstructableCustomAttribute {
    protected Enchantment enchantment;
    public EnchantmentCalculatorAttribute(@NotNull String rawId, @NotNull Enchantment enchantment) {
        super(rawId, 0, 0, 1024);
        this.enchantment = enchantment;
    }
    @Override
    public double calculate(@NotNull LivingEntity entity) {
        int levelSum = 0;
        ItemStack[] contents;
        if (entity instanceof Player player){
            contents = player.getInventory().getArmorContents();
        }
        else {
            EntityEquipment equipment = entity.getEquipment();
            if (equipment == null) contents = new ItemStack[0];
            else contents = equipment.getArmorContents();
        }

        for (ItemStack item : contents) {
            if (item == null) continue;
            levelSum += item.getEnchantmentLevel(enchantment);
        }
        return levelSum;
    }
}

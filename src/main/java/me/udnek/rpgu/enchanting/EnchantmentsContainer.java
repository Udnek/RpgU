package me.udnek.rpgu.enchanting;

import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.List;

public class EnchantmentsContainer {

    private List<Enchantment> enchantments = new ArrayList<>();

    public EnchantmentsContainer(Enchantment ...enchantments){
        for (Enchantment enchantment : enchantments) {
            add(enchantment);
        }
    }

    public List<Enchantment> getAll(){
        List<Enchantment> clone = new ArrayList<>(enchantments.size());
        clone.addAll(enchantments);
        return clone;
    }

    public void add(Enchantment ...toAddEnchantments){
        for (Enchantment enchantment : toAddEnchantments) {
            if (!enchantments.contains(enchantment)){
                enchantments.add(enchantment);
            }
        }
    }

    public void add(EnchantmentsContainer enchantmentsContainer){
        for (Enchantment enchantment : enchantmentsContainer.getAll()) {
            if (!enchantments.contains(enchantment)){
                enchantments.add(enchantment);
            }
        }
    }

    public void remove(Enchantment enchantment){
        enchantments.remove(enchantment);
    }

    public boolean contains(Enchantment enchantment){
        return enchantments.contains(enchantment);
    }

}

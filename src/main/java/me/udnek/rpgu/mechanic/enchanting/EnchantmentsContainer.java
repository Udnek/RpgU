package me.udnek.rpgu.mechanic.enchanting;

import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class EnchantmentsContainer {

    protected final Set<Enchantment> enchantments = new HashSet<>();

    public EnchantmentsContainer(@NotNull Enchantment ...enchantments){
        for (@NotNull Enchantment enchantment : enchantments) {
            add(enchantment);
        }
    }

    public void get(@NotNull Consumer<Enchantment> consumer){
        enchantments.forEach(consumer);
    }
    public @NotNull Set<Enchantment> get(){
        return new HashSet<>(enchantments);
    }


    public void add(@NotNull Enchantment enchantment){
        enchantments.add(enchantment);
    }

    public void add(@NotNull EnchantmentsContainer container){
        container.get(enchantments::add);
    }

    public boolean isEmpty(){return enchantments.isEmpty();}

    public static @NotNull HashMap<Enchantment, Integer> mix(@NotNull Iterable<EnchantmentsContainer> containers){
        HashMap<Enchantment, Integer> map = new HashMap<>();
        for (EnchantmentsContainer container : containers) {
            container.get(new Consumer<>() {
                @Override
                public void accept(Enchantment enchantment) {
                    map.put(enchantment, Math.clamp(map.getOrDefault(enchantment, 0) + 1, 1, enchantment.getMaxLevel()));
                }
            });
        }
        return map;
    }
}

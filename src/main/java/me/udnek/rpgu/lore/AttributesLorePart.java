package me.udnek.rpgu.lore;

import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.util.LoreBuilder;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

public class AttributesLorePart implements LoreBuilder.Componentable {

    HashMap<CustomEquipmentSlot, LoreBuilder.Componentable.Simple> data = new HashMap<>();

    @Override
    public void toComponents(@NotNull Consumer<Component> consumer) {
        if (isEmpty()) return;
        SortedMap<CustomEquipmentSlot, Simple> sorted = new TreeMap<>(new Comparator<CustomEquipmentSlot>() {
            @Override
            public int compare(CustomEquipmentSlot o1, CustomEquipmentSlot o2) {
                if (o1 == CustomEquipmentSlot.MAIN_HAND) return -1;
                if (o2 == CustomEquipmentSlot.MAIN_HAND) return 1;
                return Integer.compare(o1.getId().hashCode(), o2.getId().hashCode());
            }
        });
        sorted.putAll(data);

        for (Map.Entry<CustomEquipmentSlot, Simple> entry : sorted.entrySet()) {
            consumer.accept(Component.empty());
            Simple componentable = entry.getValue();
            CustomEquipmentSlot slot = entry.getKey();

            consumer.accept(AttributeLoreGenerator.getHeader(slot));
            componentable.toComponents(consumer);
        }
    }
    public void addAttribute(@NotNull CustomEquipmentSlot slot, Component component){
        addLine(slot, AttributeLoreGenerator.addTab(component), false);
    }
    public void addLine(@NotNull CustomEquipmentSlot slot, Component component, boolean asFirst){
        Simple componentable = data.getOrDefault(slot, new Simple());
        if (asFirst) componentable.addFirst(component);
        else componentable.add(component);
        data.put(slot, componentable);
    }
    public void remove(@NotNull CustomEquipmentSlot slot){
        data.remove(slot);
    }
    public @Nullable LoreBuilder.Componentable get(@NotNull CustomEquipmentSlot slot){
        return data.get(slot);
    }
    @Override
    public boolean isEmpty() {return data.isEmpty();}
    @Override
    @Deprecated
    public void add(@NotNull Component component) {}
    @Override
    @Deprecated
    public void addFirst(@NotNull Component component) {}
}

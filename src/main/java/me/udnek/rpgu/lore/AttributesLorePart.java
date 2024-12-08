package me.udnek.rpgu.lore;

import com.google.common.base.Preconditions;
import me.udnek.itemscoreu.customattribute.CustomAttribute;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.util.LoreBuilder;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
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

    public void addFullDescription(@NotNull CustomEquipmentSlot slot, @NotNull ConstructableCustomItem customItem, int linesAmount){
        for (int i = 0; i < linesAmount; i++) addDescription(slot, customItem, i);
    }
    public void addDescription(@NotNull CustomEquipmentSlot slot, @NotNull ConstructableCustomItem customItem, int line){
        Preconditions.checkArgument(customItem.getRawItemName() != null, "CustomItem raw name can not be null!");
        addDescription(slot, customItem.getRawItemName(), line);
    }
    public void addDescription(@NotNull CustomEquipmentSlot slot, @NotNull String rawItemName, int line){
        addLine(slot, AttributeLoreGenerator.addTab(Component.translatable(rawItemName + ".description." + line)).color(CustomAttribute.PLUS_COLOR), false);
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

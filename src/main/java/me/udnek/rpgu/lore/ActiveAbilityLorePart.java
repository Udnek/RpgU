package me.udnek.rpgu.lore;

import me.udnek.itemscoreu.util.LoreBuilder;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ActiveAbilityLorePart implements LoreBuilder.Componentable {

    @Nullable Component header;
    List<@NotNull Component> data = new ArrayList<>();

    @Override
    public void toComponents(@NotNull Consumer<Component> consumer) {
        if (isEmpty()) return;
        if (header != null) consumer.accept(header);
        data.forEach(consumer);
    }


    public void setHeader(@NotNull Component component){
        header = component;
    }
    @Override
    public void add(@NotNull Component component) {
        data.add(Component.translatable(TranslationKeys.equipmentDescriptionLine, component));
    }
    @Override
    public void addFirst(@NotNull Component component) {
        data.addFirst(Component.translatable(TranslationKeys.equipmentDescriptionLine, component));
    }

    @Override
    public boolean isEmpty() {return data.isEmpty() && header == null;}

}

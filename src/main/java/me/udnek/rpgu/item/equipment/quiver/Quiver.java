package me.udnek.rpgu.item.equipment.quiver;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.coreu.custom.component.instance.AutoGeneratingFilesItem;
import me.udnek.coreu.custom.component.instance.DispensableItem;
import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.jeiu.component.HiddenItemComponent;
import me.udnek.rpgu.component.Components;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class Quiver extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {
        return "quiver";
    }

    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Quiver", "Колчан");
    }

    @Override
    public boolean isUpdateMaterial() {
        return false;
    }

    @Override
    public void getComponentsToUpdate(@NotNull ComponentConsumer consumer) {
        consumer.accept(DataComponentTypes.ITEM_MODEL);
    }

    @Override
    public @Nullable DataSupplier<Integer> getMaxStackSize() {
        return DataSupplier.of(1);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        getComponents().set(HiddenItemComponent.INSTANCE);
        getComponents().set(DispensableItem.ALWAYS_DROP);
        getComponents().set(AutoGeneratingFilesItem.GENERATED_20X20);
        getComponents().set(new QuiverLoadUnloadComponent());
        getComponents().getOrCreateDefault(RPGUComponents.ACTIVE_ABILITY_ITEM).getComponents().set(QuiverChangeArrowAbility.DEFAULT);
        getComponents().getOrCreateDefault(RPGUComponents.PASSIVE_ABILITY_ITEM).getComponents().set(QuiverShootAbility.DEFAULT);
    }

    public static @NotNull List<Component> generateLore(@NotNull List<Component> lore, @NotNull List<ItemStack> content){
        int sum = content.stream().mapToInt(ItemStack::getAmount).sum();
        TextColor color = (sum >= QuiverLoadUnloadComponent.MAX_SIZE) ? NamedTextColor.RED : NamedTextColor.WHITE;
        lore.addFirst(Component.text(sum).append(Component.text(" / ")).append(Component.text(QuiverLoadUnloadComponent.MAX_SIZE))
                .decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE).color(color));

        for (ItemStack itemStack : content.reversed()) {
            CustomItem customItem = CustomItem.get(itemStack);
            Component icon = Objects.requireNonNull(customItem).getComponents().getOrDefault(Components.ARROW_ITEM).getIcon(customItem, itemStack);
            lore.addFirst(icon.append(Component.text(" x "  ).append(Component.text(itemStack.getAmount())).
                    decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE).font(Key.key("default"))));
        }
        return lore;
    }

}
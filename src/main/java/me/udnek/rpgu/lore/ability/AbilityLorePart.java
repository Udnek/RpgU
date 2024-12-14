package me.udnek.rpgu.lore.ability;

import com.google.common.base.Preconditions;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.util.LoreBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

public interface AbilityLorePart extends LoreBuilder.Componentable{

    TextColor ACTIVE_HEADER_COLOR = NamedTextColor.GREEN;
    TextColor PASSIVE_HEADER_COLOR = NamedTextColor.YELLOW;

    TextColor ACTIVE_STATS_COLOR = NamedTextColor.GRAY;
    TextColor PASSIVE_STATS_COLOR = NamedTextColor.GRAY;

    TextColor ACTIVE_DESCRIPTION_COLOR = NamedTextColor.BLUE;
    TextColor PASSIVE_DESCRIPTION_COLOR = NamedTextColor.BLUE;


    void setHeader(@NotNull Component component);

    void addAbilityStat(@NotNull Component component);

    default void addFullAbilityDescription(@NotNull ConstructableCustomItem customItem, int linesAmount){
        for (int i = 0; i < linesAmount; i++) addAbilityDescription(customItem, i);
    }
    default void addAbilityDescription(@NotNull ConstructableCustomItem customItem, int line){
        Preconditions.checkArgument(customItem.getRawItemName() != null, "CustomItem raw name can not be null!");
        addAbilityDescription(customItem.getRawItemName(), line);
    }
    void addAbilityDescription(@NotNull String rawItemName, int line);
    void addAbilityDescription(@NotNull Component component);

    void addWithAbilityFormat(@NotNull Component component);
}

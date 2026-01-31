package me.udnek.rpgu.item.equipment.Illagerite;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.coreu.custom.component.instance.AutoGeneratingFilesItem;
import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.RepairData;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.rpgu.component.ability.vanilla.ShieldCrashingAbility;
import me.udnek.rpgu.item.equipment.flint.FlintTool;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public class IllageriteAxe extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "illagerite_axe";}

    @Override
    public @NotNull Material getMaterial() {return Material.DIAMOND_AXE;}

    @Override
    public @Nullable TranslatableThing getTranslations() {return TranslatableThing.ofEngAndRu("Illagerite Axe", "Злодеянитовый топор");}

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().getOrCreateDefault(RPGUComponents.ACTIVE_ABILITY_ITEM).getComponents().set(new ShieldCrashingAbility());
        getComponents().set(AutoGeneratingFilesItem.HANDHELD);
    }
    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {
        //TODO
    }

    /*

    @Override
    public @Nullable RepairData initializeRepairData() {
        HashSet<Material> materials = new HashSet<>(Set.of(Material.FLINT));
        materials.addAll(Tag.ITEMS_STONE_TOOL_MATERIALS.getValues());
        return new RepairData(Set.of(), materials);//TODO
    }*/
}

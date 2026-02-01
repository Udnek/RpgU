package me.udnek.rpgu.item.equipment.Illagerite;

import me.udnek.coreu.custom.component.instance.AutoGeneratingFilesItem;
import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.RepairData;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.rpgu.component.ability.vanilla.ShieldCrashingAbility;
import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class IllageriteAxe extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {
        return "illagerite_axe";
    }

    @Override
    public @NotNull Material getMaterial() {
        return Material.DIAMOND_AXE;
    }

    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Illagerite Axe", "Злодеянитовый топор");
    }

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


    @Override
    public @Nullable RepairData initializeRepairData() {
        return new RepairData(Items.ILLAGERITE_INGOT);
    }
}

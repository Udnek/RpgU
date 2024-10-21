package me.udnek.rpgu.util;

import me.udnek.itemscoreu.customadvancement.AdvancementCriterion;
import me.udnek.itemscoreu.customadvancement.ConstructableCustomAdvancement;
import me.udnek.itemscoreu.customadvancement.CustomAdvancementUtils;
import me.udnek.rpgu.item.Items;
import org.bukkit.NamespacedKey;
import me.udnek.itemscoreu.customadvancement.AdvancementCriterion;
import me.udnek.itemscoreu.customadvancement.ConstructableCustomAdvancement;
import me.udnek.itemscoreu.customadvancement.CustomAdvancementUtils;

public class AdvancementRegistering {
    ConstructableCustomAdvancement advancement = CustomAdvancementUtils.itemAdvancement(new NamespacedKey("rpg", "test"), Items.FABRIC.getItem());
        advancement.getDisplay().background("textures/block/cobblestone.png");
        advancement.register();

    ConstructableCustomAdvancement advancementTest = CustomAdvancementUtils.itemAdvancement(new NamespacedKey("rpg", "test2"), Items.FERRUDAM_INGOT.getItem());
        advancementTest.addCriterion(AdvancementCriterion.INVENTORY_CHANGE.create(Items.INGOT_MOLD.getItem()));
        advancementTest.requirementsStrategy(ConstructableCustomAdvancement.RequirementsStrategy.OR);
        advancementTest.parent(advancement);
        advancementTest.register();
}

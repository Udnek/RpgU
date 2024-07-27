package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.utils.ComponentU;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.item.abstraction.ArtifactItem;
import me.udnek.rpgu.lore.LoreUtils;
import me.udnek.rpgu.mechanic.damaging.DamageEvent;
import me.udnek.rpgu.util.CooldownData;
import me.udnek.rpgu.util.ExtraDescribed;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import oshi.util.tuples.Pair;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class NautilusCore extends ConstructableCustomItem implements ExtraDescribed, ArtifactItem {
    private final CustomAttributesContainer container = new CustomAttributesContainer.Builder()
            .add(Attributes.MAGICAL_DAMAGE, 0.1, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlots.ARTIFACT)
            .build();

    private final CooldownData cooldownData = new CooldownData(20*3);

    @Override
    public Integer getCustomModelData() {
        return 3104;
    }

    @Override
    public Material getMaterial() {
        return Material.GUNPOWDER;
    }


    @Override
    protected void modifyFinalItemStack(ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);
        LoreUtils.generateFullLoreAndApply(itemStack);
    }

    @Override
    public String getRawId() {
        return "nautilus_core";
    }

    @Override
    public CustomAttributesContainer getDefaultCustomAttributes() {
        return container;
    }

    @Override
    public Map<CustomEquipmentSlot, Pair<Integer, Integer>> getExtraDescription() {
        return ExtraDescribed.getSimple(EquipmentSlots.ARTIFACT, 1);
    }


    @Override
    protected List<Recipe> generateRecipes() {
        ShapedRecipe recipe = new ShapedRecipe(this.getRecipeNamespace(0), this.getItem());
        recipe.shape("AAA","ABA","AAA");

        recipe.setIngredient('A', Material.PRISMARINE_SHARD);
        recipe.setIngredient('B', Material.NAUTILUS_SHELL);

        return Collections.singletonList(recipe);
    }

    @Override
    public void onPlayerAttacksWhenEquipped(Player player, CustomEquipmentSlot slot, DamageEvent event) {
        if (event.isCritical() && !event.containsExtraFlag(new isMagicalCriticalApplied())){
            event.getDamage().multiplyMagicalDamage(1.5);
            event.addExtraFlag(new isMagicalCriticalApplied());
            cooldownData.set(player);
        }
    }

    @Override
    public List<Component> getHudImages(Player player) {
        if (!cooldownData.has(player)) return null;
        return Collections.singletonList(
                ComponentU.textWithNoSpace(0, Component.translatable("item.rpgu.nautilus_core.image"), 16).append(cooldownData.getImage(player))
        );
    }

    private static class isMagicalCriticalApplied extends DamageEvent.ExtraFlag{}
}



















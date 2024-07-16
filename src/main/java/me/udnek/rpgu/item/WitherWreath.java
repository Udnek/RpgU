package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.mechanic.damaging.DamageEvent;
import me.udnek.rpgu.item.abstraction.ArtifactItem;
import me.udnek.rpgu.util.ExtraDescribed;
import me.udnek.rpgu.lore.LoreUtils;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import oshi.util.tuples.Pair;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class WitherWreath extends ConstructableCustomItem implements ExtraDescribed, ArtifactItem {
    private final CustomAttributesContainer container = new CustomAttributesContainer.Builder()
            .add(Attributes.MAGICAL_DAMAGE, 0.2, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlots.ARTIFACT)
            .build();
    @Override
    public Integer getCustomModelData() {
        return 3103;
    }
    @Override
    public Material getMaterial() {
        return Material.GUNPOWDER;
    }
    @Override
    public String getRawId() {
        return "wither_wreath";
    }

    @Override
    protected void modifyFinalItemStack(ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);
        LoreUtils.generateFullLoreAndApply(itemStack);
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
        recipe.shape("AAA","A A","AAA");

        recipe.setIngredient('A', Material.WITHER_ROSE);

        return Collections.singletonList(recipe);
    }

    @Override
    public void onPlayerAttacksWhenEquipped(Player player, CustomEquipmentSlot slot, DamageEvent event) {
        Entity entity = event.getVictim();
        if (entity instanceof LivingEntity){
            ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20*4, 1, false, true));
        }
    }
}

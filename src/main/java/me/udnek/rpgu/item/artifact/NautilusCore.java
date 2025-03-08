package me.udnek.rpgu.item.artifact;

import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customcomponent.instance.CustomItemAttributesComponent;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customequipmentslot.SingleSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.LoreBuilder;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.ConstructableEquippableItemComponent;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.lore.AttributesLorePart;
import me.udnek.rpgu.mechanic.damaging.DamageInstance;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class NautilusCore extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "nautilus_core";}

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape("PPP","PNP","PPP");

        recipe.setIngredient('P', Material.PRISMARINE_SHARD);
        recipe.setIngredient('N', Material.NAUTILUS_SHELL);

        consumer.accept(recipe);
    }

    @Override
    public @Nullable LoreBuilder getLoreBuilder() {
        LoreBuilder loreBuilder = new LoreBuilder();
        AttributesLorePart attributesLorePart = new AttributesLorePart();
        loreBuilder.set(LoreBuilder.Position.ATTRIBUTES, attributesLorePart);
        attributesLorePart.addFullDescription(EquipmentSlots.ARTIFACTS, this, 1);
        return loreBuilder;
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(new NautilusCoreComponent());
        getComponents().set(new CustomItemAttributesComponent(new CustomAttributesContainer.Builder()
                .add(Attributes.MELEE_MAGICAL_DAMAGE_MULTIPLIER, 0.15, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlots.ARTIFACTS)
                .build()
        ));
    }

    public static class NautilusCoreComponent extends ConstructableEquippableItemComponent {

        @Override
        public boolean isAppropriateSlot(@NotNull CustomEquipmentSlot slot) {
            return EquipmentSlots.ARTIFACTS.test(slot);
        }

        @Override
        public void onPlayerAttacksWhenEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull SingleSlot slot, @NotNull DamageInstance damageInstance) {
            if (!damageInstance.isCritical()) return;
            if (damageInstance.containsExtraFlag(new isMagicalCriticalApplied())) return;

            damageInstance.getDamage().multiplyMagical(Attributes.CRITICAL_DAMAGE.calculate(player));
            damageInstance.addExtraFlag(new isMagicalCriticalApplied());
        }
    }


    private static class isMagicalCriticalApplied extends DamageInstance.ExtraFlag{}
}



















package me.udnek.rpgu.component;

import me.udnek.coreu.custom.attribute.CustomKeyedAttributeModifier;
import me.udnek.coreu.custom.attribute.VanillaAttributesContainer;
import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.equipmentslot.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.equipmentslot.slot.CustomEquipmentSlot.Single;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.util.LoreBuilder;
import me.udnek.rpgu.component.ability.passive.PassiveAbility;
import me.udnek.rpgu.mechanic.damaging.DamageInstance;
import net.kyori.adventure.text.Component;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface EquippableItemComponent extends CustomComponent<CustomItem> {
    EquippableItemComponent EMPTY = new EquippableItemComponent() {
        @Override
        public boolean isAppropriateSlot(@NotNull CustomEquipmentSlot slot) {return false;}
        @Override
        public void addPassive(@NotNull PassiveAbility passive) {
            throw new RuntimeException("Can not add passive to default empty component");
        }
        @Override
        public @NotNull List<PassiveAbility> getPassives() {
            return List.of();
        }
        @Override
        public void getPassives(@NotNull Consumer<PassiveAbility> consumer) {}
    };

    boolean isAppropriateSlot(@NotNull CustomEquipmentSlot slot);
    default void onEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot, @NotNull ItemStack itemStack){
        getAttributes(item, player, slot, (attributeInstance, custom.Modifier) -> {
            AttributeModifier modifier = custom.Modifier.toVanillaWitAdjustedKey("_" + slot.getKey().asString().replace(':', '_'));
            if (attributeInstance.getModifier(modifier.getKey()) == null) {
                attributeInstance.addModifier(modifier);
            }
        });
    }
    default void onUnequipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot, @NotNull ItemStack itemStack) {
        getAttributes(item, player, slot, (attributeInstance, custom.Modifier) -> {
            AttributeModifier modifier = custom.Modifier.toVanillaWitAdjustedKey("_" + slot.getKey().asString().replace(':', '_'));
            if (attributeInstance.getModifier(modifier.getKey()) != null) {
                attributeInstance.removeModifier(modifier);
            }
        });
    }
    default void tickBeingEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot.Single slot){
        getPassives(passiveAbility -> passiveAbility.tick(item, player, slot));
    }
    default void onPlayerAttacksWhenEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot.Single slot, @NotNull DamageInstance damageInstance){}
    default void onPlayerReceivesDamageWhenEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot.Single slot, @NotNull DamageInstance damageInstance){}
    default void onPlayerHitsWithProjectileWhenEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot.Single slot, @NotNull DamageInstance damageInstance){}
    default @Nullable Component getHudImage(@NotNull CustomItem item, @NotNull Player player){return null;}


    default void getAttributes(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot, @NotNull AttributeConsumer consumer){
        VanillaAttributesContainer container = item.getComponents().getOrDefault(CustomComponentType.VANILLA_ATTRIBUTED_ITEM).getAttributes(item);
        if (container.isEmpty()) return;

        for (Map.Entry<Attribute, List<CustomKeyedAttributeModifier>> entry : container.get(slot).getAll().entrySet()) {
            Attribute attribute = entry.getKey();
            List<CustomKeyedAttributeModifier> modifiers = entry.getValue();
            AttributeInstance attributeInstance = player.getAttribute(attribute);
            if (attributeInstance == null) continue;

            for (CustomKeyedAttributeModifier modifier : modifiers) {
                consumer.accept(attributeInstance, modifier);
            }
        }
    }
    default void getLore(@NotNull LoreBuilder loreBuilder) {}

    void addPassive(@NotNull PassiveAbility passive);

    @NotNull List<PassiveAbility> getPassives();

    void getPassives(@NotNull Consumer<PassiveAbility> consumer);

    interface AttributeConsumer{
        void accept(AttributeInstance attributeInstance, CustomKeyedAttributeModifier modifier);
    }


    @Override
    @NotNull
    default CustomComponentType<CustomItem, ?> getType(){
        return ComponentTypes.EQUIPPABLE_ITEM;
    }
}

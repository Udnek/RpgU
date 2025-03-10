package me.udnek.rpgu.component;


import me.udnek.itemscoreu.customequipmentslot.slot.CustomEquipmentSlot;
import me.udnek.rpgu.component.ability.passive.PassiveAbility;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ConstructableEquippableItemComponent implements EquippableItemComponent {
    protected List<PassiveAbility> passives = new ArrayList<>();

    @Override
    public boolean isAppropriateSlot(@NotNull CustomEquipmentSlot slot) {
        for (PassiveAbility passive : getPassives()) {
            if (slot.intersects(passive.getSlot())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addPassive(@NotNull PassiveAbility passive) {
        this.passives.add(passive);
    }

    @Override
    public void getPassives(@NotNull Consumer<PassiveAbility> consumer) {
        passives.forEach(consumer);
    }

    @Override
    public @NotNull List<PassiveAbility> getPassives() {
        return passives;
    }
}

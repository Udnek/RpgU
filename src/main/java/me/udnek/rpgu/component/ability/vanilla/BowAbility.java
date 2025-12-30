package me.udnek.rpgu.component.ability.vanilla;

import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.equipment.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.equipment.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.RPGUActiveItem;
import me.udnek.coreu.rpgu.component.ability.active.RPGUConstructableActiveAbility;
import me.udnek.rpgu.component.ability.RPGUActiveTriggerableAbility;
import me.udnek.rpgu.component.ability.VanillaAbilities;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.util.TriConsumer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class BowAbility extends RPGUConstructableActiveAbility<EntityShootBowEvent> implements RPGUActiveTriggerableAbility<EntityShootBowEvent> {

    public static final BowAbility DEFAULT = new BowAbility();

    @Override
    protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot universalInventorySlot, @NonNull EntityShootBowEvent entityShootBowEvent) {
        System.out.println("Пидор стрельнул: " + livingEntity.getName());
        return ActionResult.NO_COOLDOWN;
    }

    @Override
    public void onShoot(@NotNull CustomItem customItem, @NotNull EntityShootBowEvent event) {
        activate(customItem, event.getEntity(), new BaseUniversalSlot(event.getHand()), event);
    }

    @Override
    public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
        return Pair.of(List.of("Shoots a stretched arrow"), List.of("Стреляет натянутой стрелой"));
    }

    @Override
    public void getEngAndRuProperties(TriConsumer<@NotNull String, @NotNull String, @NotNull List<Component>> Eng_Ru_Args) {
        super.getEngAndRuProperties(Eng_Ru_Args);
        Eng_Ru_Args.accept("Full charge: %s seconds", "Полная натяжка: %s секунд", List.of(Component.text(0.9)));
        Eng_Ru_Args.accept("Damage at full charge: %s", "Урон при полной натяжке: %s", List.of(Component.text(6)));
        Eng_Ru_Args.accept("Critical charge: %s seconds", "Критическая натяжка: %s секунд", List.of(Component.text(1)));
    }

    @Override
    public @NotNull CustomComponentType<? super RPGUActiveItem, ? extends CustomComponent<? super RPGUActiveItem>> getType() {
        return VanillaAbilities.BOW;
    }
}

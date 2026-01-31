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

public class CrossbowAbility extends RPGUConstructableActiveAbility<EntityShootBowEvent> implements RPGUActiveTriggerableAbility<EntityShootBowEvent> {

    public static final CrossbowAbility DEFAULT = new CrossbowAbility();

    @Override
    protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot universalInventorySlot, @NonNull EntityShootBowEvent entityShootBowEvent) {
        return ActionResult.FULL_COOLDOWN;
    }

    @Override
    public void onShoot(@NotNull CustomItem customItem, @NotNull EntityShootBowEvent event) {
        activate(customItem, event.getEntity(), new BaseUniversalSlot(event.getHand()), event);
    }

    @Override
    public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
        return Pair.of(List.of("Charges and shoots an projectile"), List.of("Заряжает и выпускает снаряд"));
    }

    @Override
    public void getEngAndRuProperties(TriConsumer<String, String, List<Component>> Eng_Ru_Args) {
        super.getEngAndRuProperties(Eng_Ru_Args);
        Eng_Ru_Args.accept("Arrow speed: %s blocks per second", "Скорость выпущенной стрелы: %s блоков в секунду",
                List.of(Component.text(60)));
        Eng_Ru_Args.accept("Firework speed: %s blocks per second", "Скорость выпущенного фейерверка: %s блоков в секунду",
                List.of(Component.text(32)));
        Eng_Ru_Args.accept("Charge time: %s seconds", "Время натяжки: %s секунд", List.of(Component.text(1.25)));
        Eng_Ru_Args.accept("Damage: %s", "Урон: %s", List.of(Component.text(6)));
    }

    @Override
    public @NotNull CustomComponentType<? super RPGUActiveItem, ? extends CustomComponent<? super RPGUActiveItem>> getType() {
        return VanillaAbilities.CROSSBOW;
    }
}

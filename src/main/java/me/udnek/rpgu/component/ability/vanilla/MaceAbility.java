package me.udnek.rpgu.component.ability.vanilla;

import io.papermc.paper.event.entity.EntityAttemptSmashAttackEvent;
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
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class MaceAbility extends RPGUConstructableActiveAbility<EntityAttemptSmashAttackEvent> implements RPGUActiveTriggerableAbility<EntityAttemptSmashAttackEvent> {

    public static final MaceAbility DEFAULT = new MaceAbility();

    @Override
    protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot universalInventorySlot, @NonNull EntityAttemptSmashAttackEvent entityShootBowEvent) {
        return ActionResult.NO_COOLDOWN;
    }

    @Override
    public void onSmashAttack(@NotNull CustomItem customItem, @NotNull EntityAttemptSmashAttackEvent event) {
        activate(customItem, (LivingEntity) event.getEntity(), new BaseUniversalSlot(EquipmentSlot.HAND), event);
    }

    @Override
    public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
        return Pair.of(List.of("When falling from above 1.5 blocks, deals a smash attack"),
                List.of("При падении с высоты более 1,5 блоков наносится сокрушающий удар"));

    }

    @Override
    public void getEngAndRuProperties(TriConsumer<@NotNull String, @NotNull String, @NotNull List<Component>> Eng_Ru_Args) {
        super.getEngAndRuProperties(Eng_Ru_Args);
        Eng_Ru_Args.accept("Flight speed: %s blocks per second", "Скорость полёта: %s блоков в секунду", List.of(Component.text(50)));
        Eng_Ru_Args.accept("Damage when shot: %s", "Урон при выстреле: %s", List.of(Component.text(8)));//TODO переделать при изменение
    }

    @Override
    public @NotNull CustomComponentType<? super RPGUActiveItem, ? extends CustomComponent<? super RPGUActiveItem>> getType() {
        return VanillaAbilities.MACE;
    }
}

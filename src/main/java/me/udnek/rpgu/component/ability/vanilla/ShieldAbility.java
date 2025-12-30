package me.udnek.rpgu.component.ability.vanilla;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.BlocksAttacks;
import io.papermc.paper.datacomponent.item.blocksattacks.DamageReduction;
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
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Objects;

public class ShieldAbility extends RPGUConstructableActiveAbility<PlayerInteractEvent> implements RPGUActiveTriggerableAbility<PlayerInteractEvent> {

    public static final ShieldAbility DEFAULT = new ShieldAbility(Material.SHIELD.getDefaultData(DataComponentTypes.BLOCKS_ATTACKS));

    private final float blockAngle;

    public ShieldAbility(BlocksAttacks defaultData) {
         this.blockAngle = validate(defaultData);
    }

    @Override
    protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot universalInventorySlot, @NonNull PlayerInteractEvent entityShootBowEvent) {
        return ActionResult.NO_COOLDOWN;
    }

    @Override
    public void onRightClick(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event) {
        activate(customItem, event.getPlayer(), new BaseUniversalSlot(Objects.requireNonNull(event.getHand())), event);
    }

    @Override
    public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
        return Pair.of(List.of("Blocks all damage that the player takes in front"), List.of("Блокирует весь урон который получает игрок спереди"));
    }

    @Override
    public void getEngAndRuProperties(TriConsumer<@NotNull String, @NotNull String, @NotNull List<Component>> Eng_Ru_Args) {
        super.getEngAndRuProperties(Eng_Ru_Args);
        Eng_Ru_Args.accept("Horizontal block angle: %s degrees", "Угол блокирования по горизонтали: %s градусов", List.of(Component.text(blockAngle)));
    }

    @Override
    public @NotNull CustomComponentType<? super RPGUActiveItem, ? extends CustomComponent<? super RPGUActiveItem>> getType() {
        return VanillaAbilities.SHIELD;
    }

    private float validate(BlocksAttacks blocksAttacks) {
        List<DamageReduction> damageReductions = blocksAttacks.damageReductions();
        float firstAngle = damageReductions.getFirst().horizontalBlockingAngle();
        damageReductions.removeFirst();
        for (DamageReduction damageReduction : damageReductions) {
            if (firstAngle != damageReduction.horizontalBlockingAngle()) {
                throw new RuntimeException("The angle is not the same in damageReductions");
            }
        }
        return firstAngle;
    }
}

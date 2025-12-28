package me.udnek.rpgu.item.equipment.quiver;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.BundleContents;
import io.papermc.paper.datacomponent.item.ItemLore;
import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.equipment.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.equipment.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.RPGUActiveItem;
import me.udnek.coreu.rpgu.component.ability.RPGUItemAbstractAbility;
import me.udnek.coreu.rpgu.component.ability.active.RPGUConstructableActiveAbility;
import me.udnek.rpgu.component.ability.Abilities;
import me.udnek.rpgu.component.ability.RPGUActiveTriggerableAbility;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuiverChangeArrowAbility extends RPGUConstructableActiveAbility<PlayerInteractEvent> implements RPGUActiveTriggerableAbility<PlayerInteractEvent> {

    public static final QuiverChangeArrowAbility DEFAULT = new QuiverChangeArrowAbility();

    private QuiverChangeArrowAbility(){}

    public void onRightClick(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event) {
        event.setCancelled(true);
        activate(customItem, event.getPlayer(), new BaseUniversalSlot(Objects.requireNonNull(event.getHand())), event);
    }

    @Override
    protected @NotNull RPGUItemAbstractAbility.ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot slot, @NotNull PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null) return RPGUItemAbstractAbility.ActionResult.NO_COOLDOWN;
        BundleContents data = item.getData(DataComponentTypes.BUNDLE_CONTENTS);
        if (data == null) return RPGUItemAbstractAbility.ActionResult.NO_COOLDOWN;
        List<ItemStack> contents = new ArrayList<>(data.contents());
        if (contents.isEmpty()) return RPGUItemAbstractAbility.ActionResult.NO_COOLDOWN;

        ItemStack firstItem = contents.removeFirst();
        contents.add(firstItem);
        item.setData(DataComponentTypes.BUNDLE_CONTENTS, BundleContents.bundleContents(contents));

        List<Component> lines = Quiver.generateLore(Objects.requireNonNull(customItem.getItem().lore()),contents);
        item.setData(DataComponentTypes.LORE, ItemLore.lore(lines));

        ItemStack stack = item.withType(contents.getFirst().getType());
        stack.unsetData(DataComponentTypes.POTION_CONTENTS);
        stack.unsetData(DataComponentTypes.FIREWORKS);
        new BaseUniversalSlot(Objects.requireNonNull(event.getHand())).setItem(stack, event.getPlayer());

        return RPGUItemAbstractAbility.ActionResult.FULL_COOLDOWN;
    }

    @Override
    public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
        return null;
    }

    @Override
    public @NotNull CustomComponentType<? super RPGUActiveItem, ? extends CustomComponent<? super RPGUActiveItem>> getType() {
        return Abilities.QUIVER_CHANGE_ARROW;
    }
}

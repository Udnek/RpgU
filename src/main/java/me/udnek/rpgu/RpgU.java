package me.udnek.rpgu;

import me.udnek.itemscoreu.customadvancement.ConstructableCustomAdvancement;
import me.udnek.itemscoreu.customadvancement.CustomAdvancementUtils;
import me.udnek.itemscoreu.customblock.CustomBlock;
import me.udnek.itemscoreu.customentity.CustomEntityType;
import me.udnek.itemscoreu.customequipmentslot.SingleSlot;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.resourcepack.ResourcePackablePlugin;
import me.udnek.itemscoreu.util.VanillaItemManager;
import me.udnek.rpgu.block.Blocks;
import me.udnek.rpgu.command.DebugEquipmentCommand;
import me.udnek.rpgu.entity.EntityTypes;
import me.udnek.rpgu.entity.ModifiedEntitySpawnListener;
import me.udnek.rpgu.equipment.EquipmentListener;
import me.udnek.rpgu.equipment.PlayerWearingEquipmentTask;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.hud.Hud;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.mechanic.alloying.AlloyForgeManager;
import me.udnek.rpgu.mechanic.damaging.DamageListener;
import me.udnek.rpgu.mechanic.electricity.ElectricityEvents;
import me.udnek.rpgu.mechanic.enchanting.EnchantmentTableListener;
import me.udnek.rpgu.mechanic.rail.MinecartListener;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class RpgU extends JavaPlugin implements ResourcePackablePlugin {

    private static RpgU instance;
    private PlayerWearingEquipmentTask wearingEquipmentTask;

    public static RpgU getInstance() { return instance; }

    @Override
    public void onEnable() {
        instance = this;

        CustomItem blazeBlade = Items.SHINY_AXE;
        CustomBlock customBlock = Blocks.TEST;
        CustomEntityType ancientBreeze = EntityTypes.ANCIENT_BREEZE;
        SingleSlot artifacts = EquipmentSlots.FIRST_ARTIFACT;

        new DamageListener(this);
        new EquipmentListener(this);
        new ElectricityEvents(this);
        new EnchantmentTableListener(this);
        new ModifiedEntitySpawnListener(this);
        new MinecartListener(this);
        new TestListener(this);
        AlloyForgeManager.getInstance();

        this.getCommand("debugEquipmentU").setExecutor(new DebugEquipmentCommand());

        wearingEquipmentTask = new PlayerWearingEquipmentTask();
        wearingEquipmentTask.start(this);

        new Hud().register();

        ConstructableCustomAdvancement advancement = CustomAdvancementUtils.itemAdvancement(new NamespacedKey("rpg", "test"), Items.FABRIC.getItem());
        advancement.getDisplay().background("textures/block/cobblestone.png");
        advancement.register();
    }

    @Override
    public void onDisable() {
        if (wearingEquipmentTask == null) return;
        wearingEquipmentTask.stop();
    }
}
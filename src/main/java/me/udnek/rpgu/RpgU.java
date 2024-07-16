package me.udnek.rpgu;

import me.udnek.itemscoreu.customblock.CustomBlock;
import me.udnek.itemscoreu.customentity.CustomDumbTickingEntity;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.block.Blocks;
import me.udnek.rpgu.command.DebugEquipmentCommand;
import me.udnek.rpgu.mechanic.rail.MinecartListener;
import me.udnek.rpgu.mechanic.damaging.DamageListener;
import me.udnek.rpgu.mechanic.electricity.ElectricityEvents;
import me.udnek.rpgu.mechanic.enchanting.EnchantmentTableListener;
import me.udnek.rpgu.entity.Entities;
import me.udnek.rpgu.entity.ModifiedEntitySpawnListener;
import me.udnek.rpgu.equipment.EquipmentListener;
import me.udnek.rpgu.equipment.PlayerWearingEquipmentTask;
import me.udnek.rpgu.hud.Hud;
import me.udnek.rpgu.item.Items;
import org.bukkit.plugin.java.JavaPlugin;

public final class RpgU extends JavaPlugin {

    private static RpgU instance;
    private PlayerWearingEquipmentTask wearingEquipmentTask;

    public static RpgU getInstance() { return instance; }

    @Override
    public void onEnable() {
        instance = this;


        CustomItem blazeBlade = Items.BLAZE_BLADE;
        CustomDumbTickingEntity customDumbTickingEntity = Entities.energyVault;
        CustomBlock customBlock = Blocks.TEST;

        new DamageListener(this);
        new EquipmentListener(this);
        new ElectricityEvents(this);
        new EnchantmentTableListener(this);
        new ModifiedEntitySpawnListener(this);
        new MinecartListener(this);
        new TestListener(this);

        this.getCommand("debugEquipmentU").setExecutor(new DebugEquipmentCommand());

        wearingEquipmentTask = new PlayerWearingEquipmentTask();
        wearingEquipmentTask.start(this);

        new Hud().register();
    }

    @Override
    public void onDisable() {
        if (wearingEquipmentTask == null) return;
        wearingEquipmentTask.stop();
    }
}
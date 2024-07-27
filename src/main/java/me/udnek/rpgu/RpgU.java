package me.udnek.rpgu;

import me.udnek.itemscoreu.customblock.CustomBlock;
import me.udnek.itemscoreu.customentity.CustomEntityType;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.utils.VanillaItemDisabler;
import me.udnek.rpgu.block.Blocks;
import me.udnek.rpgu.command.DebugEquipmentCommand;
import me.udnek.rpgu.entity.EntityTypes;
import me.udnek.rpgu.entity.ModifiedEntitySpawnListener;
import me.udnek.rpgu.equipment.EquipmentListener;
import me.udnek.rpgu.equipment.PlayerWearingEquipmentTask;
import me.udnek.rpgu.hud.Hud;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.mechanic.damaging.DamageListener;
import me.udnek.rpgu.mechanic.electricity.ElectricityEvents;
import me.udnek.rpgu.mechanic.enchanting.EnchantmentTableListener;
import me.udnek.rpgu.mechanic.rail.MinecartListener;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public final class RpgU extends JavaPlugin {

    private static RpgU instance;
    private PlayerWearingEquipmentTask wearingEquipmentTask;

    public static RpgU getInstance() { return instance; }

    @Override
    public void onEnable() {
        instance = this;


        CustomItem blazeBlade = Items.BLAZE_BLADE;
        CustomBlock customBlock = Blocks.TEST;
        CustomEntityType ancientBreeze = EntityTypes.ANCIENT_BREEZE;

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

        VanillaItemDisabler.getInstance().disableItem(Material.NETHERITE_CHESTPLATE);
    }

    @Override
    public void onDisable() {
        if (wearingEquipmentTask == null) return;
        wearingEquipmentTask.stop();
    }
}
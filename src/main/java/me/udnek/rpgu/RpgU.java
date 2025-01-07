package me.udnek.rpgu;


import me.udnek.itemscoreu.customattribute.CustomAttribute;
import me.udnek.itemscoreu.customeffect.CustomEffect;
import me.udnek.itemscoreu.customequipmentslot.SingleSlot;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.resourcepack.ResourcePackablePlugin;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.command.*;
import me.udnek.rpgu.effect.Effects;
import me.udnek.rpgu.entity.EntityTypes;
import me.udnek.rpgu.entity.ModifiedEntitySpawnListener;
import me.udnek.rpgu.equipment.EquipmentListener;
import me.udnek.rpgu.equipment.PlayerWearingEquipmentTask;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.hud.Hud;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.mechanic.alloying.AlloyForgeManager;
import me.udnek.rpgu.mechanic.damaging.DamageListener;
import me.udnek.rpgu.mechanic.enchanting.EnchantingListener;
import me.udnek.rpgu.util.GeneralListener;
import me.udnek.rpgu.vanilla.AttributeManaging;
import org.bukkit.plugin.java.JavaPlugin;

public final class RpgU extends JavaPlugin implements ResourcePackablePlugin {

    private static RpgU instance;
    private PlayerWearingEquipmentTask wearingEquipmentTask;

    public static RpgU getInstance() { return instance; }

    @Override
    public void onEnable() {
        instance = this;

        CustomItem blazeBlade = Items.SHINY_AXE;
        EntityTypes.TOTEM_OF_SAVING.getRawId();
        SingleSlot artifacts = EquipmentSlots.FIRST_ARTIFACT;
        CustomAttribute magicalPotential = Attributes.MAGICAL_POTENTIAL;
        CustomEffect magicalResistance = Effects.MAGICAL_RESISTANCE;

        new DamageListener(this);
        new EquipmentListener(this);
        new EnchantingListener(this);
        new ModifiedEntitySpawnListener(this);
        new GeneralListener(this);
        new AttributeManaging(this);
        AlloyForgeManager.getInstance();

        Commands.declareCommands();

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
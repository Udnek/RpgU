package me.udnek.rpgu;


import me.udnek.itemscoreu.customattribute.CustomAttribute;
import me.udnek.itemscoreu.customeffect.CustomEffect;
import me.udnek.itemscoreu.customentitylike.block.CustomBlockType;
import me.udnek.itemscoreu.customentitylike.entity.CustomEntityType;
import me.udnek.itemscoreu.customequipmentslot.SingleSlot;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.customsound.CustomSound;
import me.udnek.itemscoreu.resourcepack.ResourcePackablePlugin;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.block.Blocks;
import me.udnek.rpgu.command.Commands;
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
import me.udnek.rpgu.util.AbilityListener;
import me.udnek.rpgu.util.GeneralListener;
import me.udnek.rpgu.util.Sounds;
import me.udnek.rpgu.vanilla.AttributeManaging;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class RpgU extends JavaPlugin implements ResourcePackablePlugin {

    private static RpgU instance;

    public static @NotNull RpgU getInstance() { return instance; }

    @Override
    public void onEnable() {
        instance = this;

        CustomItem blazeBlade = Items.SHINY_AXE;
        CustomEntityType totemOfSaving = EntityTypes.TOTEM_OF_SAVING;
        SingleSlot artifacts = EquipmentSlots.FIRST_ARTIFACT;
        CustomAttribute magicalPotential = Attributes.MAGICAL_POTENTIAL;
        CustomEffect magicalResistance = Effects.MAGICAL_RESISTANCE;
        CustomBlockType soulBinder = Blocks.SOUL_BINDER;
        CustomSound backstab = Sounds.BACKSTAB;

        new DamageListener(this);
        new EquipmentListener(this);
        new EnchantingListener(this);
        new ModifiedEntitySpawnListener(this);
        new GeneralListener(this);
        new AbilityListener(this);
        new AttributeManaging(this);
        AlloyForgeManager.getInstance();

        Commands.declareCommands();

        PlayerWearingEquipmentTask.getInstance().start(this);

        Hud.getInstance().register();
    }
}
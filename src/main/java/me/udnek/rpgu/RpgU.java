package me.udnek.rpgu;


import me.udnek.coreu.custom.attribute.CustomAttribute;
import me.udnek.coreu.custom.effect.CustomEffect;
import me.udnek.coreu.custom.entitylike.block.CustomBlockType;
import me.udnek.coreu.custom.entitylike.entity.CustomEntityType;
import me.udnek.coreu.custom.equipmentslot.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.custom.sound.CustomSound;
import me.udnek.coreu.resourcepack.ResourcePackablePlugin;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.block.Blocks;
import me.udnek.rpgu.command.Commands;
import me.udnek.rpgu.component.ability.Listeners;
import me.udnek.rpgu.effect.Effects;
import me.udnek.rpgu.entity.EntityTypes;
import me.udnek.rpgu.entity.ModifiedEntitySpawnListener;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.hud.Hud;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.mechanic.alloying.AlloyForgeManager;
import me.udnek.rpgu.mechanic.damaging.DamageListener;
import me.udnek.rpgu.mechanic.enchanting.EnchantingListener;
import me.udnek.rpgu.util.AbilityListener;
import me.udnek.rpgu.util.EntityListener;
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
        CustomEquipmentSlot.Single artifacts = EquipmentSlots.FIRST_ARTIFACT;
        CustomAttribute magicalPotential = Attributes.MAGICAL_POTENTIAL;
        CustomEffect magicalResistance = Effects.MAGIC_RESISTANCE;
        CustomBlockType soulBinder = Blocks.SOUL_BINDER;
        CustomSound backstab = Sounds.BACKSTAB;

        new DamageListener(this);
        new EnchantingListener(this);
        new ModifiedEntitySpawnListener(this);
        new GeneralListener(this);
        new AbilityListener(this);
        new AttributeManaging(this);
        new EntityListener(this);

        new Listeners(this);

        AlloyForgeManager.getInstance();

        Commands.declareCommands();

        Hud.getInstance().register();
    }

    @Override
    public @NotNull Priority getPriority() {
        return Priority.BASE;
    }
}
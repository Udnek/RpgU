package me.udnek.rpgu;


import me.udnek.coreu.custom.attribute.CustomAttribute;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.effect.CustomEffect;
import me.udnek.coreu.custom.entitylike.block.CustomBlockType;
import me.udnek.coreu.custom.entitylike.entity.CustomEntityType;
import me.udnek.coreu.custom.equipment.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.custom.recipe.CustomRecipeType;
import me.udnek.coreu.custom.sound.CustomSound;
import me.udnek.coreu.resourcepack.ResourcePackablePlugin;
import me.udnek.coreu.rpgu.component.RPGUActiveItem;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.block.Blocks;
import me.udnek.rpgu.command.Commands;
import me.udnek.rpgu.component.ComponentListener;
import me.udnek.rpgu.component.ability.Abilities;
import me.udnek.rpgu.component.ability.AbilityListener;
import me.udnek.rpgu.component.ability.VanillaAbilities;
import me.udnek.rpgu.component.ability.vanilla.ShieldCrashingAbility;
import me.udnek.rpgu.effect.Effects;
import me.udnek.rpgu.entity.EntityTypes;
import me.udnek.rpgu.entity.ModifiedEntitySpawnListener;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.hud.Hud;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.item.utility.AirElementalTome;
import me.udnek.rpgu.jeiu.JeiUListener;
import me.udnek.rpgu.mechanic.damaging.DamageListener;
import me.udnek.rpgu.mechanic.enchanting.EnchantingListener;
import me.udnek.rpgu.mechanic.enchanting.upgrade.EnchantingTableUpgrade;
import me.udnek.rpgu.mechanic.machine.RecipeTypes;
import me.udnek.rpgu.mechanic.machine.alloying.AlloyingRecipe;
import me.udnek.rpgu.mechanic.railing.VehicleListener;
import me.udnek.rpgu.util.GeneralListener;
import me.udnek.rpgu.util.Sounds;
import me.udnek.rpgu.vanilla.AttributeManaging;
import me.udnek.rpgu.vanilla.StructureManaging;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.NullUnmarked;

@NullMarked
public final class RpgU extends JavaPlugin implements ResourcePackablePlugin {

    private static @UnknownNullability RpgU instance;

    public static RpgU getInstance() {
        return instance;
    }

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
        EnchantingTableUpgrade end = EnchantingTableUpgrade.END;
        CustomComponentType<RPGUActiveItem, AirElementalTome.Ability> tome = Abilities.AIR_ELEMENTAL_TOME;
        CustomComponentType<RPGUActiveItem, ShieldCrashingAbility> shieldCrashing = VanillaAbilities.SHIELD_CRASHING;
        CustomRecipeType<AlloyingRecipe> alloying = RecipeTypes.ALLOYING;

        new DamageListener(this);
        new EnchantingListener(this);
        new ModifiedEntitySpawnListener(this);
        new GeneralListener(this);
        new AttributeManaging(this);
        new ComponentListener(this);
        new AbilityListener(this);
        new VehicleListener(this);
        new JeiUListener(this);

        Commands.declareCommands();

        Hud.getInstance().register();

        StructureManaging.run();
    }

    @Override
    public Priority getPriority() {
        return Priority.BASE;
    }
}
package me.udnek.rpgu.item;

import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.custom.registry.CustomRegistries;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.item.artifact.*;
import me.udnek.rpgu.item.artifact.sphere.SphereOfBalance;
import me.udnek.rpgu.item.artifact.sphere.SphereOfDiscord;
import me.udnek.rpgu.item.artifact.wreath.FlowerWreath;
import me.udnek.rpgu.item.artifact.wreath.WitherWreath;
import me.udnek.rpgu.item.block.AlloyForgeItem;
import me.udnek.rpgu.item.block.CrusherItem;
import me.udnek.rpgu.item.block.SoulBinderItem;
import me.udnek.rpgu.item.equipment.*;
import me.udnek.rpgu.item.equipment.Illagerite.IllageriteAxe;
import me.udnek.rpgu.item.equipment.Illagerite.IllageriteCrossbow;
import me.udnek.rpgu.item.equipment.Illagerite.IllageriteKey;
import me.udnek.rpgu.item.equipment.Illagerite.IllageriteUpgrade;
import me.udnek.rpgu.item.equipment.doloire.AmethystDoloire;
import me.udnek.rpgu.item.equipment.doloire.HeavyAmethystDoloire;
import me.udnek.rpgu.item.equipment.ferrudam.armor.FerrudamBoots;
import me.udnek.rpgu.item.equipment.ferrudam.armor.FerrudamChestplate;
import me.udnek.rpgu.item.equipment.ferrudam.armor.FerrudamHelmet;
import me.udnek.rpgu.item.equipment.ferrudam.armor.FerrudamLeggings;
import me.udnek.rpgu.item.equipment.ferrudam.tool.*;
import me.udnek.rpgu.item.equipment.flint.*;
import me.udnek.rpgu.item.equipment.grim.GrimChestplate;
import me.udnek.rpgu.item.equipment.grim.GrimHelmet;
import me.udnek.rpgu.item.equipment.hungry_horror_armor.HungryHorrorChestplate;
import me.udnek.rpgu.item.equipment.hungry_horror_armor.HungryHorrorHelmet;
import me.udnek.rpgu.item.equipment.magical.MagicalChestplate;
import me.udnek.rpgu.item.equipment.magical.MagicalSword;
import me.udnek.rpgu.item.equipment.quiver.Quiver;
import me.udnek.rpgu.item.equipment.wolf.WolfBoots;
import me.udnek.rpgu.item.equipment.wolf.WolfChestplate;
import me.udnek.rpgu.item.equipment.wolf.WolfHelmet;
import me.udnek.rpgu.item.equipment.wolf.WolfLeggings;
import me.udnek.rpgu.item.ingredients.*;
import me.udnek.rpgu.item.techincal.TechnicalInventoryFiller;
import me.udnek.rpgu.item.utility.*;
import org.bukkit.Color;
import org.bukkit.generator.structure.Structure;
import org.jetbrains.annotations.NotNull;

public class Items {

    // ARTIFACTS
    public static final CustomItem NAUTILUS_CORE = register(new NautilusCore());
    public static final CustomItem FISHERMAN_SNORKEL = register(new FishermanSnorkel());
    public static final CustomItem RUSTY_IRON_RING = register(new RustyIronRing());
    public static final CustomItem CRITICAL_STONE = register(new CriticalStone());
    public static final CustomItem SPELL_PRISM = register(new SpellPrism());

    public static final CustomItem FLOWER_WREATH = register(new FlowerWreath());
    public static final CustomItem WITHER_WREATH = register(new WitherWreath());

    public static final CustomItem SPHERE_OF_BALANCE = register(new SphereOfBalance());
    public static final CustomItem SPHERE_OF_DISCORD = register(new SphereOfDiscord());

    // EQUIPMENT
    public static final CustomItem SHINY_AXE = register(new ShinyAxe());
    public static final CustomItem CEREMONIOUS_DAGGER = register(new CeremonialDagger());
    public static final CustomItem PHANTOM_BOW = register(new PhantomBow());

    public static final CustomItem FERRUDAM_SWORD = register(new FerrudamSword());
    public static final CustomItem FERRUDAM_PICKAXE = register(new FerrudamPickaxe());
    public static final CustomItem FERRUDAM_AXE = register(new FerrudamAxe());
    public static final CustomItem FERRUDAM_SHOVEL = register(new FerrudamShovel());
    public static final CustomItem FERRUDAM_HOE = register(new FerrudamHoe());

    public static final CustomItem FERRUDAM_HELMET = register(new FerrudamHelmet());
    public static final CustomItem FERRUDAM_CHESTPLATE = register(new FerrudamChestplate());
    public static final CustomItem FERRUDAM_LEGGINGS = register(new FerrudamLeggings());
    public static final CustomItem FERRUDAM_BOOTS = register(new FerrudamBoots());

    public static final CustomItem HUNGRY_HORROR_HELMET = register(new HungryHorrorHelmet());
    public static final CustomItem HUNGRY_HORROR_CHESTPLATE = register(new HungryHorrorChestplate());

    public static final CustomItem WOLF_HELMET = register(new WolfHelmet());
    public static final CustomItem WOLF_CHESTPLATE = register(new WolfChestplate());
    public static final CustomItem WOLF_LEGGINGS = register(new WolfLeggings());
    public static final CustomItem WOLF_BOOTS = register(new WolfBoots());
    public static final CustomItem ARMADILLO_SCUTE_WOLF_ARMOR = register(new ArmadilloScuteWolfArmor());

    public static final CustomItem GRIM_HELMET = register(new GrimHelmet());
    public static final CustomItem GRIM_CHESTPLATE = register(new GrimChestplate());

    public static final CustomItem FLINT_SWORD = register(new FlintSword());
    public static final CustomItem FLINT_PICKAXE = register(new FlintPickaxe());
    public static final CustomItem FLINT_AXE = register(new FlintAxe());
    public static final CustomItem FLINT_SHOVEL = register(new FlintShovel());
    public static final CustomItem FLINT_HOE = register(new FlintHoe());

    public static final CustomItem PHANTOM_CHESTPLATE = register(new PhantomChestplate());

    public final static CustomItem AMETHYST_DIRK = register(new AmethystDirk());
    public final static CustomItem AMETHYST_DOLOIRE = register(new AmethystDoloire());
    public final static CustomItem HEAVY_AMETHYST_DOLOIRE = register(new HeavyAmethystDoloire());

    public static final CustomItem EVOCATION_ROBE = register(new EvocationRobe());

    public static final CustomItem QUIVER = register(new Quiver());

    public static final CustomItem ILLAGERITE_AXE = register(new IllageriteAxe());
    public static final CustomItem ILLAGERITE_CROSSBOW = register(new IllageriteCrossbow());
    public static final CustomItem ILLAGERITE_KEY = register(new IllageriteKey());
    public static final CustomItem ILLAGERITE_UPGRADE = register(new IllageriteUpgrade());

    // INGREDIENT
    public static final CustomItem FABRIC = register(new Fabric());
    public static final CustomItem FERRUDAM_INGOT = register(new FerrudamIngot());
    public static final CustomItem BLAST_COAL = register(new BlastCoal());
    public static final CustomItem INGOT_MOLD = register(new IngotMold());
    public static final CustomItem RAW_MAGNETITE = register(new RawMagnetite());
    public static final CustomItem MAGNETITE_INGOT = register(new MagnetiteIngot());
    public static final CustomItem PHANTOM_WING = register(new PhantomWing());
    public static final CustomItem ESOTERIC_SALVE = register(new EsotericSalve());
    public static final CustomItem WOLF_PELT = register(new WolfPelt());
    public static final CustomItem WEAK_MAGIC_CORE = register(new WeakMagicCore());
    public static final CustomItem ILLAGERITE = register(new Illagerite());

    // TECHNICAL
    public static final  CustomItem TECHNICAL_INVENTORY_FILLER = register(new TechnicalInventoryFiller());

    // UTILITY
    public static final CustomItem WRENCH = register(new Wrench());
    public static final CustomItem DARK_MIRROR = register(new DarkMirror());
    public static final CustomItem TOTEM_OF_SAVING = register(new TotemOfSavingItem());
    public static final CustomItem ARMADILLO_BAR = register(new ArmadilloBar());
    public static final CustomItem AIR_ELEMENTAL_TOME = register(new AirElementalTome());
    public static final CustomItem SHAMAN_TAMBOURINE = register(new ShamanTambourine());
    public static final CustomItem NATURES_STAFF = register(new NaturesStaff());
    public static final CustomItem NATURES_COMPASS = register(new NaturesCompass());
    public static final CustomItem FOLDED_MAP_MANSION =
            register(new FoldedMap(Structure.MANSION, Color.BLUE, "Map of Mansion", "Карта особняка")); //TODO поменять названия
    public static final CustomItem FOLDED_MAP_BURIED_TREASURE =
            register(new FoldedMap(Structure.BURIED_TREASURE, Color.AQUA, "Map of Treasure", "Карта сокровища")); //TODO поменять названия

    // EXAMPLES
    public static final CustomItem MAGICAL_CHESTPLATE = register(new MagicalChestplate());
    public static final CustomItem MAGICAL_SWORD = register(new MagicalSword());

    // BLOCKS
    public static final CustomItem SOUL_BINDER = register(new SoulBinderItem());
    public static final CustomItem CRUSHER = register(new CrusherItem());
    public static final CustomItem ALLOY_FORGE = register(new AlloyForgeItem());


    private static @NotNull CustomItem register(@NotNull CustomItem customItem){
        return CustomRegistries.ITEM.register(RpgU.getInstance(), customItem);
    }
}

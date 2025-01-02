package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.customregistry.CustomRegistries;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.item.artifact.*;
import me.udnek.rpgu.item.artifact.sphere.SphereOfBalance;
import me.udnek.rpgu.item.artifact.sphere.SphereOfDiscord;
import me.udnek.rpgu.item.artifact.wreath.FlowerWreath;
import me.udnek.rpgu.item.artifact.wreath.WitherWreath;
import me.udnek.rpgu.item.equipment.*;
import me.udnek.rpgu.item.equipment.doloire.AmethystDoloire;
import me.udnek.rpgu.item.equipment.doloire.HeavyAmethystDoloire;
import me.udnek.rpgu.item.equipment.ferrudam.armor.FerrudamBoots;
import me.udnek.rpgu.item.equipment.ferrudam.armor.FerrudamChestplate;
import me.udnek.rpgu.item.equipment.ferrudam.armor.FerrudamHelmet;
import me.udnek.rpgu.item.equipment.ferrudam.armor.FerrudamLeggings;
import me.udnek.rpgu.item.equipment.ferrudam.tools.*;
import me.udnek.rpgu.item.equipment.flint.*;
import me.udnek.rpgu.item.equipment.grim.GrimChestplate;
import me.udnek.rpgu.item.equipment.grim.GrimHelmet;
import me.udnek.rpgu.item.equipment.hungry_horror_armor.HungryHorrorChestplate;
import me.udnek.rpgu.item.equipment.hungry_horror_armor.HungryHorrorHelmet;
import me.udnek.rpgu.item.equipment.magical.MagicalChestplate;
import me.udnek.rpgu.item.equipment.magical.MagicalSword;
import me.udnek.rpgu.item.equipment.wolf.WolfBoots;
import me.udnek.rpgu.item.equipment.wolf.WolfChestplate;
import me.udnek.rpgu.item.equipment.wolf.WolfHelmet;
import me.udnek.rpgu.item.equipment.wolf.WolfLeggings;
import me.udnek.rpgu.item.ingredients.*;
import me.udnek.rpgu.item.techincal.TechnicalInventoryFiller;
import me.udnek.rpgu.item.utility.*;

public class Items {

    // ARTIFACTS
    public final static CustomItem NAUTILUS_CORE = register(new NautilusCore());
    public final static CustomItem FISHERMAN_SNORKEL = register(new FishermanSnorkel());
    public final static CustomItem RUSTY_IRON_RING = register(new RustyIronRing());
    public final static CustomItem BLOOD_STONE = register(new BloodStone());
    public final static CustomItem HEALTH_STONE = register(new HealthStone());
    public final static CustomItem ARCANE_ACCUMULATOR = register(new ArcaneAccumulator());

    public final static CustomItem FLOWER_WREATH = register(new FlowerWreath());
    public static final CustomItem WITHER_WREATH = register(new WitherWreath());

    public final static CustomItem SPHERE_OF_BALANCE = register(new SphereOfBalance());
    public final static CustomItem SPHERE_OF_DISCORD = register(new SphereOfDiscord());

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

    // TECHNICAL
    public static final  CustomItem TECHNICAL_INVENTORY_FILLER = register(new TechnicalInventoryFiller());

    // UTILITY
    public final static CustomItem WRENCH = register(new Wrench());
    public static final CustomItem DARK_MIRROR = register(new DarkMirror());
    public static final CustomItem TOTEM_OF_SAVING = register(new TotemOfSavingItem());
    public static final CustomItem ARMADILLO_BAR = register(new ArmadilloBar());
    public static final CustomItem AIR_ELEMENTAL_TOME = register(new AirElementalTome());
    public static final CustomItem SHAMAN_TAMBOURINE = register(new ShamanTambourine());
    public static final CustomItem NATURES_STAFF = register(new NaturesStaff());

    // EXAMPLES
    public static final CustomItem MAGICAL_CHESTPLATE = register(new MagicalChestplate());
    public static final CustomItem MAGICAL_SWORD = register(new MagicalSword());


    private static CustomItem register(CustomItem customItem){
        return CustomRegistries.ITEM.register(RpgU.getInstance(), customItem);
    }
}

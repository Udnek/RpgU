package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.customregistry.CustomRegistries;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.item.artifact.*;
import me.udnek.rpgu.item.equipment.*;
import me.udnek.rpgu.item.equipment.ferrudam.armor.FerrudamBoots;
import me.udnek.rpgu.item.equipment.ferrudam.armor.FerrudamChestplate;
import me.udnek.rpgu.item.equipment.ferrudam.armor.FerrudamHelmet;
import me.udnek.rpgu.item.equipment.ferrudam.armor.FerrudamLeggings;
import me.udnek.rpgu.item.equipment.ferrudam.tools.*;
import me.udnek.rpgu.item.equipment.flint.*;
import me.udnek.rpgu.item.equipment.hungry_horror_armor.HungryHorrorChestplate;
import me.udnek.rpgu.item.equipment.hungry_horror_armor.HungryHorrorHelmet;
import me.udnek.rpgu.item.equipment.magical.MagicalChestplate;
import me.udnek.rpgu.item.equipment.magical.MagicalSword;
import me.udnek.rpgu.item.equipment.shaman.ShamanBoots;
import me.udnek.rpgu.item.equipment.shaman.ShamanHelmet;
import me.udnek.rpgu.item.equipment.shaman.ShamanTambourine;
import me.udnek.rpgu.item.ingredients.*;
import me.udnek.rpgu.item.techincal.TechnicalInventoryFiller;
import me.udnek.rpgu.item.utility.*;

public class Items {

    // ARTIFACTS
    public final static CustomItem NAUTILUS_CORE = register(new NautilusCore());
    public final static CustomItem FISHERMAN_SNORKEL = register(new FishermanSnorkel());
    public final static CustomItem RUSTY_IRON_RING = register(new RustyIronRing());

    public final static CustomItem FLOWER_WREATH = register(new FlowerWreath());
    public static final CustomItem WITHER_WREATH = register(new WitherWreath());

    public final static CustomItem SPHERE_OF_BALANCE = register(new SphereOfBalance());
    public final static CustomItem SPHERE_OF_DISCORD = register(new SphereOfDiscord());

    public final static CustomItem CRUDE_AMETHYST_SWORD = register(new CrudeAmethystSword());

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

    public static final CustomItem SHAMAN_HELMET = register(new ShamanHelmet());
    public static final CustomItem SHAMAN_BOOTS = register(new ShamanBoots());

    public static final CustomItem FLINT_SWORD = register(new FlintSword());
    public static final CustomItem FLINT_PICKAXE = register(new FlintPickaxe());
    public static final CustomItem FLINT_AXE = register(new FlintAxe());
    public static final CustomItem FLINT_SHOVEL = register(new FlintShovel());
    public static final CustomItem FLINT_HOE = register(new FlintHoe());

    public static final CustomItem PHANTOM_CHESTPLATE = register(new PhantomChestplate());

    public static final CustomItem SHAMAN_TAMBOURINE = register(new ShamanTambourine());

    // INGREDIENT
    public static final CustomItem FABRIC = register(new Fabric());
    public static final CustomItem FERRUDAM_INGOT = register(new FerrudamIngot());
    public static final CustomItem BLAST_COAL = register(new BlastCoal());
    public static final CustomItem INGOT_MOLD = register(new IngotMold());
    public static final CustomItem RAW_MAGNETITE = register(new RawMagnetite());
    public static final CustomItem MAGNETITE_INGOT = register(new MagnetiteIngot());
    public static final CustomItem PHANTOM_WING = register(new PhantomWing());
    public static final CustomItem MAGICAL_DYE = register(new MagicalDye());

    // TECHNICAL
    public static final  CustomItem TECHNICAL_INVENTORY_FILLER = register(new TechnicalInventoryFiller());

    // UTILITY
    public final static CustomItem WRENCH = register(new Wrench());
    public static final CustomItem DARK_MIRROR = register(new DarkMirror());
    public static final CustomItem TOTEM_OF_SAVING = register(new TotemOfSavingItem());
    public static final CustomItem ARMADILLO_BAR = register(new ArmadilloBar());

    // EXAMPLES
    public static final CustomItem MAGICAL_CHESTPLATE = register(new MagicalChestplate());
    public static final CustomItem MAGICAL_SWORD = register(new MagicalSword());


    private static CustomItem register(CustomItem customItem){
        return CustomRegistries.ITEM.register(RpgU.getInstance(), customItem);
    }
}

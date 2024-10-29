package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.customregistry.CustomRegistries;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.item.equipment.CeremonialDagger;
import me.udnek.rpgu.item.equipment.ShinyAxe;
import me.udnek.rpgu.item.equipment.ferrudam.armor.FerrudamBoots;
import me.udnek.rpgu.item.equipment.ferrudam.armor.FerrudamChestplate;
import me.udnek.rpgu.item.equipment.ferrudam.armor.FerrudamHelmet;
import me.udnek.rpgu.item.equipment.ferrudam.armor.FerrudamLeggings;
import me.udnek.rpgu.item.equipment.ferrudam.tools.*;
import me.udnek.rpgu.item.equipment.flint.*;
import me.udnek.rpgu.item.equipment.magical.MagicalChestplate;
import me.udnek.rpgu.item.equipment.magical.MagicalSword;
import me.udnek.rpgu.item.ingredients.*;
import me.udnek.rpgu.item.techincal.TechnicalInventoryFiller;

public class Items {
    public static final  CustomItem TECHNICAL_INVENTORY_FILLER = register(new TechnicalInventoryFiller());
    //public final static CustomItem BLAZE_BLADE = register(new BlazeBlade());
    //public final static CustomItem LIGHT_FEATHER = register(new LightFeather());
/*    public final static CustomItem LIFE_CRYSTAL = register(new LifeCrystal());
    public final static CustomItem HUNGRY_HORROR_HELMET =  register(new HungryHorrorHelmet());
    public final static CustomItem HUNGRY_HORROR_CHESTPLATE = register(new HungryHorrorChestplate());
    public final static CustomItem IRON_SHURIKEN = register(new IronShuriken());
    public final static CustomItem WARPING_CLOCK = register(new WarpingClock());
    public final static CustomItem CEREMONIOUS_DAGGER = register(new CeremoniousDagger());
    public final static CustomItem FLOWER_WREATH = register(new FlowerWreath());
    public final static CustomItem WITHER_WREATH = register(new WitherWreath());
    public final static CustomItem NAUTILUS_CORE = register(new NautilusCore());
    public final static CustomItem SPHERE_OF_BALANCE = register(new SphereOfBalance());
    public final static CustomItem PHANTOM_BOW = register(new PhantomBow());
    public final static CustomItem FISHERMAN_SNORKEL = register(new FishermanSnorkel());
    public final static CustomItem COPPER_SWORD = register(new CopperSword());
    public final static CustomItem COPPER_PICKAXE = register(new CopperPickaxe());
    public final static CustomItem WRENCH = register(new Wrench());
    public final static CustomItem INJECTOR = register(new Injector());*/

    public static final CustomItem SHINY_AXE = register(new ShinyAxe());
    public static final CustomItem CEREMONIOUS_DAGGER = register(new CeremonialDagger());

    public static final CustomItem FABRIC = register(new Fabric());

    public static final CustomItem FERRUDAM_INGOT = register(new FerrudamIngot());

    public static final CustomItem FERRUDAM_SWORD = register(new FerrudamSword());
    public static final CustomItem FERRUDAM_PICKAXE = register(new FerrudamPickaxe());
    public static final CustomItem FERRUDAM_AXE = register(new FerrudamAxe());
    public static final CustomItem FERRUDAM_SHOVEL = register(new FerrudamShovel());
    public static final CustomItem FERRUDAM_HOE = register(new FerrudamHoe());

    public static final CustomItem FERRUDAM_HELMET = register(new FerrudamHelmet());
    public static final CustomItem FERRUDAM_CHESTPLATE = register(new FerrudamChestplate());
    public static final CustomItem FERRUDAM_LEGGINGS = register(new FerrudamLeggings());
    public static final CustomItem FERRUDAM_BOOTS = register(new FerrudamBoots());

    public static final CustomItem BLAST_COAL = register(new BlastCoal());

    public static final CustomItem INGOT_MOLD = register(new IngotMold());

    public static final CustomItem FLINT_SWORD = register(new FlintSword());
    public static final CustomItem FLINT_PICKAXE = register(new FlintPickaxe());
    public static final CustomItem FLINT_AXE = register(new FlintAxe());
    public static final CustomItem FLINT_SHOVEL = register(new FlintShovel());
    public static final CustomItem FLINT_HOE = register(new FlintHoe());

    public static final CustomItem RAW_MAGNETITE = register(new RawMagnetite());
    public static final CustomItem MAGNETITE_INGOT = register(new MagnetiteIngot());

    public static final CustomItem MAGICAL_CHESTPLATE = register(new MagicalChestplate());
    public static final CustomItem MAGICAL_SWORD = register(new MagicalSword());

    private static CustomItem register(CustomItem customItem){
        return CustomRegistries.ITEM.register(RpgU.getInstance(), customItem);
    }
}

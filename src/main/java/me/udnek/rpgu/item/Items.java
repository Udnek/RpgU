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
import me.udnek.rpgu.item.ingredients.*;
import me.udnek.rpgu.item.techincal.TechnicalInventoryFiller;

public class Items {
    public final static CustomItem TECHNICAL_INVENTORY_FILLER = register(new TechnicalInventoryFiller());
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

    public final static CustomItem SHINY_AXE = register(new ShinyAxe());
    public final static CustomItem CEREMONIOUS_DAGGER = register(new CeremonialDagger());

    public final static CustomItem FABRIC = register(new Fabric());

    public final static CustomItem FERRUDAM_INGOT = register(new FerrudamIngot());

    public final static CustomItem FERRUDAM_SWORD = register(new FerrudamSword());
    public final static CustomItem FERRUDAM_PICKAXE = register(new FerrudamPickaxe());
    public final static CustomItem FERRUDAM_AXE = register(new FerrudamAxe());
    public final static CustomItem FERRUDAM_SHOVEL = register(new FerrudamShovel());
    public final static CustomItem FERRUDAM_HOE = register(new FerrudamHoe());

    public final static CustomItem FERRUDAM_HELMET = register(new FerrudamHelmet());
    public final static CustomItem FERRUDAM_CHESTPLATE = register(new FerrudamChestplate());
    public final static CustomItem FERRUDAM_LEGGINGS = register(new FerrudamLeggings());
    public final static CustomItem FERRUDAM_BOOTS = register(new FerrudamBoots());

    public final static CustomItem BLAST_COAL = register(new BlastCoal());

    public final static CustomItem INGOT_MOLD = register(new IngotMold());

    public final static CustomItem FLINT_SWORD = register(new FlintSword());
    public final static CustomItem FLINT_PICKAXE = register(new FlintPickaxe());
    public final static CustomItem FLINT_AXE = register(new FlintAxe());
    public final static CustomItem FLINT_SHOVEL = register(new FlintShovel());
    public final static CustomItem FLINT_HOE = register(new FlintHoe());

    public final static CustomItem RAW_MAGNETITE = register(new RawMagnetite());
    public final static CustomItem MAGNETITE_INGOT = register(new MagnetiteIngot());

    private static CustomItem register(CustomItem customItem){
        return CustomRegistries.ITEM.register(RpgU.getInstance(), customItem);
    }
}

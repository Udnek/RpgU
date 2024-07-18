package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.customitem.CustomItemManager;
import me.udnek.rpgu.RpgU;

public class Items {

    public final static CustomItem BLAZE_BLADE = register(new BlazeBlade());
    public final static CustomItem LIGHT_FEATHER = register(new LightFeather());
    public final static CustomItem LIFE_CRYSTAL = register(new LifeCrystal());
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
    public final static CustomItem INJECTOR = register(new Injector());
    public final static CustomItem SHINY_AXE = register(new ShinyAxe());

    public final static CustomItem TEST_BLOCK = register(new TestBlockItem());

    private static CustomItem register(CustomItem customItem){
        return CustomItemManager.getInstance().register(RpgU.getInstance(), customItem);
    }
}

package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.customitem.CustomItemManager;
import me.udnek.rpgu.RpgU;

public class Items {

    public final static CustomItem blazeBlade = register(new BlazeBlade());
    public final static CustomItem lightFeather = register(new LightFeather());
    public final static CustomItem lifeCrystal  = register(new LifeCrystal());
    public final static CustomItem hungryHorrorHelmet =  register(new HungryHorrorHelmet());
    public final static CustomItem hungryHorrorChestplate = register(new HungryHorrorChestplate());
    public final static CustomItem ironShuriken = register(new IronShuriken());
    public final static CustomItem warpingClock = register(new WarpingClock());
    public final static CustomItem ceremoniousDagger = register(new CeremoniousDagger());
    public final static CustomItem flowerWreath = register(new FlowerWreath());
    public final static CustomItem witherWreath = register(new WitherWreath());
    public final static CustomItem nautilusCore = register(new NautilusCore());
    public final static CustomItem sphereOfBalance = register(new SphereOfBalance());
    public final static CustomItem phantomBow = register(new PhantomBow());
    public final static CustomItem fishermanSnorkel = register(new FishermanSnorkel());
    public final static CustomItem copperSword = register(new CopperSword());
    public final static CustomItem copperPickaxe = register(new CopperPickaxe());
    public final static CustomItem wrench = register(new Wrench());

    public final static CustomItem testBlock = register(new TestBlockItem());

    private static CustomItem register(CustomItem customItem){
        return CustomItemManager.register(RpgU.getInstance(), customItem);
    }
}

package me.udnek.rpgu.component.ability;

import me.udnek.coreu.custom.component.ConstructableComponentType;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.registry.CustomRegistries;
import me.udnek.coreu.rpgu.component.RPGUActiveItem;
import me.udnek.coreu.rpgu.component.RPGUPassiveItem;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.component.ability.vanilla.*;

public class VanillaAbilities {

    //PASSIVE
    public static final CustomComponentType<RPGUPassiveItem, DeathProtectionPassive> DEATH_PROTECTION =
            register(new ConstructableComponentType<>("death_protection", DeathProtectionPassive.DEFAULT));
    public static final CustomComponentType<RPGUPassiveItem, GliderPassive> GLIDER =
            register(new ConstructableComponentType<>("glider", GliderPassive.DEFAULT));

    //ACTIVE
    public static final CustomComponentType<RPGUActiveItem, BowAbility> BOW =
            register(new ConstructableComponentType<>("bow", BowAbility.DEFAULT));
    public static final CustomComponentType<RPGUActiveItem, CrossbowAbility> CROSSBOW =
            register(new ConstructableComponentType<>("crossbow", CrossbowAbility.DEFAULT));
    public static final CustomComponentType<RPGUActiveItem, ShieldAbility> SHIELD =
            register(new ConstructableComponentType<>("shield", ShieldAbility.DEFAULT));
    public static final CustomComponentType<RPGUActiveItem, ShieldCrashingAbility> SHIELD_CRASHING =
            register(new ConstructableComponentType<>("shield_crashing", ShieldCrashingAbility.DEFAULT));


    private static <T extends CustomComponentType<?, ?>> T register(T type){
        return CustomRegistries.COMPONENT_TYPE.register(RpgU.getInstance(), type);
    }
}

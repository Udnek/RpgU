package me.udnek.rpgu.component.ability;

import me.udnek.coreu.custom.component.ConstructableComponentType;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.registry.CustomRegistries;
import me.udnek.coreu.rpgu.component.RPGUActiveItem;
import me.udnek.coreu.rpgu.component.RPGUPassiveItem;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.component.ability.instance.AllyBuffingAuraPassive;
import me.udnek.rpgu.component.ability.instance.FlowerWreathPassive;
import me.udnek.rpgu.component.ability.instance.SwordDashAbility;
import me.udnek.rpgu.item.artifact.FishermanSnorkel;
import me.udnek.rpgu.item.artifact.NautilusCore;
import me.udnek.rpgu.item.artifact.wreath.WitherWreath;
import me.udnek.rpgu.item.equipment.doloire.AmethystDoloire;
import me.udnek.rpgu.item.equipment.hungry_horror_armor.HungryHorrorArmor;
import me.udnek.rpgu.item.equipment.quiver.QuiverChangeArrowAbility;
import me.udnek.rpgu.item.equipment.quiver.QuiverShootAbility;
import me.udnek.rpgu.item.utility.*;

public class Abilities {

    //PASSIVE
    public static final CustomComponentType<RPGUPassiveItem, TotemOfSavingItem.Passive> TOTEM_OF_SAVING =
            register(new ConstructableComponentType<>("totem_of_saving", TotemOfSavingItem.Passive.DEFAULT));
    public static final CustomComponentType<RPGUPassiveItem, NautilusCore.Passive> NAUTILUS_CORE =
            register(new ConstructableComponentType<>("nautilus_core", NautilusCore.Passive.DEFAULT));
    public static final CustomComponentType<RPGUPassiveItem, AllyBuffingAuraPassive> ALLY_BUFFING_AURA =
            register(new ConstructableComponentType<>("ally_buffing_aura", AllyBuffingAuraPassive.DEFAULT));
    public static final CustomComponentType<RPGUPassiveItem, FlowerWreathPassive> FLOWER_WREATH =
            register(new ConstructableComponentType<>("flower_wreath", FlowerWreathPassive.DEFAULT));
    public static final CustomComponentType<RPGUPassiveItem, WitherWreath.Passive> WITHER_WREATH =
            register(new ConstructableComponentType<>("wither_wreath", WitherWreath.Passive.DEFAULT));
    public static final CustomComponentType<RPGUPassiveItem, FishermanSnorkel.Passive> FISHERMAN_SNORKEL =
            register(new ConstructableComponentType<>("fisherman_snorkel", FishermanSnorkel.Passive.DEFAULT));
    public static final CustomComponentType<RPGUPassiveItem, QuiverShootAbility> QUIVER_SHOOT =
            register(new ConstructableComponentType<>("quiver_shoot", QuiverShootAbility.DEFAULT));
    public static final CustomComponentType<RPGUPassiveItem, HungryHorrorArmor.Passive> HUNGRY_HORROR =
            register(new ConstructableComponentType<>("hungry_horror", HungryHorrorArmor.Passive.DEFAULT));

    //ACTIVE
    public static final CustomComponentType<RPGUActiveItem, QuiverChangeArrowAbility> QUIVER_CHANGE_ARROW =
            register(new ConstructableComponentType<>("quiver_change_arrow", QuiverChangeArrowAbility.DEFAULT));
    public static final CustomComponentType<RPGUActiveItem, AirElementalTome.Ability> AIR_ELEMENTAL_TOME =
            register(new ConstructableComponentType<>("air_elemental_tome", AirElementalTome.Ability.DEFAULT));
    public static final CustomComponentType<RPGUActiveItem, NaturesStaff.Ability> NATURES_STAFF =
            register(new ConstructableComponentType<>("natures_staff", NaturesStaff.Ability.DEFAULT));
    public static final CustomComponentType<RPGUActiveItem, ShamanTambourine.Ability> SHAMAN_TAMBOURINE =
            register(new ConstructableComponentType<>("shaman_tambourine", ShamanTambourine.Ability.DEFAULT));
    public static final CustomComponentType<RPGUActiveItem, AmethystDoloire.Ability> AMETHYST_DOLOIRE =
            register(new ConstructableComponentType<>("amethyst_doloire", AmethystDoloire.Ability.DEFAULT));
    public static final CustomComponentType<RPGUActiveItem, ArmadilloBar.Ability> ARMADILLO_BAR =
            register(new ConstructableComponentType<>("armadillo_bar", ArmadilloBar.Ability.DEFAULT));
    public static final CustomComponentType<RPGUActiveItem, SwordDashAbility> SWORD_DASH =
            register(new ConstructableComponentType<>("sword_dash", SwordDashAbility.DEFAULT));
    public static final CustomComponentType<RPGUActiveItem, NaturesCompass.Ability> NATURES_COMPASS =
            register(new ConstructableComponentType<>("natures_compass", NaturesCompass.Ability.DEFAULT));
    public static final CustomComponentType<RPGUActiveItem, FoldedMap.Ability> FOLDED_MAP =
            register(new ConstructableComponentType<>("folded_map", FoldedMap.Ability.DEFAULT));


    private static <T extends CustomComponentType<?, ?>> T register(T type){
        return CustomRegistries.COMPONENT_TYPE.register(RpgU.getInstance(), type);
    }
}

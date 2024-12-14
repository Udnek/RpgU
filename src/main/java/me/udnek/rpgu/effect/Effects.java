package me.udnek.rpgu.effect;

import me.udnek.itemscoreu.customeffect.CustomEffect;
import me.udnek.itemscoreu.customregistry.CustomRegistries;
import me.udnek.rpgu.RpgU;

public class Effects {
    public static final CustomEffect MAGICAL_RESISTANCE = register(new MagicalResistance());
    public static final CustomEffect INCREASED_FALL_DAMAGE = register(new IncreasedFallDamage());
    public static final CustomEffect NO_FALL_DAMAGE = register(new NoFallDamage());
    public static final CustomEffect NO_GRAVITY = register(new NoGravity());
    public static final CustomEffect BONUS_AREA_OF_EFFECT = register(new BonusAreaOfEffect());
    public static final CustomEffect ROOT_EFFECT = register(new RootEffect());
    public static final CustomEffect MIRE_TOUCH = register(new MireTouch());

    private static CustomEffect register(CustomEffect customEffect){
        return CustomRegistries.EFFECT.register(RpgU.getInstance(), customEffect);
    }
}

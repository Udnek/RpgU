package me.udnek.rpgu.effect;

import me.udnek.itemscoreu.customeffect.CustomEffect;
import me.udnek.itemscoreu.customregistry.CustomRegistries;
import me.udnek.rpgu.RpgU;

public class Effects {
    public static final CustomEffect MAGICAL_RESISTANCE = register(new MagicalResistance());
    public static final CustomEffect INCREASED_FALL_DAMAGE = register(new IncreasedFallDamage());
    public static final CustomEffect NO_FALL_DAMAGE = register(new NoFallDamage());
    public static final CustomEffect NO_GRAVITY = register(new NoGravity());
    public static final CustomEffect AREA_OF_EFFECT = register(new BonusAreaOfEffect());

    private static CustomEffect register(CustomEffect customEffect){
        return CustomRegistries.EFFECT.register(RpgU.getInstance(), customEffect);
    }
}

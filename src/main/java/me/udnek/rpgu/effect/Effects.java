package me.udnek.rpgu.effect;

import me.udnek.coreu.custom.effect.CustomEffect;
import me.udnek.coreu.custom.registry.CustomRegistries;
import me.udnek.rpgu.RpgU;

public class Effects {
    public static final CustomEffect MAGIC_RESISTANCE = register(new MagicResistanceEffect());
    public static final CustomEffect HEAVY_FALLING = register(new HeavyFallingEffect());
    public static final CustomEffect NO_FALL_DAMAGE = register(new NoFallDamageEffect());
    public static final CustomEffect NO_GRAVITY = register(new NoGravityEffect());
    public static final CustomEffect BONUS_AREA_OF_EFFECT = register(new BonusAreaOfEffect());
    public static final CustomEffect ROOT_EFFECT = register(new RootEffect());
    public static final CustomEffect MIRE_TOUCH = register(new MireTouchEffect());
    public static final CustomEffect STUN_EFFECT = register(new StunEffect());

    private static CustomEffect register(CustomEffect customEffect){
        return CustomRegistries.EFFECT.register(RpgU.getInstance(), customEffect);
    }
}

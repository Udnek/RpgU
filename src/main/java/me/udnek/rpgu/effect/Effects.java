package me.udnek.rpgu.effect;

import me.udnek.itemscoreu.customeffect.CustomEffect;
import me.udnek.itemscoreu.customregistry.CustomRegistries;
import me.udnek.rpgu.RpgU;

public class Effects {
    public static final CustomEffect MAGICAL_RESISTANCE = register(new MagicalResistance());

    private static CustomEffect register(CustomEffect customEffect){
        return CustomRegistries.EFFECT.register(RpgU.getInstance(), customEffect);
    }
}

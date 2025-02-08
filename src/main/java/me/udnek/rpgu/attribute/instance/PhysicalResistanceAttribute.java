package me.udnek.rpgu.attribute.instance;

import me.udnek.itemscoreu.customattribute.ConstructableReversedCustomAttribute;
import me.udnek.rpgu.attribute.Attributes;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class PhysicalResistanceAttribute extends ConstructableReversedCustomAttribute {

    public PhysicalResistanceAttribute(@NotNull String rawId, double defaultValue, double min, double max) {
        super(rawId, defaultValue, min, max);
    }

    @Override
    public double calculateWithBase(@NotNull LivingEntity entity, double base) {
        PotionEffect physResistance = entity.getPotionEffect(PotionEffectType.RESISTANCE);
        if (physResistance != null) base += (physResistance.getAmplifier() + 1) * 0.1;
        base += PhysicalArmorAttribute.calculateAbsorption(Attributes.PHYSICAL_ARMOR.calculate(entity));
        return super.calculateWithBase(entity, base);
    }
}

package me.udnek.rpgu.component.ability.property;

import me.udnek.itemscoreu.customcomponent.CustomComponentType;
import me.udnek.itemscoreu.customeffect.CustomEffect;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.AbilityComponent;
import me.udnek.rpgu.component.ability.property.function.AttributeFunction;
import me.udnek.rpgu.component.ability.property.function.Functions;
import me.udnek.rpgu.component.ability.property.function.Modifiers;
import me.udnek.rpgu.component.ability.property.function.PropertyFunction;
import me.udnek.rpgu.lore.ability.AbilityLorePart;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EffectsProperty implements AbilityProperty<Player, List<PotionEffect>> {

    public static EffectsProperty DEFAULT = new EffectsProperty(){
        @Override
        public void add(@NotNull PotionData data) {}
    };

    protected List<PotionData> effects = new ArrayList<>();

    public EffectsProperty(){}

    public EffectsProperty(@NotNull PotionData ...data){
        Arrays.stream(data).forEach(this::add);
    }

    public void add(@NotNull PotionData data){effects.add(data);}


    @Override
    public @NotNull List<PotionEffect> getBase() {
        List<PotionEffect> potionEffects = new ArrayList<>();
        for (PotionData data : effects) {
            PropertyFunction<Player, Integer> duration = data.duration;
            potionEffects.add(new PotionEffect(data.type, duration.getBase(), data.amplifier.getBase(), data.ambient, data.particles, data.icon));
        }
        return potionEffects;
    }

    @Override
    public @NotNull List<PotionEffect> get(@NotNull Player player) {
        List<PotionEffect> potionEffects = new ArrayList<>();
        for (PotionData data : effects) {
            PropertyFunction<Player, Integer> duration = data.duration;
            potionEffects.add(new PotionEffect(data.type, duration.apply(player), data.amplifier.apply(player), data.ambient, data.particles, data.icon));
        }
        return potionEffects;
    }

    @Override
    public void describe(@NotNull AbilityLorePart componentable) {
        Component text = Component.empty();
        for (int i = 0; i < effects.size(); i++) {
            PotionData data = effects.get(i);

            List<Component> args = new ArrayList<>();

            args.add(Component.translatable(data.type.translationKey()));

            String translation = "ability.rpgu.effects.";
            if (!data.amplifier.isZeroConstant()) {
                translation += "with_amplifier";
                args.add(data.amplifier.isConstant() ? Component.translatable("potion.potency." + data.amplifier.getBase()) : data.amplifier.describe());
            }
            else translation += "no_amplifier";

            if (!data.duration.isZeroConstant()) {
                translation += "_with_duration";
                args.add(data.duration.describeWithModifier(Modifiers.TICKS_TO_SECONDS()));
            }
            else translation += "_no_duration";

            text = text.append(Component.translatable(translation, args));

            if (i != effects.size()-1) text = text.append(Component.text(", "));
        }

        componentable.addAbilityStat(Component.translatable("ability.rpgu.effects", List.of(text)));
    }

    public void applyOn(@NotNull Player source, @NotNull LivingEntity target){
        for (PotionEffect effect : get(source)) {
            CustomEffect customEffect = CustomEffect.get(effect.getType());
            if (customEffect == null) target.addPotionEffect(effect);
            else customEffect.apply(target, effect);
        }
    }

    public void applyOn(@NotNull Player source, @NotNull Iterable<LivingEntity> targets){
        for (PotionEffect effect : get(source)) {
            CustomEffect customEffect = CustomEffect.get(effect.getType());
            for (LivingEntity target : targets) {
                if (customEffect == null) target.addPotionEffect(effect);
                else customEffect.apply(target, effect);
            }
        }
    }

    @Override
    public @NotNull CustomComponentType<AbilityComponent<?>, ?> getType() {
        return ComponentTypes.ABILITY_EFFECTS;
    }

    public record PotionData(
            @NotNull PotionEffectType type,
            @NotNull PropertyFunction<Player, Integer> duration,
            @NotNull PropertyFunction<Player, Integer> amplifier,
            boolean ambient,
            boolean particles,
            boolean icon
    ){
        public PotionData(@NotNull PotionEffectType type, @NotNull PropertyFunction<Player, Integer> duration, @NotNull PropertyFunction<Player, Integer> amplifier){
            this(type, duration, amplifier, false, true, true);
        }

        public PotionData(@NotNull PotionEffectType type, int duration, int amplifier){
            this(type, Functions.CEIL(new AttributeFunction(Attributes.ABILITY_DURATION, duration)), Functions.CONSTANT(amplifier));
        }
    }
}





















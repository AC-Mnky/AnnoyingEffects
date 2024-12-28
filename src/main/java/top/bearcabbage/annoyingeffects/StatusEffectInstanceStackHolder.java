package top.bearcabbage.annoyingeffects;

import net.minecraft.entity.effect.StatusEffectInstance;


public interface StatusEffectInstanceStackHolder {
    void pushStatusEffect(StatusEffectInstance effect);
    StatusEffectInstance popStatusEffect();
}

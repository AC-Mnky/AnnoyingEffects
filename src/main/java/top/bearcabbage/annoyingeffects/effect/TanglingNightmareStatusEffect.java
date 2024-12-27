package top.bearcabbage.annoyingeffects.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.random.Random;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;

public class TanglingNightmareStatusEffect extends StatusEffect {
    public TanglingNightmareStatusEffect() {
        super(
                StatusEffectCategory.HARMFUL, // 药水效果是有益的还是有害的
                0x98D982); // 显示的颜色
    }

    // 这个方法在每个 tick 都会调用，以检查是否应应用药水效果
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    // 这个方法在应用药水效果时会被调用，所以我们可以在这里实现自定义功能。
    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        Random random = entity.getRandom();
        for(RegistryEntry<StatusEffect> effect: AnnoyingEffects.STATUS_EFFECT_MAP.keySet()){
            if(entity.hasStatusEffect(effect)) continue;
            int packed = AnnoyingEffects.STATUS_EFFECT_MAP.get(effect);
            int duration = (packed >> 16) * 20;
            int interval = (packed & 0xffff) * 20;
            if(random.nextInt(interval)==0){
                entity.addStatusEffect(new StatusEffectInstance(effect, duration));
                break;
            }
        }

        return true;
    }
}

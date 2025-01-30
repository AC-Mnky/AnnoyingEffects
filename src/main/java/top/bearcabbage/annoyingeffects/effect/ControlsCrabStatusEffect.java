package top.bearcabbage.annoyingeffects.effect;

import net.minecraft.entity.LivingEntity;
import top.bearcabbage.annoyingeffects.effecttags.HighlyAdaptableStatusEffectTag;
import top.bearcabbage.annoyingeffects.effecttags.NightMareStatusEffectTag;

public class ControlsCrabStatusEffect extends AnnoyingStatusEffect implements HighlyAdaptableStatusEffectTag, NightMareStatusEffectTag {
    public ControlsCrabStatusEffect() {
        super(); // 显示的颜色
    }

    // 这个方法在每个 tick 都会调用，以检查是否应应用药水效果
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return false;
    }

    // 这个方法在应用药水效果时会被调用，所以我们可以在这里实现自定义功能。
    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        return true;
    }
}

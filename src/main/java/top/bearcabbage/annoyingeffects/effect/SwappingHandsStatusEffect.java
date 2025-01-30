package top.bearcabbage.annoyingeffects.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import top.bearcabbage.annoyingeffects.effecttags.SevereHandicapStatusEffectTag;

public class SwappingHandsStatusEffect extends AnnoyingStatusEffect implements SevereHandicapStatusEffectTag {
    public SwappingHandsStatusEffect() {
        super(); // 显示的颜色
    }

    // 这个方法在每个 tick 都会调用，以检查是否应应用药水效果
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % (1 + (19 >> amplifier)) == 0;
    }

    // 这个方法在应用药水效果时会被调用，所以我们可以在这里实现自定义功能。
    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        ItemStack main = entity.getMainHandStack();
        ItemStack off = entity.getOffHandStack();
        entity.setStackInHand(Hand.MAIN_HAND, off);
        entity.setStackInHand(Hand.OFF_HAND, main);
        return true;
    }
}

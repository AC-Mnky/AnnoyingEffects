package top.bearcabbage.annoyingeffects.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import top.bearcabbage.annoyingeffects.effecttags.SubtleStatusEffectTag;

public class VulnerableStatusEffect extends StatusEffect implements SubtleStatusEffectTag {
    public VulnerableStatusEffect() {
        super(
                StatusEffectCategory.HARMFUL, // 药水效果是有益的还是有害的
                0x98D982); // 显示的颜色
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

    @Override
    public void onEntityDamage(LivingEntity entity, int amplifier, DamageSource source, float amount) {
        float health = entity.getHealth();
//        if(!(entity instanceof PlayerEntity)){
//            entity.setHealth(health - amount * 0.5F * (1 + amplifier));
//            return;
//        }
        if(health <= 0.01F) return;
        entity.setHealth(Math.max(0.01F, health - amount * 0.5F * (1 + amplifier)));
    }
}

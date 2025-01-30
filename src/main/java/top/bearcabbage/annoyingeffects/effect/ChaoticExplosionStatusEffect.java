package top.bearcabbage.annoyingeffects.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import top.bearcabbage.annoyingeffects.effecttags.NightMareStatusEffectTag;
import top.bearcabbage.annoyingeffects.effecttags.SevereHandicapStatusEffectTag;

public class ChaoticExplosionStatusEffect extends AnnoyingStatusEffect implements SevereHandicapStatusEffectTag, NightMareStatusEffectTag {
    public ChaoticExplosionStatusEffect() {
        super(); // 显示的颜色
    }

    // 这个方法在每个 tick 都会调用，以检查是否应应用药水效果
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    // 这个方法在应用药水效果时会被调用，所以我们可以在这里实现自定义功能。
    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(entity.getRandom().nextInt(50) <= amplifier && !entity.getWorld().isClient) {
            entity.getWorld().createExplosion(entity, Explosion.createDamageSource(entity.getWorld(), entity), null, entity.getX(), entity.getBodyY(0.0625F), entity.getZ(), (float)Math.sqrt(1 + amplifier), true, World.ExplosionSourceType.MOB);
        }
        return true;
    }
}

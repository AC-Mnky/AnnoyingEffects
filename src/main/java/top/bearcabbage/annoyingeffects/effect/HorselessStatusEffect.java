package top.bearcabbage.annoyingeffects.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import top.bearcabbage.annoyingeffects.effecttags.NightMareStatusEffectTag;
import top.bearcabbage.annoyingeffects.effecttags.SubtleStatusEffectTag;

public class HorselessStatusEffect extends AnnoyingStatusEffect implements SubtleStatusEffectTag, NightMareStatusEffectTag {
    public HorselessStatusEffect() {
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
        if(entity.getWorld().isClient) return true;
        ServerWorld world = (ServerWorld) entity.getWorld();
        double distance = 4F * (1+amplifier);
        for(HorseEntity horse : world.getNonSpectatingEntities(HorseEntity.class,
                Box.of(entity.getEyePos(), 2 * distance, 2 * distance, 2 * distance))){
            if(horse.isAlive() && horse.getPos().distanceTo(entity.getEyePos()) < distance && !horse.equals(entity)){
                entity.getWorld().createExplosion(horse, Explosion.createDamageSource(horse.getWorld(), horse), null, horse.getX(), horse.getBodyY(0.0625F), horse.getZ(), 6.0F, true, World.ExplosionSourceType.MOB);
                horse.kill();
            }
        }
        return true;
    }
}

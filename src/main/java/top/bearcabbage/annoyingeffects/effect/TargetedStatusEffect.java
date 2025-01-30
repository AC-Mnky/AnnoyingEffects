package top.bearcabbage.annoyingeffects.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import top.bearcabbage.annoyingeffects.effecttags.NightMareStatusEffectTag;
import top.bearcabbage.annoyingeffects.effecttags.SubtleStatusEffectTag;

public class TargetedStatusEffect extends AnnoyingStatusEffect implements SubtleStatusEffectTag, NightMareStatusEffectTag {
    public static final double RADIUS = 16F;
    public static final double MAX_ACCELERATION = 0.6F;
    public static final double DAMP = 0.95F;
    public TargetedStatusEffect() {
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
//        if(!(entity instanceof PlayerEntity player)) return false;
        World world = entity.getWorld();
        Vec3d eyePos = entity.getEyePos();
        double radius = RADIUS * (1 + amplifier);
        for(ProjectileEntity projectile: world.getNonSpectatingEntities(ProjectileEntity.class,
                Box.of(eyePos, radius * 2, radius * 2, radius * 2))){
            Vec3d vec = eyePos.subtract(projectile.getPos());
            double distance = eyePos.distanceTo(projectile.getPos());
            if(distance >= radius) continue;
            Vec3d v = projectile.getVelocity();
            projectile.setVelocity(v.multiply(DAMP).add(vec.normalize().multiply(MAX_ACCELERATION * (1 - distance / radius))));

        }
        return true;
    }
}

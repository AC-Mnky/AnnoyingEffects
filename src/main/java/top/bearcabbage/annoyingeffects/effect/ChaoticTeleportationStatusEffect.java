package top.bearcabbage.annoyingeffects.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;

public class ChaoticTeleportationStatusEffect extends StatusEffect {
    public ChaoticTeleportationStatusEffect() {
        super(
                StatusEffectCategory.HARMFUL, // 药水效果是有益的还是有害的
                0x98D982); // 显示的颜色
    }

    // 这个方法在每个 tick 都会调用，以检查是否应应用药水效果
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 50 == 0;
    }

    // 这个方法在应用药水效果时会被调用，所以我们可以在这里实现自定义功能。
    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(entity.getWorld().isClient) return true;
        ServerWorld world = (ServerWorld) entity.getWorld();

        // This part of code is copied from chorus fruit.
        for(int i = 0; i < 16; ++i) {
            double d = entity.getX() + (entity.getRandom().nextDouble() - (double)0.5F) * (double)16.0F;
            double e = MathHelper.clamp(entity.getY() + (double)(entity.getRandom().nextInt(16) - 8), world.getBottomY(), (world.getBottomY() + (world).getLogicalHeight() - 1));
            double f = entity.getZ() + (entity.getRandom().nextDouble() - (double)0.5F) * (double)16.0F;
            if (entity.hasVehicle()) {
                entity.stopRiding();
            }

            Vec3d vec3d = entity.getPos();
            if (entity.teleport(d, e, f, true)) {
                world.emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(entity));
                SoundCategory soundCategory;
                SoundEvent soundEvent;
                if (entity instanceof FoxEntity) {
                    soundEvent = SoundEvents.ENTITY_FOX_TELEPORT;
                    soundCategory = SoundCategory.NEUTRAL;
                } else {
                    soundEvent = SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                    soundCategory = SoundCategory.PLAYERS;
                }

                world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), soundEvent, soundCategory);
                entity.onLanding();
                break;
            }
        }
        return true;
    }
}

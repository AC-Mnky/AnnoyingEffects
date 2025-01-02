package top.bearcabbage.annoyingeffects.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;
import top.bearcabbage.annoyingeffects.StatusEffectInstanceStackHolder;
import top.bearcabbage.annoyingeffects.TimerType;
import top.bearcabbage.annoyingeffects.WithTimer;

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
        if(!entity.isPlayer()) return false;
        long random_seed = entity.getRandom().nextLong();
        if(entity.getWorld().isClient) return true;
        ServerPlayerEntity player = (ServerPlayerEntity) entity;
        ServerWorld world = (ServerWorld) player.getWorld();
        StatusEffectInstanceStackHolder stackHolder = (StatusEffectInstanceStackHolder) player;
        WithTimer timer = (WithTimer) player;
        Random random = Random.create(random_seed);
        for(RegistryEntry<StatusEffect> effect: AnnoyingEffects.STATUS_EFFECT_MAP.keySet()){
            if(player.hasStatusEffect(effect)) continue;
            int packed = AnnoyingEffects.STATUS_EFFECT_MAP.get(effect);
            int duration = (packed >> 16) * 20;
            int interval = (packed & 0xffff) * 20;

            if(effect.equals(AnnoyingEffects.CHANNELING) && !world.getLevelProperties().isThundering()) continue;
            if(effect.equals(AnnoyingEffects.WATER_FILLING)){
                 if(!player.isTouchingWaterOrRain() || timer.getTick(TimerType.EXPOSURE_TO_WATER) < 1500) continue;
                 duration = timer.getTick(TimerType.EXPOSURE_TO_WATER) / 5;
            }
            if(effect.equals(AnnoyingEffects.CARROT_CURSE) && timer.getTick(TimerType.EAT_CARROT) > 0) continue;
            if(effect.equals(AnnoyingEffects.HORSELESS) && player.hasVehicle() && player.getVehicle() instanceof HorseEntity) interval /= 5;

            if(duration > 0 && random.nextInt(interval) <= amplifier){
//                player.addStatusEffect(new StatusEffectInstance(effect, duration));
                stackHolder.pushStatusEffect(new StatusEffectInstance(effect, duration));
                break;
            }
        }

        return true;
    }
}

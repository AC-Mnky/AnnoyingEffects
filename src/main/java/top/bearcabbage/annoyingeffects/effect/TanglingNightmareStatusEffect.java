package top.bearcabbage.annoyingeffects.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;
import top.bearcabbage.annoyingeffects.StatusEffectInstanceStackHolder;

import java.util.Map;

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
    public boolean applyUpdateEffect(LivingEntity entity, int this_amplifier) {
//        if(!entity.isPlayer()) return false;
        long random_seed = entity.getRandom().nextLong();
        if(entity.getWorld().isClient) return true;
//        ServerPlayerEntity player = (ServerPlayerEntity) entity;
        ServerWorld world = (ServerWorld) entity.getWorld();
        StatusEffectInstanceStackHolder stackHolder = (StatusEffectInstanceStackHolder) entity;
        Random random = Random.create(random_seed);
        for(RegistryEntry<StatusEffect> effect: AnnoyingEffects.STATUS_EFFECT_MAP.keySet()){
//            if(this instanceof TanglingDreamsStatusEffect && effect.value() instanceof NightMareStatusEffectTag) continue;
            if(entity.hasStatusEffect(effect)) continue;
            Map<String, Integer> durationAndInterval = AnnoyingEffects.STATUS_EFFECT_MAP.get(effect);

            int duration = (this instanceof TanglingDreamsStatusEffect ? durationAndInterval.get("weak_duration") : durationAndInterval.get("duration")) * 20;
            int interval = (this instanceof TanglingDreamsStatusEffect ? durationAndInterval.get("weak_interval") : durationAndInterval.get("interval")) * 20;
            int amplifier = (this instanceof TanglingDreamsStatusEffect ? durationAndInterval.get("weak_amplifier") : durationAndInterval.get("amplifier"));
            if(duration < 0) continue;

            if(effect.equals(AnnoyingEffects.CHANNELING) && !world.getLevelProperties().isThundering()) continue;
            if(effect.equals(AnnoyingEffects.HORSELESS) && entity.hasVehicle() && entity.getVehicle() instanceof HorseEntity) interval /= 5;

            if(entity instanceof PlayerEntity player) {
                if (!(this instanceof TanglingDreamsStatusEffect) && effect.equals(AnnoyingEffects.WATER_FILLING)) {
                    if (!entity.isTouchingWaterOrRain() || WaterFillingStatusEffect.WaterTicks.get(player) < 1500)
                        continue;
                    duration = WaterFillingStatusEffect.WaterTicks.get(player) / 5;
                }
                if (effect.equals(AnnoyingEffects.CARROT_CURSE) && CarrotCurseStatusEffect.CarrotTicks.get(player) > 0)
                    continue;
            }

            if(duration > 0 && random.nextInt(interval) < 1 << Math.min(30, this_amplifier)){
//                entity.addStatusEffect(new StatusEffectInstance(effect, duration));
                stackHolder.pushStatusEffect(new StatusEffectInstance(effect, duration, amplifier + Math.max(0, this_amplifier / 31), true, true));
                break;
            }
        }

        return true;
    }
}

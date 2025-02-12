package top.bearcabbage.annoyingeffects.effect;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;
import top.bearcabbage.annoyingeffects.StatusEffectInstanceStackHolder;
import top.bearcabbage.annoyingeffects.network.AnnoyingBarDisplayPayload;
import top.bearcabbage.annoyingeffects.network.AnnoyingBarStagePayload;
import top.bearcabbage.annoyingeffects.utils.NoSavePlayerData;

import java.util.Map;

import static top.bearcabbage.annoyingeffects.AnnoyingEffects.MOD_ID;

public class TanglingNightmareStatusEffect extends StatusEffect {
    public static final Identifier ANNOYINGBAR_DISPLAY_PACKET = Identifier.of(MOD_ID, "annoyingbar-display-payload");
    public static final Identifier ANNOYINGBAR_STAGE_PACKET = Identifier.of(MOD_ID, "annoyingbar-stage-payload");

    public TanglingNightmareStatusEffect() {
        super(
                StatusEffectCategory.HARMFUL, // 药水效果是有益的还是有害的
                0x000000); // 显示的颜色
    }

    public static final NoSavePlayerData<Double> AnnoyingBar = new NoSavePlayerData<>(0.0);

    // 这个方法在每个 tick 都会调用，以检查是否应应用药水效果
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    // 这个方法在应用药水效果时会被调用，所以我们可以在这里实现自定义功能。
    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int this_amplifier) {
        long random_seed = entity.getRandom().nextLong();
        if(entity.getWorld().isClient) return true;

        ServerWorld world = (ServerWorld) entity.getWorld();
        StatusEffectInstanceStackHolder stackHolder = (StatusEffectInstanceStackHolder) entity;
        Random random = Random.create(random_seed);

        boolean weak = this instanceof TanglingDreamsStatusEffect;

        if(!(entity instanceof PlayerEntity player))
        {
            for(RegistryEntry<StatusEffect> effect: AnnoyingEffects.STATUS_EFFECT_MAP.keySet()) {
                if (entity.hasStatusEffect(effect)) continue;
                Map<String, Integer> durationAndInterval = AnnoyingEffects.STATUS_EFFECT_MAP.get(effect);

                int duration = (weak ? durationAndInterval.get("weak_duration") : durationAndInterval.get("duration")) * 20;
                int interval = (weak ? durationAndInterval.get("weak_interval") : durationAndInterval.get("interval")) * 20;
                int amplifier = (weak ? durationAndInterval.get("weak_amplifier") : durationAndInterval.get("amplifier"));
                if (duration < 0) continue;

                if (effect.equals(AnnoyingEffects.CHANNELING) && !world.getLevelProperties().isThundering()) continue;
                if (effect.equals(AnnoyingEffects.HORSELESS) && entity.hasVehicle() && entity.getVehicle() instanceof HorseEntity)
                    interval /= 5;


                if (duration > 0 && random.nextInt(interval) < 1 << Math.min(30, this_amplifier)) {
                    stackHolder.pushStatusEffect(new StatusEffectInstance(effect, duration, amplifier + Math.max(0, this_amplifier / 31), true, true));
                    break;
                }
            }

        } else{

            int annoyingEffectCount = 0;
            for(StatusEffectInstance statusEffectInstance: entity.getStatusEffects()) {
                if(statusEffectInstance.getEffectType().value() instanceof AnnoyingStatusEffect)
                    ++annoyingEffectCount;
            }

            double increment_base = weak ? 1.0 / 300 : 1.0 / 100;
            double increment_multiplier = weak ? 0.5 : 0.8;
            double increment = increment_base * Math.pow(increment_multiplier, annoyingEffectCount) * (1 << Math.min(30, this_amplifier));

            AnnoyingBar.set(player, AnnoyingBar.get(player) + increment);
            if(player instanceof ServerPlayerEntity serverPlayer) {
                ServerPlayNetworking.send(serverPlayer, new AnnoyingBarStagePayload((int) (AnnoyingBar.get(player) * 6)));
            }
            if(AnnoyingBar.get(player) < 1.0) return true;
            AnnoyingBar.set(player, 0.0);

            double relative_possibility_sum = 0.0;
            for(RegistryEntry<StatusEffect> effect: AnnoyingEffects.STATUS_EFFECT_MAP.keySet()) {
                if (entity.hasStatusEffect(effect)) continue;
                Map<String, Integer> durationAndInterval = AnnoyingEffects.STATUS_EFFECT_MAP.get(effect);

                int duration = (weak ? durationAndInterval.get("weak_duration") : durationAndInterval.get("duration")) * 20;
                int interval = (weak ? durationAndInterval.get("weak_interval") : durationAndInterval.get("interval")) * 20;
//                int amplifier = (weak ? durationAndInterval.get("weak_amplifier") : durationAndInterval.get("amplifier"));
                if (duration < 0) continue;

                if (effect.equals(AnnoyingEffects.CHANNELING) && !world.getLevelProperties().isThundering()) continue;
                if (effect.equals(AnnoyingEffects.HORSELESS) && entity.hasVehicle() && entity.getVehicle() instanceof HorseEntity)
                    interval /= 5;
                if (!weak && effect.equals(AnnoyingEffects.WATER_FILLING)) {
                    if (!entity.isTouchingWaterOrRain() || WaterFillingStatusEffect.WaterTicks.get(player) < 1500)
                        continue;
                    duration = WaterFillingStatusEffect.WaterTicks.get(player) / 5;
                }
                if (effect.equals(AnnoyingEffects.CARROT_CURSE) && CarrotCurseStatusEffect.CarrotTicks.get(player) > 0)
                    continue;

                if(duration > 0) relative_possibility_sum += 1.0 / interval;
            }

            double dice = random.nextDouble() * relative_possibility_sum;

            for(RegistryEntry<StatusEffect> effect: AnnoyingEffects.STATUS_EFFECT_MAP.keySet()) {
                if (entity.hasStatusEffect(effect)) continue;
                Map<String, Integer> durationAndInterval = AnnoyingEffects.STATUS_EFFECT_MAP.get(effect);

                int duration = (weak ? durationAndInterval.get("weak_duration") : durationAndInterval.get("duration")) * 20;
                int interval = (weak ? durationAndInterval.get("weak_interval") : durationAndInterval.get("interval")) * 20;
                int amplifier = (weak ? durationAndInterval.get("weak_amplifier") : durationAndInterval.get("amplifier"));
                if (duration < 0) continue;

                if (effect.equals(AnnoyingEffects.CHANNELING) && !world.getLevelProperties().isThundering()) continue;
                if (effect.equals(AnnoyingEffects.HORSELESS) && entity.hasVehicle() && entity.getVehicle() instanceof HorseEntity)
                    interval /= 5;
                if (!weak && effect.equals(AnnoyingEffects.WATER_FILLING)) {
                    if (!entity.isTouchingWaterOrRain() || WaterFillingStatusEffect.WaterTicks.get(player) < 1500)
                        continue;
                    duration = WaterFillingStatusEffect.WaterTicks.get(player) / 5;
                }
                if (effect.equals(AnnoyingEffects.CARROT_CURSE) && CarrotCurseStatusEffect.CarrotTicks.get(player) > 0)
                    continue;

                dice -= 1.0 / interval;
                if(dice <= 0){
                    stackHolder.pushStatusEffect(new StatusEffectInstance(effect, duration, amplifier + Math.max(0, this_amplifier / 31), true, true));
                    break;
                }
            }
        }

        return true;
    }
}

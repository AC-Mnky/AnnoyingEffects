package top.bearcabbage.annoyingeffects.effect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import top.bearcabbage.annoyingeffects.effecttags.HandicapStatusEffectTag;
import top.bearcabbage.annoyingeffects.effecttags.NightMareStatusEffectTag;

import static net.minecraft.entity.effect.StatusEffects.FIRE_RESISTANCE;

public class DisableSleepingStatusEffect extends StatusEffect implements HandicapStatusEffectTag, NightMareStatusEffectTag {
    public static final int AVERAGE_SPAWN_INTERVAL = 300;
    public static final Double HEIGHT = 10.0;
    public DisableSleepingStatusEffect() {
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
//        if(entity.getRandom().nextInt(20)==0 && entity.isSleeping() && !entity.getWorld().isClient){
//            entity.wakeUp();
//        }
//        double random = Math.random();
        if (entity instanceof ServerPlayerEntity serverPlayer && entity.getRandom().nextInt(AVERAGE_SPAWN_INTERVAL)<=amplifier
                && LocationPredicate.Builder.create().canSeeSky(true).build().test(serverPlayer.getServerWorld(),serverPlayer.getX(),serverPlayer.getY(),serverPlayer.getZ())){
            PhantomEntity phantom = new PhantomEntity(EntityType.PHANTOM,serverPlayer.getServerWorld());
            phantom.setStatusEffect(new StatusEffectInstance(FIRE_RESISTANCE, 2400),null);
            phantom.setPos(serverPlayer.getX(),serverPlayer.getY()+HEIGHT,serverPlayer.getZ());
            serverPlayer.getServerWorld().spawnEntity(phantom);
        }
        return true;
    }
}

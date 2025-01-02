package top.bearcabbage.annoyingeffects.effect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class DisableSleepingStatusEffect extends StatusEffect {
    public static final Double PROBABILITY = 0.01;
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
        double random = Math.random();
        if (entity instanceof ServerPlayerEntity serverPlayer && random<PROBABILITY
                && LocationPredicate.Builder.create().canSeeSky(true).build().test(serverPlayer.getServerWorld(),serverPlayer.getX(),serverPlayer.getY(),serverPlayer.getZ())){
            PhantomEntity phantom = new PhantomEntity(EntityType.PHANTOM,serverPlayer.getServerWorld());
            phantom.setStatusEffect(new StatusEffectInstance(Registries.STATUS_EFFECT.getEntry(StatusEffects.FIRE_RESISTANCE.value()), StatusEffectInstance.INFINITE),null);
            phantom.setPos(serverPlayer.getX(),serverPlayer.getY()+HEIGHT,serverPlayer.getZ());
            serverPlayer.getServerWorld().spawnEntity(phantom);
        }
        return true;
    }
}

// 并没有做完，没有办法正常工作。

package top.bearcabbage.annoyingeffects.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class CreeperphobiaStatusEffect extends StatusEffect {
    public static final Double PROBABILITY = 0.01;

    public CreeperphobiaStatusEffect() {
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
        double random = Math.random();
        if (entity instanceof ServerPlayerEntity serverPlayer && random<PROBABILITY){
            serverPlayer.networkHandler.sendPacket(new PlaySoundS2CPacket(Registries.SOUND_EVENT.getEntry(SoundEvents.ENTITY_CREEPER_PRIMED), SoundCategory.HOSTILE, serverPlayer.getX()+random-0.5,serverPlayer.getY()+random-0.5,serverPlayer.getZ()+random-0.5, 1.0F, 1.0F, 0));
        }
        return true;
    }
}

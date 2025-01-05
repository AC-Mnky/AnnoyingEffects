package top.bearcabbage.annoyingeffects.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import top.bearcabbage.annoyingeffects.EffectTags;

import java.util.Set;

public class EndermanHostileStatusEffect extends StatusEffect {
    public static final Set<EffectTags> tags = Set.of(EffectTags.NONE);
    public EndermanHostileStatusEffect() {
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
        if(!(entity instanceof PlayerEntity player)) return false;
        if(player.getWorld().isClient) return true;
        ServerWorld world = (ServerWorld) player.getWorld();
        for(EndermanEntity enderman: world.getNonSpectatingEntities(EndermanEntity.class,
                Box.of(player.getEyePos(), 2048F, 2048F, 2048F))){
            if(!enderman.isAngry()){
                enderman.setAngryAt(player.getUuid());
                enderman.setTarget(player);
            }
        }
        return true;
    }
}

package top.bearcabbage.annoyingeffects.effect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import top.bearcabbage.annoyingeffects.EffectTags;

import java.util.EnumSet;
import java.util.Set;

public class ChannelingStatusEffect extends StatusEffect {
    public static final Set<EffectTags> tags = Set.of(EffectTags.NONE);
    public ChannelingStatusEffect() {
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
        long random_seed = entity.getRandom().nextLong();
        Random random = Random.create(random_seed);
        if(!entity.getWorld().isClient && random.nextInt(20) <= amplifier){
            ServerWorld world = (ServerWorld) entity.getWorld();
            if(!world.getLevelProperties().isThundering()) return false;
            double x = entity.getX() + random.nextBetween(-10, 10);
            double y = entity.getY();
            double z = entity.getZ() + random.nextBetween(-10, 10);
            for(int i=-10;i<10;++i) {
                if (LocationPredicate.Builder.create().canSeeSky(true).build().test(world, x, y+i, z)) {
                    LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
                    lightning.teleport(world, x, y+i, z, EnumSet.noneOf(PositionFlag.class), lightning.getYaw(), lightning.getPitch());
                    world.spawnEntity(lightning);
                    break;
                }
            }
        }
        return true;
    }
}

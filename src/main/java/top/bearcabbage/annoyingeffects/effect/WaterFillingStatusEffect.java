package top.bearcabbage.annoyingeffects.effect;

import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class WaterFillingStatusEffect extends StatusEffect {
    public WaterFillingStatusEffect() {
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
        if(entity.getWorld().isClient()) return true;
        PlayerEntity player = (PlayerEntity) entity;
        ServerWorld world = (ServerWorld) player.getWorld();
        BlockPos pos = player.getBlockPos();
        for(int dx=-2;dx<=2;++dx) for(int dy=-2;dy<=3;++dy) for(int dz=-2;dz<=2;++dz){
            BlockPos p = pos.add(dx, dy, dz);
            BlockState blockState = world.getBlockState(p);
            ((BucketItem)Items.WATER_BUCKET).placeFluid(null, world, p, null);
            if(blockState.getBlock() instanceof DoorBlock){
                world.breakBlock(p, true);
            }
//            if(blockState.isAir()){
//                world.setBlockState(p, Blocks.WATER.getDefaultState());
////                world.set
//            } else if(blockState.canBucketPlace(Fluids.WATER)) {
////                blockState.getFluidState();
//
//                ((BucketItem)Items.WATER_BUCKET).placeFluid(null, world, p, null);
//
//            }
        }
        return true;
    }
}

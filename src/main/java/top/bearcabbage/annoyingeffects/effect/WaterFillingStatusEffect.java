package top.bearcabbage.annoyingeffects.effect;

import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import top.bearcabbage.annoyingeffects.EffectTags;
import top.bearcabbage.annoyingeffects.utils.NoSavePlayerData;

import java.util.Set;

public class WaterFillingStatusEffect extends StatusEffect {
    public static final Set<EffectTags> tags = Set.of(EffectTags.NONE);
    public WaterFillingStatusEffect() {
        super(
                StatusEffectCategory.HARMFUL, // 药水效果是有益的还是有害的
                0x98D982); // 显示的颜色
    }

    public static final NoSavePlayerData<Integer> WaterTicks = new NoSavePlayerData<>(0);

    // 这个方法在每个 tick 都会调用，以检查是否应应用药水效果
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    // 这个方法在应用药水效果时会被调用，所以我们可以在这里实现自定义功能。
    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(!entity.isPlayer()) return false;
        if (entity.getWorld().getDimension().ultrawarm()) return false;
        if(entity.getWorld().isClient()) return true;
        PlayerEntity player = (PlayerEntity) entity;
        player.removeStatusEffect(StatusEffects.CONDUIT_POWER);
        ServerWorld world = (ServerWorld) player.getWorld();
        BlockPos pos = player.getBlockPos();
        for(int dx=-5;dx<=5;++dx) for(int dy=-5;dy<=5;++dy) for(int dz=-5;dz<=5;++dz){
            BlockPos p = pos.add(dx, dy, dz);
            double distance = player.getEyePos().distanceTo(p.toCenterPos());
            if(distance > 4F) continue;
            placeWater(world, p);

            BlockState blockState = world.getBlockState(p);
            Block block = blockState.getBlock();

            if(!blockState.isFullCube(world.getChunkAsView(p.getX() >> 4, p.getZ() >> 4), p) &&
                    !(blockState.getFluidState().getFluid().matchesType(Fluids.WATER)) &&
                    !world.getDimension().ultrawarm())
            {
                float hardness = block.getHardness();
                if(hardness <= 4F - distance || block instanceof DoorBlock || block instanceof BellBlock){
                    world.breakBlock(p, true);
                }
            }
            if(p.getY() >= 192 && world.getBlockState(p).getBlock() == Blocks.WATER){
                world.setBlockState(p, Blocks.FROSTED_ICE.getDefaultState());
            }

        }
        return true;
    }

    private void placeWater(World world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        boolean blockIsBreakable = blockState.canBucketPlace(Fluids.WATER);

        boolean mayPlace = blockState.isAir() || blockIsBreakable || block instanceof FluidFillable fluidFillable1 && fluidFillable1.canFillWithFluid(null, world, pos, blockState, Fluids.WATER);
        if (!mayPlace) return;

        if (world.getDimension().ultrawarm()) {
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);

            for(int l = 0; l < 8; ++l) {
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0F, 0.0F, 0.0F);
            }
            return;
        }

        if (block instanceof FluidFillable fluidFillable) {
            fluidFillable.tryFillWithFluid(world, pos, blockState, Fluids.WATER.getStill(false));
            return;
        }

        if (!world.isClient && blockIsBreakable && !blockState.isLiquid()) {
            world.breakBlock(pos, true);
        }

        world.setBlockState(pos, Fluids.WATER.getDefaultState().getBlockState(), 11);


    }
}

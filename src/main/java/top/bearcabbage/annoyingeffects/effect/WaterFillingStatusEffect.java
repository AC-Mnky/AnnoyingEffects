package top.bearcabbage.annoyingeffects.effect;

import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import top.bearcabbage.annoyingeffects.effecttags.AdaptableStatusEffectTag;
import top.bearcabbage.annoyingeffects.effecttags.DamageStatusEffectTag;
import top.bearcabbage.annoyingeffects.effecttags.NightMareStatusEffectTag;
import top.bearcabbage.annoyingeffects.effecttags.TerrachangingStatusEffectTag;
import top.bearcabbage.annoyingeffects.utils.NoSavePlayerData;

public class WaterFillingStatusEffect extends AnnoyingStatusEffect implements DamageStatusEffectTag, AdaptableStatusEffectTag, TerrachangingStatusEffectTag, NightMareStatusEffectTag {
    public final double REACH = 3F;
    public final double BREAKING_FORCE = 2.5F;
    public WaterFillingStatusEffect() {
        super(); // 显示的颜色
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
        FlowableFluid fluid;
        Block blockFluid;
        if(entity.getWorld().getDimension().ultrawarm()) {
            fluid = Fluids.FLOWING_LAVA;
            blockFluid = Blocks.LAVA;
        } else{
            fluid = Fluids.FLOWING_WATER;
            blockFluid = Blocks.WATER;
        }
        if(!(entity.getWorld() instanceof ServerWorld world)) return true;
        if(entity instanceof PlayerEntity player){
            player.removeStatusEffect(StatusEffects.CONDUIT_POWER);
        }

//        boolean createWaterSource = !entity.isSubmergedInWater();

        BlockPos pos = entity.getBlockPos();
        int range = (int)Math.floor(REACH+0.5);
        for(int dx=-range;dx<=range;++dx) for(int dy=-range;dy<=range;++dy) for(int dz=-range;dz<=range;++dz){
            BlockPos p = pos.add(dx, dy, dz);
            double distance = entity.getEyePos().distanceTo(p.toCenterPos());
            if(distance > REACH) continue;
//            placeWater(world, p);

            {
                BlockState blockState = world.getBlockState(p);
                Block block = blockState.getBlock();

                if(block instanceof FarmlandBlock || block instanceof DirtPathBlock){
                    FarmlandBlock.setToDirt(null, blockState, world, p);
                    blockState = world.getBlockState(p);
                    block = blockState.getBlock();
                }

                if(block instanceof LeavesBlock){
                    world.breakBlock(p, true);
                    blockState = world.getBlockState(p);
                    block = blockState.getBlock();
                }

                if(!blockState.isFullCube(world.getChunkAsView(p.getX() >> 4, p.getZ() >> 4), p) &&
                        !(blockState.getFluidState().getFluid().matchesType(fluid)) &&
                        !(block instanceof SoulSandBlock))
                {
                    float hardness = block.getHardness();
                    if(hardness <= BREAKING_FORCE * (REACH - distance)){
                        world.breakBlock(p, true);
                    }
                    blockState = world.getBlockState(p);
                    block = blockState.getBlock();
                }

                if(block instanceof AirBlock
//                        || block instanceof FluidBlock && blockState.getFluidState().getFluid().matchesType(fluid) && blockState.getFluidState().getLevel() > 1
                ){
                    BlockState flowing = blockFluid.getDefaultState().with(FluidBlock.LEVEL, 8);
                    world.setBlockState(p, flowing);
                    blockState = world.getBlockState(p);
                    block = blockState.getBlock();
                }

//                if(createWaterSource && distance < 1){
//                    world.setBlockState(p, Blocks.WATER.getDefaultState());
//                    blockState = world.getBlockState(p);
//                    block = blockState.getBlock();
//                }



//                if(p.getY() >= 192 && world.getBlockState(p).getBlock() == Blocks.WATER){
//                    world.setBlockState(p, Blocks.FROSTED_ICE.getDefaultState());
//                }

//                break;
            }



        }

//        if(!entity.isSubmergedInWater()){
//            entity.getEyePos().;
//            entity.setAir(entity.getAir() - 3);
//            entity.addVelocity(0F, -0.15F, 0F);
//            entity.setVelocity(entity.getVelocity().add(0F, -0.15F, 0F));
//        }

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

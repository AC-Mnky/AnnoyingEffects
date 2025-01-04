package top.bearcabbage.annoyingeffects.mixin;


import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.entity.EntityLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;
import top.bearcabbage.annoyingeffects.effect.SlippyStatusEffect;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityLike{

    @Shadow
    private Vec3d velocity;
    @Unique
    public Vec3d trueVelocity;

    @Inject(
            method = {"baseTick"},
            at = {@At("HEAD")}
    )
    private void injectBaseTickForTrueVelocity(CallbackInfo ci){
        this.trueVelocity = this.velocity;
    }

    @Inject(
            method = {"baseTick"},
            at = {@At("RETURN")}
    )
    private void injectBaseTickForHeaviness(CallbackInfo ci){
        if (!((EntityLike) this instanceof LivingEntity entity)) return;
        if (!entity.hasStatusEffect(AnnoyingEffects.HEAVINESS)) return;
        entity.setVelocity(entity.getVelocity().add(0F, -0.15F, 0F));
    }

//    @Unique
//    private void updateFallDistance(){
//        if(!((EntityLike)this instanceof LivingEntity entity)) return;
//        Vec3d velocity = this.trueVelocity;
//
//        float prev = entity.fallDistance;
//
//        if(velocity.y >= 0.2) {
//            entity.fallDistance = 0;
//            return;
//        }
//
//        if(velocity.y >= -0.1) return;
//
//        entity.fallDistance = 6F * (float)Math.pow(velocity.y, 2F);
//
//        if(entity instanceof ServerPlayerEntity player && (entity.fallDistance > 0.5 || prev > 0.5)){
//            player.sendMessage(Text.of(velocity + " " + prev + " " + entity.fallDistance + (entity.isTouchingWater() ? " water":"")), false);
//        }
//    }

    @Inject(
        method = {"getVelocityAffectingPos"},
        at = {@At("HEAD")}
    )
    private void injectGetVelocityAffectingPosForSlippy(CallbackInfoReturnable<BlockPos> ci){
        /// ignore the warning
        if((EntityLike)this instanceof LivingEntity entity && entity.hasStatusEffect(AnnoyingEffects.SLIPPY)){
            SlippyStatusEffect.globalSlippyFlag = true;
        }
    }

    @Inject(
            method = {"isOnGround"},
            at = {@At("HEAD")}
    )
    private void injectIsOnGroundForSlippy(CallbackInfoReturnable<BlockPos> ci){
        /// ignore the warning
        if((EntityLike)this instanceof LivingEntity entity && entity.hasStatusEffect(AnnoyingEffects.SLIPPY)){
            SlippyStatusEffect.globalSlippyFlag = false;
        }
    }

    @Shadow
    protected Object2DoubleMap<TagKey<Fluid>> fluidHeight;
    @Shadow
    protected boolean touchingWater;

    @Inject(
            method = {"limitFallDistance"},
            at = {@At("HEAD")},
            cancellable = true
    )
    private void injectLimitFallDistanceForHeaviness(CallbackInfo ci) {
        if (!((EntityLike) this instanceof LivingEntity entity)) return;
        if (!entity.hasStatusEffect(AnnoyingEffects.HEAVINESS)) return;
        double vy = this.trueVelocity.y;
        float maxFallDistance = 2F * (float)Math.pow(vy, 2F);
        if(entity instanceof ServerPlayerEntity player && (maxFallDistance > 0.5 || entity.fallDistance > 0.5)){
            player.sendMessage(Text.of(maxFallDistance + " " + entity.fallDistance + (entity.isTouchingWater() ? " water":"")), false);
        }

        entity.fallDistance = Math.min(maxFallDistance, entity.fallDistance);
        ci.cancel();
    }


    @Inject(
            method = {"onLanding"},
            at = {@At("HEAD")},
            cancellable = true
    )
    private void injectOnLandingForHeaviness(CallbackInfo ci){
        if(!((EntityLike)this instanceof LivingEntity entity)) return;
        if(!entity.hasStatusEffect(AnnoyingEffects.HEAVINESS)) return;
        entity.limitFallDistance();
//            player.sendMessage(Text.of(String.valueOf(player.fallDistance)), false);
//            player.sendMessage(Text.of(String.valueOf(player.isOnGround())), false);
//        if(player.getVelocity().y >= 0) return;
//        if(player.updateMovementInFluid(FluidTags.WATER, 0.014)){
//        entity.fallDistance *= 0.5F;
//            player.sendMessage(Text.of(String.valueOf(player.fallDistance)), false);
//        updateFallDistance();
        ci.cancel();
    }

    @Inject(
            method = {"updateWaterState"},
            at = {@At("HEAD")},
            cancellable = true
    )
    private void injectUpdateWaterStateForHeaviness(CallbackInfoReturnable<Boolean> ci){
        if(!((EntityLike)this instanceof LivingEntity entity)) return;
        if(!entity.hasStatusEffect(AnnoyingEffects.HEAVINESS)) return;

        this.fluidHeight.clear();


        if (entity.updateMovementInFluid(FluidTags.WATER, 0.007)) {
            entity.onLanding();
            this.touchingWater = true;
            entity.extinguish();

        } else {
            this.touchingWater = false;
        }


        double d = entity.getWorld().getDimension().ultrawarm() ? 0.007 : 0.0023333333333333335;
        boolean bl = entity.updateMovementInFluid(FluidTags.LAVA, d);
        ci.setReturnValue(entity.isTouchingWater() || bl);
    }
//    @Inject(
//            method = {"checkWaterState"},
//            at = {@At("HEAD")},
//            cancellable = true
//    )
//    private void injectCheckWaterStateForHeaviness(CallbackInfo ci){
//        if(!((EntityLike)this instanceof LivingEntity entity)) return;
//        if(!entity.hasStatusEffect(AnnoyingEffects.HEAVINESS)) return;
//
//        if (entity.updateMovementInFluid(FluidTags.WATER, 0.014)) {
////            this.onLanding();
//            this.touchingWater = true;
//            entity.extinguish();
//        } else {
//            this.touchingWater = false;
//        }
//
//        ci.cancel();
//    }


//    @Inject(
//            method = {"fall"},
//            at = {@At("HEAD")}
//    )
//    private void injectFallForHeaviness(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition, CallbackInfo ci){
//        if(!((EntityLike)this instanceof LivingEntity entity)) return;
//        if(!entity.hasStatusEffect(AnnoyingEffects.HEAVINESS)) return;
////        updateFallDistance();
//    }

    @Inject(
            method = {"fall"},
            at = {@At("RETURN")}
    )
    private void injectFallAtReturnForHeaviness(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition, CallbackInfo ci){
        if(!((EntityLike)this instanceof LivingEntity entity)) return;
        if(!entity.hasStatusEffect(AnnoyingEffects.HEAVINESS)) return;
        if(onGround) entity.fallDistance = 0;
    }
}

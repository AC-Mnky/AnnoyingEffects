package top.bearcabbage.annoyingeffects.mixin;


import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.entity.EntityLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;
import top.bearcabbage.annoyingeffects.effect.SlippyStatusEffect;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityLike{

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
}

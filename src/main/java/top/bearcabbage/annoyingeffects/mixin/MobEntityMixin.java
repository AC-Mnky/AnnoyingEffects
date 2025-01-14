package top.bearcabbage.annoyingeffects.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {

    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = {"tickNewAi"},
            at = {@At("RETURN")}
    )
    public void injectTickNewAiForControls(CallbackInfo ci) {
        if(this.hasStatusEffect(AnnoyingEffects.CONTROLS_CRAB)) this.forwardSpeed = 0F;
        if(this.hasStatusEffect(AnnoyingEffects.CONTROLS_MIRROR)) this.sidewaysSpeed *= -1F;
        if(this.hasStatusEffect(AnnoyingEffects.CONTROLS_ALWAYS_JUMP)) this.setJumping(true);
        if(this.hasStatusEffect(AnnoyingEffects.REALLY_COLD)) this.setJumping(false);
    }
}

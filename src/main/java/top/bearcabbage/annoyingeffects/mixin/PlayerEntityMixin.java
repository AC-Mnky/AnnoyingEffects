package top.bearcabbage.annoyingeffects.mixin;

import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    public PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = {"canChangeIntoPose"},
            at = {@At("HEAD")},
            cancellable = true
    )
    public void canChangeIntoPose(EntityPose pose, CallbackInfoReturnable<Boolean> ci) {
        if (this.hasStatusEffect(AnnoyingEffects.CRAWLER)) {
            ci.setReturnValue(false);
        }
    }


}

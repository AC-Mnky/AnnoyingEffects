package top.bearcabbage.annoyingeffects.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;
import top.bearcabbage.annoyingeffects.effect.SchizophreniaStatusEffect;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }


    @Inject(
            method = {"tickNewAi"},
            at = {@At("RETURN")}
    )
    public void injectTickNewAiForControls(CallbackInfo ci) {
        if(this.hasStatusEffect(AnnoyingEffects.CONTROLS_CRAB)) this.forwardSpeed = 0F;
        if(this.hasStatusEffect(AnnoyingEffects.CONTROLS_MIRROR)) this.sidewaysSpeed *= -1F;
        if(this.hasStatusEffect(AnnoyingEffects.CONTROLS_ALWAYS_JUMP)) this.jumping = true;
        if(this.hasStatusEffect(AnnoyingEffects.REALLY_COLD)) this.jumping = false;
    }
}

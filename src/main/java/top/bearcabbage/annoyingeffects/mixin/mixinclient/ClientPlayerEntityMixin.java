package top.bearcabbage.annoyingeffects.mixin.mixinclient;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;
import top.bearcabbage.annoyingeffects.utils.ReturnInt;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity implements ReturnInt {

    @Unique
    public int loadTick = 0;


    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = {"tick"}, at = {@At("HEAD")})
    private void addLoadTick(CallbackInfo ci){
        ++loadTick;
    }


    @Unique @Override
    public int Return(String name) {
        if(name.equals("loadTick")) return loadTick;
        return 0;
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

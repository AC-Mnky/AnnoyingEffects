package top.bearcabbage.annoyingeffects.mixin.mixinclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    @Shadow
    private MinecraftClient client;


    @Inject(
            method = {"isBreakingBlock"},
            at = {@At("HEAD")},
            cancellable = true
    )
    private void RedirectIsBreakingBlock(CallbackInfoReturnable<Boolean> ci){
        if(client.player!=null &&
                client.player.hasStatusEffect(AnnoyingEffects.CONTROLS_ALWAYS_DIG)) ci.setReturnValue(Boolean.FALSE);
    }
}

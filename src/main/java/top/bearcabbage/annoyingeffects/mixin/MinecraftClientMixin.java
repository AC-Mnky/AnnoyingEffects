package top.bearcabbage.annoyingeffects.mixin;


import net.minecraft.client.*;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.thread.ReentrantThreadExecutor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin extends ReentrantThreadExecutor<Runnable> implements WindowEventHandler {

    public MinecraftClientMixin(String string) {
        super(string);
    }

    @Shadow @Nullable
    public ClientWorld world;
    @Shadow @Nullable
    public ClientPlayerEntity player;

    @Shadow @Nullable
    public ClientPlayNetworkHandler getNetworkHandler() {return null;}
    @Shadow
    private void handleBlockBreaking(boolean breaking) {}




    @Inject(
            method = {"handleInputEvents"},
            at = {@At("RETURN")}
    )
    private void injectHandleInputEventsForBlockBreaking(CallbackInfo ci) {
        if (this.player!=null && this.player.hasStatusEffect(AnnoyingEffects.CONTROLS_ALWAYS_DIG))
        {
            this.handleBlockBreaking(true);
        }



    }


}

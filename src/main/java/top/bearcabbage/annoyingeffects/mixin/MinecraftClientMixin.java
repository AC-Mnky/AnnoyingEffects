package top.bearcabbage.annoyingeffects.mixin;


import net.minecraft.client.*;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.thread.ReentrantThreadExecutor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin extends ReentrantThreadExecutor<Runnable> implements WindowEventHandler {

    public MinecraftClientMixin(String string) {
        super(string);
    }

    @Unique
    private boolean diggingFlag = false;

    @Shadow @Nullable
    public ClientWorld world;
    @Shadow @Nullable
    public ClientPlayerEntity player;
    @Shadow
    public int attackCooldown;

    @Shadow
    private void handleBlockBreaking(boolean breaking) {}
    @Shadow
    private void doItemUse() {}


    @Inject(
            method = {"handleBlockBreaking"},
            at = {@At("HEAD")},
            cancellable = true
    )
    private void injectHandleBlockBreakingForRecord(CallbackInfo ci) {
        if (this.player!=null && this.player.hasStatusEffect(AnnoyingEffects.CONTROLS_ALWAYS_DIG) && !diggingFlag)
        {
            ci.cancel();
        }
    }


    @Inject(
            method = {"handleInputEvents"},
            at = {@At("RETURN")}
    )
    private void injectHandleInputEventsForBlockBreakingAndItemUsing(CallbackInfo ci) {
        if (this.player!=null && this.player.hasStatusEffect(AnnoyingEffects.CONTROLS_ALWAYS_DIG)) {
            this.attackCooldown = 0;
            this.diggingFlag = true;
            this.handleBlockBreaking(true);
            this.diggingFlag = false;
//            this.interactionManager.
        }
        if (this.player!=null && this.player.hasStatusEffect(AnnoyingEffects.CONTROLS_CHAOTIC_USE)){
            if(this.player.getRandom().nextInt(50) == 0){
                this.doItemUse();
            }
        }

    }


}

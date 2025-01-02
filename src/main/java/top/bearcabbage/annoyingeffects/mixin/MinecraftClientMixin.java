package top.bearcabbage.annoyingeffects.mixin;


import net.minecraft.client.*;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.Text;
import net.minecraft.util.thread.ReentrantThreadExecutor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;
import top.bearcabbage.annoyingeffects.TimerType;
import top.bearcabbage.annoyingeffects.WithTimer;

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
            method = {"tick"},
            at = {@At("RETURN")}
    )
    private void injectTickForThings(CallbackInfo ci) {
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
        if(this.player!=null && this.player.hasStatusEffect(AnnoyingEffects.TANGLING_NIGHTMARE) &&
                ((WithTimer)this.player).getTick(TimerType.EXPOSURE_TO_WATER) > 1000 &&
                !this.player.hasStatusEffect(AnnoyingEffects.WATER_FILLING) &&
                this.player.isTouchingWaterOrRain()){
            this.player.sendMessage(Text.translatable("messages.annoyingeffects.water_filling.warning"), true);
        }
        if(this.player!=null && this.player.hasStatusEffect(AnnoyingEffects.TANGLING_NIGHTMARE) &&
                ((WithTimer)this.player).getTick(TimerType.EXPOSURE_TO_WATER) > 1500){
            if(this.player.hasStatusEffect(AnnoyingEffects.WATER_FILLING)) {
                this.player.sendMessage(Text.translatable("messages.annoyingeffects.water_filling.water"), true);
            } else{
                this.player.sendMessage(Text.translatable("messages.annoyingeffects.water_filling.danger"), true);
            }

        }

    }


}

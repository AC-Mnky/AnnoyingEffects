package top.bearcabbage.annoyingeffects.mixin.mixinclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;
import top.bearcabbage.annoyingeffects.effect.OppressedStatusEffect;
import top.bearcabbage.annoyingeffects.effect.SpinStatusEffect;

import java.util.Objects;

@Mixin(Mouse.class)
public class MouseMixin {

    @Inject(
            method = {"tick"},
            at = {@At("RETURN")}
    )
    private void injectMouseTickForSpinAndOppressed(CallbackInfo ci){
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player == null || !player.isAlive()) return;


        if(player.hasStatusEffect(AnnoyingEffects.SPIN)){
            double deltaYaw = 0F;
            int amplifier = Objects.requireNonNull(player.getStatusEffect(AnnoyingEffects.SPIN)).getAmplifier();
//            float yaw = player.getYaw();
            deltaYaw += SpinStatusEffect.ANGLE_PER_TICK * 20 * (1 + amplifier)/ MinecraftClient.getInstance().getCurrentFps();
//            player.setYaw(yaw);
            player.changeLookDirection(deltaYaw / 0.15F, 0F);
        }
        if(player.hasStatusEffect(AnnoyingEffects.OPPRESSED)){
            int amplifier = Objects.requireNonNull(player.getStatusEffect(AnnoyingEffects.OPPRESSED)).getAmplifier();
            float pitch = player.getPitch();
            float min_pitch = Math.min(OppressedStatusEffect.PITCH * (1+amplifier), 90F);
            double deltaPitch = Math.max(pitch, min_pitch) - pitch;
            player.changeLookDirection(0F, deltaPitch / 0.15F);
        }


    }
}

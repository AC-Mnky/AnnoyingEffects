package top.bearcabbage.annoyingeffects.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;
import top.bearcabbage.annoyingeffects.effect.SchizophreniaStatusEffect;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @ModifyVariable(method = "render", at = @At(value = "STORE"), ordinal = 0)
    private double modifyRenderPosX(double posX) {
        if(MinecraftClient.getInstance().player.hasStatusEffect(AnnoyingEffects.SCHIZOPHRENIA)) return posX + SchizophreniaStatusEffect.OFFSET_X;
        return posX;
    }
}

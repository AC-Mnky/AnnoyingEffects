package top.bearcabbage.annoyingeffects.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;

@Mixin(ChatScreen.class)
public class ChatScreenMixin extends Screen {
    protected ChatScreenMixin(Text title) {
        super(title);
    }

    @Inject(
            method = {"sendMessage"},
            at = {@At("HEAD")},
            cancellable = true
    )
    private void injectSendMessageForVoiceless(CallbackInfo ci){
        if(MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().player.hasStatusEffect(AnnoyingEffects.VOICELESS)){
            ci.cancel();
            MinecraftClient.getInstance().player.sendMessage(Text.translatable("messages.annoyingeffects.voiceless"), true);
        }
    }
}

package top.bearcabbage.annoyingeffects.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;

import java.util.Objects;

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
    private void injectSendMessageForVoiceless(String chatText, boolean addToHistory, CallbackInfo ci){
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player != null && player.hasStatusEffect(AnnoyingEffects.VOICELESS)){
            if(Objects.equals(chatText, "/effect give @s minecraft:instant_health 42 42 true   ")) return;
            ci.cancel();
            player.sendMessage(Text.translatable("messages.annoyingeffects.voiceless"), true);
        }
    }
}

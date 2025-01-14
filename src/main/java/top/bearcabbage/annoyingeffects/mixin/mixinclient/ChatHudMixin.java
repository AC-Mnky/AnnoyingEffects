package top.bearcabbage.annoyingeffects.mixin.mixinclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;

@Mixin(ChatHud.class)
public class ChatHudMixin {

    @Inject(
            method = {"addMessage"},
            at = {@At("HEAD")}
    )
    private void injectAddMessageForRepeater(Text message, CallbackInfo ci)
    {
        MinecraftClient client = MinecraftClient.getInstance();
        if(client.player == null || !client.player.isAlive() || !client.player.hasStatusEffect(AnnoyingEffects.REPEATER)) return;
//        client.player.networkHandler.sendChatMessage("???");
        String text = "\"" + message.getString() + "\"";
        if(text.contains("\"\"\"")) return;
//        client.inGameHud.getChatHud().addToMessageHistory(text);
        client.player.networkHandler.sendChatMessage(text);
    }
}

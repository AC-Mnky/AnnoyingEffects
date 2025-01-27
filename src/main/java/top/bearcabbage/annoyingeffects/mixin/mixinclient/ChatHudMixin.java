package top.bearcabbage.annoyingeffects.mixin.mixinclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import net.minecraft.util.StringHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;
import top.bearcabbage.annoyingeffects.utils.ReturnInt;

import java.util.Objects;

@Mixin(ChatHud.class)
public class ChatHudMixin {
    @Unique
    private static final int RECURSION = 1;

    // 这是好的inject，或许可以作为代码参考
    @Inject(
            method = {"addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V"},
            at = {@At("HEAD")}
    )
    private void injectAddMessageForRepeater(Text message, @Nullable MessageSignatureData signatureData, @Nullable MessageIndicator indicator, CallbackInfo ci)
    {
        MinecraftClient client = MinecraftClient.getInstance();
        if(client.player == null ||((ReturnInt)(client.player)).Return("loadTick") < 100 || !client.player.isAlive()|| !client.player.hasStatusEffect(AnnoyingEffects.REPEATER)) return;
        int amplifier = Objects.requireNonNull(client.player.getStatusEffect(AnnoyingEffects.REPEATER)).getAmplifier();
        String rawText = message.getString();
        String text;
        if (indicator != null && (indicator.equals(MessageIndicator.system()) || indicator.equals(MessageIndicator.singlePlayer()))){
            text = message.getString();
        } else {
            String[] split = rawText.split("> ", 2);
            if(split[0].endsWith(client.player.getName().getString())) return;
            if(split[1].endsWith(" ".repeat(RECURSION + amplifier))) return;
            text = split[1];
        }
//        client.inGameHud.getChatHud().addToMessageHistory(text);

        client.player.networkHandler.sendChatMessage(StringHelper.truncateChat(text) + " ");
    }
}

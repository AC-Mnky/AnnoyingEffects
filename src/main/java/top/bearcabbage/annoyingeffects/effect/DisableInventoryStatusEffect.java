package top.bearcabbage.annoyingeffects.effect;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import top.bearcabbage.annoyingeffects.effecttags.SevereHandicapStatusEffectTag;

public class DisableInventoryStatusEffect extends AnnoyingStatusEffect implements SevereHandicapStatusEffectTag {
    public DisableInventoryStatusEffect() {
        super(); // 显示的颜色
    }

    // 这个方法在每个 tick 都会调用，以检查是否应应用药水效果
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    // 这个方法在应用药水效果时会被调用，所以我们可以在这里实现自定义功能。
    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(!entity.isPlayer()) return false;
        if(!entity.getWorld().isClient()) return true;
        PlayerEntity player = (PlayerEntity) entity;
        Screen screen =  MinecraftClient.getInstance().currentScreen;
        if(screen instanceof HandledScreen<?>){
            player.sendMessage(Text.translatable("messages.annoyingeffects.disable_inventory"), true);
            screen.close();
        }

        return true;
    }
}

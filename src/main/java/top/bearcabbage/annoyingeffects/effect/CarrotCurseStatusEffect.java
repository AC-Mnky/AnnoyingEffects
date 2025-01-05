package top.bearcabbage.annoyingeffects.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import top.bearcabbage.annoyingeffects.EffectTags;
import top.bearcabbage.annoyingeffects.utils.NoSavePlayerData;

import java.util.Set;

public class CarrotCurseStatusEffect extends StatusEffect {
    public static final Set<EffectTags> tags = Set.of(EffectTags.NONE);
    public CarrotCurseStatusEffect() {
        super(
                StatusEffectCategory.HARMFUL, // 药水效果是有益的还是有害的
                0x98D982); // 显示的颜色
    }

    public static final NoSavePlayerData<Integer> CarrotTicks = new NoSavePlayerData<>(10000);

    // 这个方法在每个 tick 都会调用，以检查是否应应用药水效果
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    // 这个方法在应用药水效果时会被调用，所以我们可以在这里实现自定义功能。
    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(!entity.isPlayer()) return false;
        if(entity.getWorld().isClient()) return true;
        PlayerEntity player  = (PlayerEntity) entity;
        if(CarrotTicks.get(player) > 0) return false;
        player.giveItemStack(new ItemStack(Items.POISONOUS_POTATO, 64));
        return true;
    }
}

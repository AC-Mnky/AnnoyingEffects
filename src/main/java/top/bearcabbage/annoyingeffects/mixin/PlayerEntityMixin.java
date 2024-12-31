package top.bearcabbage.annoyingeffects.mixin;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;
import top.bearcabbage.annoyingeffects.TimerType;
import top.bearcabbage.annoyingeffects.WithTimer;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements WithTimer {

    public PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }


    @Unique
    public int eat_carrot_ticks = 10000;
    @Unique
    public int exposure_to_water_ticks = 0;

    @Inject(
            method = {"eatFood"},
            at = {@At("HEAD")}
    )
    public void injectEatFoodForCarrotDetection(World world, ItemStack stack, FoodComponent foodComponent, CallbackInfoReturnable<ItemStack> ci) {
        if(eat_carrot_ticks < 0) eat_carrot_ticks = 0;
        if(stack.getItem() == Items.CARROT)
        {
            eat_carrot_ticks += 10000;
        } else{
            eat_carrot_ticks -= 1000;
        }
    }

    @Inject(
            method = {"tick"},
            at = {@At("HEAD")}
    )
    public void injectTick(CallbackInfo ci) {
        eat_carrot_ticks -= 1;
        if(eat_carrot_ticks < 0) eat_carrot_ticks = 0;
        exposure_to_water_ticks += this.isTouchingWaterOrRain() ? 1 : -1;
        if(exposure_to_water_ticks < 0) exposure_to_water_ticks = 0;
    }

    @Inject(
            method = {"canChangeIntoPose"},
            at = {@At("HEAD")},
            cancellable = true
    )
    public void injectCanChangeIntoPoseForCrawler(EntityPose pose, CallbackInfoReturnable<Boolean> ci) {
        if (this.hasStatusEffect(AnnoyingEffects.CRAWLER)) {
            ci.setReturnValue(false);
        }
    }

    @Unique @Override
    public int getTick(TimerType type){
        switch (type){
            case EAT_CARROT -> {
                return eat_carrot_ticks;
            }
            case EXPOSURE_TO_WATER -> {
                return exposure_to_water_ticks;
            }
        }
        return -1;
    }
}

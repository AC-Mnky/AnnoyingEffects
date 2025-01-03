package top.bearcabbage.annoyingeffects.mixin;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;
import top.bearcabbage.annoyingeffects.effect.CarrotCurseStatusEffect;
import top.bearcabbage.annoyingeffects.effect.WaterFillingStatusEffect;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity{

    public PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = {"eatFood"},
            at = {@At("HEAD")}
    )
    public void injectEatFoodForCarrotDetection(World world, ItemStack stack, FoodComponent foodComponent, CallbackInfoReturnable<ItemStack> ci) {
        if(this.getWorld().isClient()) return;
        int carrot_ticks = CarrotCurseStatusEffect.CarrotTicks.get(this.uuid);
        if(stack.getItem() == Items.CARROT)
        {
            carrot_ticks += 10000;
        } else{
            carrot_ticks -= 1000;
        }
        CarrotCurseStatusEffect.CarrotTicks.set(this.uuid, Math.max(0, carrot_ticks));
    }

    @Inject(
            method = {"tick"},
            at = {@At("HEAD")}
    )
    public void injectTick(CallbackInfo ci) {
        if(this.getWorld().isClient()) return;
        /// ignore warning
        assert ((LivingEntity)this) instanceof ServerPlayerEntity;
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) ((LivingEntity)this);
        int carrot_ticks = CarrotCurseStatusEffect.CarrotTicks.get(this.uuid);
        carrot_ticks -= 1;
        CarrotCurseStatusEffect.CarrotTicks.set(this.uuid, Math.max(0, carrot_ticks));
        int water_ticks = WaterFillingStatusEffect.WaterTicks.get(this.uuid);
        water_ticks += this.isTouchingWaterOrRain() ? 1 : -1;
        WaterFillingStatusEffect.WaterTicks.set(this.uuid, Math.max(0, water_ticks));

        if(this.hasStatusEffect(AnnoyingEffects.TANGLING_NIGHTMARE) &&
                WaterFillingStatusEffect.WaterTicks.get(this.uuid) > 1000 &&
                !this.hasStatusEffect(AnnoyingEffects.WATER_FILLING) &&
                this.isTouchingWaterOrRain()){
            serverPlayerEntity.sendMessage(Text.translatable("messages.annoyingeffects.water_filling.warning"), true);
        }
        if(this.hasStatusEffect(AnnoyingEffects.TANGLING_NIGHTMARE) &&
                WaterFillingStatusEffect.WaterTicks.get(this.uuid) > 1500){
            if(this.hasStatusEffect(AnnoyingEffects.WATER_FILLING)) {
                serverPlayerEntity.sendMessage(Text.translatable("messages.annoyingeffects.water_filling.water"), true);
            } else{
                if(this.isTouchingWaterOrRain()){
                    serverPlayerEntity.sendMessage(Text.translatable("messages.annoyingeffects.water_filling.danger"), true);
                }
                else{
                    serverPlayerEntity.sendMessage(Text.translatable("messages.annoyingeffects.water_filling.safe"), true);
                }
            }

        }
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
}

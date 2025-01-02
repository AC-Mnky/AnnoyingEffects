package top.bearcabbage.annoyingeffects.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import top.bearcabbage.annoyingeffects.effect.SlippyStatusEffect;

@Mixin(Block.class)
abstract public class BlockMixin extends AbstractBlock {
    public BlockMixin(Settings settings) {
        super(settings);
    }

//    @Inject(
//            method = {"getSlipperiness"},
//            at = {@At("HEAD")},
//            cancellable = true
//    )
//    private void injectGetSlipperinessForSlippy(CallbackInfoReturnable<Float> ci){
//        /// ignore the warning
//        if(SlippyStatusEffect.globalSlippyFlag){
//            ci.setReturnValue(1F);
//            ci.cancel();
//        }
//    }

    /**
     * {@code @Author} Mnky
     * {@code @reason} Slippy.
     */
    @Overwrite
    public float getSlipperiness() {
//        if(MinecraftClient.getInstance().player != null) MinecraftClient.getInstance().player.sendMessage(Text.of(String.valueOf(this.slipperiness)), true);
        if(SlippyStatusEffect.globalSlippyFlag)return Math.max(1.09F, this.slipperiness);
        return this.slipperiness;
    }

}

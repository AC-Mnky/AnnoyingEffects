package top.bearcabbage.annoyingeffects.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Shadow
    public Input input;
    @Shadow
    public float renderYaw;
    @Shadow
    public float renderPitch;
    @Shadow
    public float lastRenderYaw;
    @Shadow
    public float lastRenderPitch;

    @Shadow
    protected boolean isCamera(){return false;}


    /**
     * {@code @Author} Mnky
     * {@code @reason} Affect the player's controls.
     */
    @Overwrite
    public void tickNewAi() {
        super.tickNewAi();
        if (this.isCamera()) {
            this.sidewaysSpeed = this.input.movementSideways;
            this.forwardSpeed = this.input.movementForward;
            this.jumping = this.input.jumping;
            this.lastRenderYaw = this.renderYaw;
            this.lastRenderPitch = this.renderPitch;
            this.renderPitch += (this.getPitch() - this.renderPitch) * 0.5F;
            this.renderYaw += (this.getYaw() - this.renderYaw) * 0.5F;
        }
        if(this.hasStatusEffect(AnnoyingEffects.CONTROLS_CRAB)) this.forwardSpeed = 0F;
        if(this.hasStatusEffect(AnnoyingEffects.CONTROLS_MIRROR)) this.sidewaysSpeed *= -1F;
        if(this.hasStatusEffect(AnnoyingEffects.CONTROLS_ALWAYS_JUMP)) this.jumping = true;
        if(this.hasStatusEffect(AnnoyingEffects.REALLY_COLD)) this.jumping = false;
    }
}

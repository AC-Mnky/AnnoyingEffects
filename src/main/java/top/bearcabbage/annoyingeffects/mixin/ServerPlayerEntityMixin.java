package top.bearcabbage.annoyingeffects.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;
import top.bearcabbage.annoyingeffects.StatusEffectInstanceStackHolder;

import java.util.Stack;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity implements StatusEffectInstanceStackHolder {

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Unique
    private final Stack<StatusEffectInstance> statusEffectInstanceStack = new Stack<>();

    @Unique
    public void pushStatusEffect(StatusEffectInstance effect){
        statusEffectInstanceStack.push(effect);
    }

    @Unique
    public StatusEffectInstance popStatusEffect(){
        if(statusEffectInstanceStack.empty()) return null;
        return statusEffectInstanceStack.pop();
    }

    /**
     * {@code @Author} Mnky
     * {@code @reason} Curse of vanishing status effect.
     */
    @Override
    protected void dropInventory() {
        if(this.hasStatusEffect(AnnoyingEffects.CURSE_OF_VANISHING)) super.getInventory().clear();

        super.dropInventory();
    }

}

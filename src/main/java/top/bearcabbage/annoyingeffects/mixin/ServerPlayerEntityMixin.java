package top.bearcabbage.annoyingeffects.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import top.bearcabbage.annoyingeffects.StatusEffectInstanceStackHolder;

import java.util.Stack;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends Entity implements StatusEffectInstanceStackHolder {

    public ServerPlayerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
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

}

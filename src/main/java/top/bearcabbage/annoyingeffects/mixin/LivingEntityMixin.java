package top.bearcabbage.annoyingeffects.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import top.bearcabbage.annoyingeffects.StatusEffectInstanceStackHolder;

import java.util.Stack;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements StatusEffectInstanceStackHolder {
    public LivingEntityMixin(EntityType<?> type, World world) {
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

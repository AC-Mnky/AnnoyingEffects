package top.bearcabbage.annoyingeffects.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends Entity{

    public CreeperEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    /**
     * {@code @Author} Mnky
     * {@code @reason} Blocks creeper rendering.
     */
    @Override
    public boolean shouldRender(double distance) {
        assert MinecraftClient.getInstance().player != null;
        if (MinecraftClient.getInstance().player.hasStatusEffect(AnnoyingEffects.CREEPERPHOBIA)){
            return false;
        }
        return super.shouldRender(distance);
    }

}

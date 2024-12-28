package top.bearcabbage.annoyingeffects.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;

//@Environment(EnvType.CLIENT)
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
    @Environment(EnvType.CLIENT)
    public boolean shouldRender(double distance) {
        assert MinecraftClient.getInstance().player != null;
        if (MinecraftClient.getInstance().player.hasStatusEffect(AnnoyingEffects.CREEPERPHOBIA)){
            return false;
        }
        return super.shouldRender(distance);
    }

}

//package top.bearcabbage.annoyingeffects.mixin;
//
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityType;
//import net.minecraft.entity.data.DataTracker;
//import net.minecraft.entity.mob.CreeperEntity;
//import net.minecraft.nbt.NbtCompound;
//import net.minecraft.world.World;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Unique;
//import top.bearcabbage.annoyingeffects.awaAcc;
//
//@Mixin(CreeperEntity.class)
//public class CreeperEntityMixin extends Entity implements awaAcc {
//    @Unique
//    private static boolean shouldRender;
//
//    public CreeperEntityMixin(EntityType<?> type, World world) {
//        super(type, world);
//    }
//
//
//    @Override
//    protected void initDataTracker(DataTracker.Builder builder) {
//
//    }
//
////    @Overwrite
//    public boolean shouldRender(double distance) {
//        return shouldRender && super.shouldRender(distance);
//    }
//
//    @Override
//    protected void readCustomDataFromNbt(NbtCompound nbt) {
//
//    }
//
//    @Override
//    protected void writeCustomDataToNbt(NbtCompound nbt) {
//
//    }
//
//
//    @Override
//    public boolean getShouldRener() {
//        return shouldRender;
//    }
//
//    public void setShouldRender(boolean shouldRender){
//        CreeperEntityMixin.shouldRender = shouldRender;
//    }
//
//}

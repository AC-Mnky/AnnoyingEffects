package top.bearcabbage.annoyingeffects.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.bearcabbage.annoyingeffects.AnnoyingEffects;

import java.util.Objects;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(
            method = {"onDeath"},
            at = {@At("HEAD")}
    )
    private void injectOnDeathForCurseOfVanishingStatusEffect(CallbackInfo ci){
//        if(!((LivingEntity)this instanceof PlayerEntity player)) return;
        if(this.hasStatusEffect(AnnoyingEffects.CURSE_OF_VANISHING)){
            int amplifier = Objects.requireNonNull(this.getStatusEffect(AnnoyingEffects.CURSE_OF_VANISHING)).getAmplifier();
//            this.getInventory().clear();
            Random random = this.getRandom();
//            ItemStack pumpkin = new ItemStack(Items.CARVED_PUMPKIN, 1);
//            RegistryEntry<Enchantment> enchantment = (RegistryEntry) this.getWorld().getRegistryManager().get(RegistryKey.ofRegistry(Identifier.ofVanilla("enchantment"))).getEntry(Identifier.ofVanilla("binding_curse")).orElseThrow();
            RegistryEntry<Enchantment> enchantment = (RegistryEntry) this.getWorld().getRegistryManager().get(RegistryKey.ofRegistry(Identifier.ofVanilla("enchantment"))).getEntry(Identifier.ofVanilla("vanishing_curse")).orElseThrow();
//            pumpkin.addEnchantment(enchantment, 1);
//            pumpkin.apply(DataComponentTypes.ITEM_NAME, Text.of("噩梦缠绕"), UnaryOperator.identity());
            for(DefaultedList<ItemStack> inventory: new DefaultedList[]{this.getInventory().main, this.getInventory().armor, this.getInventory().offHand}){
                for(int i=0;i<inventory.size();++i){
                    if(!inventory.get(i).isEmpty() && random.nextInt(5)<=amplifier){
                        ItemStack itemStack = inventory.get(i);
                        itemStack.addEnchantment(enchantment, 1);
//                        inventory.set(i, pumpkin.copy());
                    }
                }
            }
//            this.getInventory().dropAll();
        }
    }

//    /**
//     * {@code @Author} Mnky
//     * {@code @reason} Curse of vanishing status effect.
//     */
//    @Override
//    protected void dropInventory() {
//        if(this.hasStatusEffect(AnnoyingEffects.CURSE_OF_VANISHING)) super.getInventory().clear();
//
//        super.dropInventory();
//    }

}

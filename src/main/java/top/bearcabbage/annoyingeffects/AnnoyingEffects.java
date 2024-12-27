package top.bearcabbage.annoyingeffects;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.bearcabbage.annoyingeffects.effect.*;

import static net.minecraft.entity.effect.StatusEffects.NAUSEA;

public class AnnoyingEffects implements ModInitializer {
	public static final String MOD_ID = "annoyingeffects";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final StatusEffect EXP = new ExpStatusEffect();
	public static final StatusEffect REALLYHOT = new ReallyHotStatusEffect();
	public static final StatusEffect REALLYCOLD = new ReallyColdStatusEffect();
	public static final StatusEffect ANOREXIA = new AnorexiaStatusEffect();
//	public static final StatusEffect CREEPERPHOBIA = new CreeperphobiaStatusEffect();
	public static final RegistryEntry<StatusEffect> ANOREXIA_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "anorexia"), ANOREXIA);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		Registry.register(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "exp"), EXP);
		Registry.register(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "really_hot"), REALLYHOT);
		Registry.register(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "really_cold"), REALLYCOLD);
		Registry.register(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "anorexia"), ANOREXIA);
//		Registry.register(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "creeperphobia"), CREEPERPHOBIA);




		UseItemCallback.EVENT.register((player, world, hand) -> {
			ItemStack itemStack = player.getStackInHand(hand);
			if(itemStack.getItem().getComponents().contains(DataComponentTypes.FOOD) &&
					player.hasStatusEffect(ANOREXIA_ENTRY) &&
//					player.hasStatusEffect(Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "anorexia"), new AnorexiaStatusEffect())) &&
					!player.isSpectator()){
//				player.damage(player.getDamageSources().cactus(), 1.0F);
				player.addStatusEffect(new StatusEffectInstance(NAUSEA, 300));
				return TypedActionResult.success(itemStack);
			}

			return TypedActionResult.pass(itemStack);
		});

		LOGGER.info("Hello Fabric world!");
	}
}
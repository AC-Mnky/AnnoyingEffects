package top.bearcabbage.annoyingeffects;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
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

	public static final StatusEffect ANOREXIA = new AnorexiaStatusEffect();
	public static final StatusEffect CARROT_CURSE = new CarrotCurseStatusEffect();
	public static final StatusEffect CHANNELING = new ChannelingStatusEffect();
	public static final StatusEffect CHAOTIC_EXPLOSION = new ChaoticExplosionStatusEffect();
	public static final StatusEffect CHAOTIC_TELEPORTATION = new ChaoticTeleportationStatusEffect();
	public static final StatusEffect CONTROLS_ALWAYS_DIG = new ControlsAlwaysDigStatusEffect();
	public static final StatusEffect CONTROLS_ALWAYS_JUMP = new ControlsAlwaysJumpStatusEffect();
	public static final StatusEffect CONTROLS_CHAOTIC_USE = new ControlsChaoticUseStatusEffect();
	public static final StatusEffect CONTROLS_CRAB = new ControlsCrabStatusEffect();
	public static final StatusEffect CONTROLS_MIRROR = new ControlsMirrorStatusEffect();
	public static final StatusEffect CONTROLS_SHORT_REACH = new ControlsShortReachStatusEffect();
	public static final StatusEffect CRAWLER = new CrawlerStatusEffect();
	public static final StatusEffect CREEPERPHOBIA = new CreeperphobiaStatusEffect();
	public static final StatusEffect CURSE_OF_VANISHING = new CurseOfVanishingStatusEffect();
	public static final StatusEffect DISABLE_CRAFTING_TABLE = new DisableCraftingTableStatusEffect();
	public static final StatusEffect DISABLE_INVENTORY = new DisableInventoryStatusEffect();
	public static final StatusEffect DISABLE_SLEEPING = new DisableSleepingStatusEffect();
	public static final StatusEffect ENDERMAN_HOSTILE = new EndermanHostileStatusEffect();
	public static final StatusEffect HEAVINESS = new HeavinessStatusEffect();
	public static final StatusEffect HORSELESS = new HorselessStatusEffect();
	public static final StatusEffect MISFORTUNE = new MisfortuneStatusEffect();
	public static final StatusEffect OPPRESSED = new OppressedStatusEffect();
	public static final StatusEffect REALLY_COLD = new ReallyColdStatusEffect();
	public static final StatusEffect REALLY_HOT = new ReallyHotStatusEffect();
	public static final StatusEffect REPEATER = new RepeaterStatusEffect();
	public static final StatusEffect SCHIZOPHRENIA = new SchizophreniaStatusEffect();
	public static final StatusEffect SLIPPY = new SlippyStatusEffect();
	public static final StatusEffect SPIN = new SpinStatusEffect();
	public static final StatusEffect SWAPPING_HANDS = new SwappingHandsStatusEffect();
	public static final StatusEffect TANGLING_NIGHTMARE = new TanglingNightmareStatusEffect();
	public static final StatusEffect TARGETED = new TargetedStatusEffect();
	public static final StatusEffect VOICELESS = new VoicelessStatusEffect();
	public static final StatusEffect VULNERABLE = new VulnerableStatusEffect();
	public static final StatusEffect WATER_FILLING = new WaterFillingStatusEffect();


	public static final RegistryEntry<StatusEffect> ANOREXIA_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "anorexia"), ANOREXIA);
//	public static final RegistryEntry<StatusEffect> CARROT_CURSE_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "carrot_curse"), CARROT_CURSE);
//	public static final RegistryEntry<StatusEffect> CHANNELING_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "channeling"), CHANNELING);
//	public static final RegistryEntry<StatusEffect> CHAOTIC_EXPLOSION_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "chaotic_explosion"), CHAOTIC_EXPLOSION);
//	public static final RegistryEntry<StatusEffect> CHAOTIC_TELEPORTATION_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "chaotic_teleportation"), CHAOTIC_TELEPORTATION);
//	public static final RegistryEntry<StatusEffect> CONTROLS_ALWAYS_DIG_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "controls_always_dig"), CONTROLS_ALWAYS_DIG);
//	public static final RegistryEntry<StatusEffect> CONTROLS_ALWAYS_JUMP_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "controls_always_jump"), CONTROLS_ALWAYS_JUMP);
//	public static final RegistryEntry<StatusEffect> CONTROLS_CHAOTIC_USE_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "controls_chaotic_use"), CONTROLS_CHAOTIC_USE);
//	public static final RegistryEntry<StatusEffect> CONTROLS_CRAB_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "controls_crab"), CONTROLS_CRAB);
//	public static final RegistryEntry<StatusEffect> CONTROLS_MIRROR_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "controls_mirror"), CONTROLS_MIRROR);
//	public static final RegistryEntry<StatusEffect> CONTROLS_SHORT_REACH_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "controls_short_reach"), CONTROLS_SHORT_REACH);
//	public static final RegistryEntry<StatusEffect> CRAWLER_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "crawler"), CRAWLER);
	public static final RegistryEntry<StatusEffect> CREEPERPHOBIA_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "creeperphobia"), CREEPERPHOBIA);
//	public static final RegistryEntry<StatusEffect> CURSE_OF_VANISHING_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "curse_of_vanishing"), CURSE_OF_VANISHING);
//	public static final RegistryEntry<StatusEffect> DISABLE_CRAFTING_TABLE_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "disable_crafting_table"), DISABLE_CRAFTING_TABLE);
//	public static final RegistryEntry<StatusEffect> DISABLE_INVENTORY_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "disable_inventory"), DISABLE_INVENTORY);
//	public static final RegistryEntry<StatusEffect> DISABLE_SLEEPING_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "disable_sleeping"), DISABLE_SLEEPING);
//	public static final RegistryEntry<StatusEffect> ENDERMAN_HOSTILE_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "enderman_hostile"), ENDERMAN_HOSTILE);
//	public static final RegistryEntry<StatusEffect> HEAVINESS_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "heaviness"), HEAVINESS);
//	public static final RegistryEntry<StatusEffect> HORSELESS_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "horseless"), HORSELESS);
//	public static final RegistryEntry<StatusEffect> MISFORTUNE_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "misfortune"), MISFORTUNE);
//	public static final RegistryEntry<StatusEffect> OPPRESSED_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "oppressed"), OPPRESSED);
	public static final RegistryEntry<StatusEffect> REALLY_COLD_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "really_cold"), REALLY_COLD);
	public static final RegistryEntry<StatusEffect> REALLY_HOT_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "really_hot"), REALLY_HOT);
//	public static final RegistryEntry<StatusEffect> REPEATER_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "repeater"), REPEATER);
//	public static final RegistryEntry<StatusEffect> SCHIZOPHRENIA_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "schizophrenia"), SCHIZOPHRENIA);
//	public static final RegistryEntry<StatusEffect> SLIPPY_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "slippy"), SLIPPY);
//	public static final RegistryEntry<StatusEffect> SPIN_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "spin"), SPIN);
//	public static final RegistryEntry<StatusEffect> SWAPPING_HANDS_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "swapping_hands"), SWAPPING_HANDS);
//	public static final RegistryEntry<StatusEffect> TANGLING_NIGHTMARE_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "tangling_nightmare"), TANGLING_NIGHTMARE);
//	public static final RegistryEntry<StatusEffect> TARGETED_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "targeted"), TARGETED);
//	public static final RegistryEntry<StatusEffect> VOICELESS_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "voiceless"), VOICELESS);
//	public static final RegistryEntry<StatusEffect> VULNERABLE_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "vulnerable"), VULNERABLE);
//	public static final RegistryEntry<StatusEffect> WATER_FILLING_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "water_filling"), WATER_FILLING);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

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
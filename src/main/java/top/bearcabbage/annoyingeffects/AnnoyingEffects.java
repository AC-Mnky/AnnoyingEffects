package top.bearcabbage.annoyingeffects;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.bearcabbage.annoyingeffects.effect.*;
import top.bearcabbage.annoyingeffects.utils.ConfigReadAndWrite;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.UnaryOperator;

import static net.minecraft.entity.effect.StatusEffects.*;
import static net.minecraft.item.Items.*;

public class AnnoyingEffects implements ModInitializer {
	public static final String MOD_ID = "annoyingeffects";
	private static final ConfigReadAndWrite config = new ConfigReadAndWrite(FabricLoader.getInstance().getConfigDir().resolve("AnnoyingEffects.json"));

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final RegistryEntry<StatusEffect> ANOREXIA = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "anorexia"), new AnorexiaStatusEffect());
	public static final RegistryEntry<StatusEffect> CARROT_CURSE = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "carrot_curse"), new CarrotCurseStatusEffect());
	public static final RegistryEntry<StatusEffect> CHANNELING = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "channeling"), new ChannelingStatusEffect());
	public static final RegistryEntry<StatusEffect> CHAOTIC_EXPLOSION = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "chaotic_explosion"), new ChaoticExplosionStatusEffect());
	public static final RegistryEntry<StatusEffect> CHAOTIC_TELEPORTATION = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "chaotic_teleportation"), new ChaoticTeleportationStatusEffect());
	public static final RegistryEntry<StatusEffect> CONTROLS_ALWAYS_DIG = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "controls_always_dig"), new ControlsAlwaysDigStatusEffect());
	public static final RegistryEntry<StatusEffect> CONTROLS_ALWAYS_JUMP = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "controls_always_jump"), new ControlsAlwaysJumpStatusEffect());
	public static final RegistryEntry<StatusEffect> CONTROLS_CHAOTIC_USE = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "controls_chaotic_use"), new ControlsChaoticUseStatusEffect());
	public static final RegistryEntry<StatusEffect> CONTROLS_CRAB = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "controls_crab"), new ControlsCrabStatusEffect());
	public static final RegistryEntry<StatusEffect> CONTROLS_MIRROR = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "controls_mirror"), new ControlsMirrorStatusEffect());
	public static final RegistryEntry<StatusEffect> CONTROLS_SHORT_REACH = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "controls_short_reach"), new ControlsShortReachStatusEffect().addAttributeModifier(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE, Identifier.of("annoyingeffect.controls_short_range"), -2.0F, EntityAttributeModifier.Operation.ADD_VALUE).addAttributeModifier(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, Identifier.of("annoyingeffect.controls_short_range"), -2.0F, EntityAttributeModifier.Operation.ADD_VALUE));
	public static final RegistryEntry<StatusEffect> CRAWLER = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "crawler"), new CrawlerStatusEffect());
	public static final RegistryEntry<StatusEffect> CREEPERPHOBIA = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "creeperphobia"), new CreeperphobiaStatusEffect());
	public static final RegistryEntry<StatusEffect> CURSE_OF_VANISHING = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "curse_of_vanishing"), new CurseOfVanishingStatusEffect());
	public static final RegistryEntry<StatusEffect> DISABLE_CRAFTING_TABLE = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "disable_crafting_table"), new DisableCraftingTableStatusEffect());
	public static final RegistryEntry<StatusEffect> DISABLE_INVENTORY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "disable_inventory"), new DisableInventoryStatusEffect());
	public static final RegistryEntry<StatusEffect> DISABLE_SLEEPING = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "disable_sleeping"), new DisableSleepingStatusEffect());
	public static final RegistryEntry<StatusEffect> ENDERMAN_HOSTILE = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "enderman_hostile"), new EndermanHostileStatusEffect());
	public static final RegistryEntry<StatusEffect> HEAVINESS = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "heaviness"), new HeavinessStatusEffect().addAttributeModifier(EntityAttributes.GENERIC_SAFE_FALL_DISTANCE, Identifier.of("annoyingeffect.heaviness.jump"), -2.25F, EntityAttributeModifier.Operation.ADD_VALUE).addAttributeModifier(EntityAttributes.GENERIC_FALL_DAMAGE_MULTIPLIER, Identifier.of("annoyingeffect.heaviness.jump"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE).addAttributeModifier(EntityAttributes.GENERIC_GRAVITY, Identifier.of("annoyingeffect.heaviness.gravity"), 1.0F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
	public static final RegistryEntry<StatusEffect> HORSELESS = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "horseless"), new HorselessStatusEffect());
	public static final RegistryEntry<StatusEffect> MISFORTUNE = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "misfortune"), new MisfortuneStatusEffect());
	public static final RegistryEntry<StatusEffect> OPPRESSED = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "oppressed"), new OppressedStatusEffect());
	public static final RegistryEntry<StatusEffect> REALLY_COLD = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "really_cold"), new ReallyColdStatusEffect());
	public static final RegistryEntry<StatusEffect> REALLY_HOT = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "really_hot"), new ReallyHotStatusEffect());
	public static final RegistryEntry<StatusEffect> REPEATER = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "repeater"), new RepeaterStatusEffect());
	public static final RegistryEntry<StatusEffect> SCHIZOPHRENIA = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "schizophrenia"), new SchizophreniaStatusEffect());
	public static final RegistryEntry<StatusEffect> SLIPPY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "slippy"), new SlippyStatusEffect());
	public static final RegistryEntry<StatusEffect> SPIN = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "spin"), new SpinStatusEffect());
	public static final RegistryEntry<StatusEffect> SWAPPING_HANDS = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "swapping_hands"), new SwappingHandsStatusEffect());
	public static final RegistryEntry<StatusEffect> TANGLING_NIGHTMARE = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "tangling_nightmare"), new TanglingNightmareStatusEffect());
	public static final RegistryEntry<StatusEffect> TARGETED = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "targeted"), new TargetedStatusEffect());
	public static final RegistryEntry<StatusEffect> VOICELESS = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "voiceless"), new VoicelessStatusEffect());
	public static final RegistryEntry<StatusEffect> VULNERABLE = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "vulnerable"), new VulnerableStatusEffect());
	public static final RegistryEntry<StatusEffect> WATER_FILLING = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "water_filling"), new WaterFillingStatusEffect());

	public static final Map<RegistryEntry<StatusEffect>, Integer> STATUS_EFFECT_MAP = new HashMap<>();

	private static int pack(int duration, int interval){
		return (duration << 16) + interval;
	}


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		loadStatusEffects();
//		{
//			STATUS_EFFECT_MAP.put(ANOREXIA, pack(200, 200));
//			STATUS_EFFECT_MAP.put(CARROT_CURSE, pack(120, 600));
//			STATUS_EFFECT_MAP.put(CHANNELING, pack(20, 100));
//			STATUS_EFFECT_MAP.put(CHAOTIC_EXPLOSION, pack(30, 600));
//			STATUS_EFFECT_MAP.put(CHAOTIC_TELEPORTATION, pack(5, 200));
//			STATUS_EFFECT_MAP.put(CONTROLS_ALWAYS_DIG, pack(30, 300));
//			STATUS_EFFECT_MAP.put(CONTROLS_ALWAYS_JUMP, pack(60, 600));
//			STATUS_EFFECT_MAP.put(CONTROLS_CHAOTIC_USE, pack(120, 600));
//			STATUS_EFFECT_MAP.put(CONTROLS_CRAB, pack(120, 600));
//			STATUS_EFFECT_MAP.put(CONTROLS_MIRROR, pack(120, 600));
//			STATUS_EFFECT_MAP.put(CONTROLS_SHORT_REACH, pack(120, 600));
//			STATUS_EFFECT_MAP.put(CRAWLER, pack(60, 600));
//			STATUS_EFFECT_MAP.put(CREEPERPHOBIA, pack(120, 600));
//			STATUS_EFFECT_MAP.put(CURSE_OF_VANISHING, pack(50, 100));
//			STATUS_EFFECT_MAP.put(DISABLE_CRAFTING_TABLE, pack(60, 600));
//			STATUS_EFFECT_MAP.put(DISABLE_INVENTORY, pack(10, 100));
//			STATUS_EFFECT_MAP.put(DISABLE_SLEEPING, pack(120, 600));
//			STATUS_EFFECT_MAP.put(ENDERMAN_HOSTILE, pack(120, 600));
//			STATUS_EFFECT_MAP.put(HEAVINESS, pack(60, 600));
//			STATUS_EFFECT_MAP.put(HORSELESS, pack(5, 300));
//			STATUS_EFFECT_MAP.put(MISFORTUNE, pack(-1, -1));
//			STATUS_EFFECT_MAP.put(OPPRESSED, pack(10, 600));
//			STATUS_EFFECT_MAP.put(REALLY_COLD, pack(3, 120));
//			STATUS_EFFECT_MAP.put(REALLY_HOT, pack(3, 120));
//			STATUS_EFFECT_MAP.put(REPEATER, pack(120, 600));
//			STATUS_EFFECT_MAP.put(SCHIZOPHRENIA, pack(-1, -1));
//			STATUS_EFFECT_MAP.put(SLIPPY, pack(120, 600));
//			STATUS_EFFECT_MAP.put(SPIN, pack(10, 600));
//			STATUS_EFFECT_MAP.put(SWAPPING_HANDS, pack(10, 200));
//			STATUS_EFFECT_MAP.put(TARGETED, pack(120, 600));
//			STATUS_EFFECT_MAP.put(VOICELESS, pack(120, 600));
//			STATUS_EFFECT_MAP.put(VULNERABLE, pack(120, 600));
//			STATUS_EFFECT_MAP.put(WATER_FILLING, pack(-1, 15));
//
//			STATUS_EFFECT_MAP.put(BLINDNESS, pack(60, 600));
//			STATUS_EFFECT_MAP.put(DARKNESS, pack(60, 600));
//			STATUS_EFFECT_MAP.put(HUNGER, pack(60, 600));
//			STATUS_EFFECT_MAP.put(INFESTED, pack(120, 600));
//			STATUS_EFFECT_MAP.put(LEVITATION, pack(10, 200));
//			STATUS_EFFECT_MAP.put(MINING_FATIGUE, pack(60, 600));
//			STATUS_EFFECT_MAP.put(NAUSEA, pack(10, 200));
//			STATUS_EFFECT_MAP.put(SLOWNESS, pack(60, 600));
//			STATUS_EFFECT_MAP.put(WEAKNESS, pack(120, 600));
//			STATUS_EFFECT_MAP.put(WITHER, pack(10, 200));
//
//		}

		UseItemCallback.EVENT.register((player, world, hand) -> {
			ItemStack itemStack = player.getStackInHand(hand);
			if(itemStack.getItem().getComponents().contains(DataComponentTypes.FOOD) &&
					player.hasStatusEffect(ANOREXIA) &&
					!player.isSpectator() && !player.getWorld().isClient() &&
					!(itemStack.getItem() == CARROT && Objects.equals(itemStack.getName().getString(), "AC is watching you"))){
				StatusEffectInstanceStackHolder stackHolder = (StatusEffectInstanceStackHolder) player;
				stackHolder.pushStatusEffect(new StatusEffectInstance(NAUSEA, 300));
				player.sendMessage(Text.translatable("messages.annoyingeffects.anorexia"), true);
				return TypedActionResult.fail(itemStack);
			}
			else if(itemStack.getItem() == MILK_BUCKET &&
					player.hasStatusEffect(TANGLING_NIGHTMARE) &&
					!player.isSpectator()){
				ItemStack pumpkin = new ItemStack(Items.CARVED_PUMPKIN, 1);
				RegistryEntry<Enchantment> enchantment = (RegistryEntry) world.getRegistryManager().get(RegistryKey.ofRegistry(Identifier.ofVanilla("enchantment"))).getEntry(Identifier.ofVanilla("binding_curse")).orElseThrow();
				pumpkin.addEnchantment(enchantment, 1);
				pumpkin.apply(DataComponentTypes.ITEM_NAME, Text.of("噩梦缠绕"), UnaryOperator.identity());
				player.setStackInHand(hand, pumpkin);
				player.sendMessage(Text.translatable("messages.annoyingeffects.tangling_nightmare_milk"), true);
				return TypedActionResult.pass(pumpkin);
			}

			return TypedActionResult.pass(itemStack);
		});


		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
//			ItemStack itemStack = player.getStackInHand(hand);
//			Direction side = hitResult.getSide();
			BlockPos pos = hitResult.getBlockPos();
			BlockState blockState = player.getWorld().getBlockState(pos);
			if(player.hasStatusEffect(DISABLE_CRAFTING_TABLE) && !player.isSpectator() &&
					(blockState.getBlock() == Block.getBlockFromItem(CRAFTING_TABLE) ||
							blockState.getBlock() == Block.getBlockFromItem(CRAFTER))) {
				player.sendMessage(Text.translatable("messages.annoyingeffects.crafting_fatigue"), true);
				return ActionResult.FAIL;
			}
			if(player.hasStatusEffect(DISABLE_SLEEPING) && !player.isSpectator() &&
					blockState.getBlock() instanceof BedBlock){
//					(blockState.getBlock().getName().getString().contains("Bed"))) { //// 这段我自己都绷不住了（是的我知道基岩）  --AC
				player.sendMessage(Text.translatable("messages.annoyingeffects.insomnia"), true);
				return ActionResult.FAIL;
			}
			return ActionResult.PASS;
		});

		ServerTickEvents.END_SERVER_TICK.register((server) -> {
			for(ServerPlayerEntity player: server.getPlayerManager().getPlayerList()){
				StatusEffectInstanceStackHolder stackHolder = (StatusEffectInstanceStackHolder) player;
				for(;;) {
					StatusEffectInstance effect = stackHolder.popStatusEffect();
					if (effect == null) break;
					player.addStatusEffect(effect);
				}
			}
		});
	}

	private void loadStatusEffects() {
		STATUS_EFFECT_MAP.put(ANOREXIA, pack(config.getDurationAndInterval("anorexia", Map.of("duration", 200, "interval", 200))));
		STATUS_EFFECT_MAP.put(CARROT_CURSE, pack(config.getDurationAndInterval("carrot_curse", Map.of("duration", 120, "interval", 600))));
		STATUS_EFFECT_MAP.put(CHANNELING, pack(config.getDurationAndInterval("channeling", Map.of("duration", 20, "interval", 100))));
		STATUS_EFFECT_MAP.put(CHAOTIC_EXPLOSION, pack(config.getDurationAndInterval("chaotic_explosion", Map.of("duration", 30, "interval", 600))));
		STATUS_EFFECT_MAP.put(CHAOTIC_TELEPORTATION, pack(config.getDurationAndInterval("chaotic_teleportation", Map.of("duration", 5, "interval", 200))));
		STATUS_EFFECT_MAP.put(CONTROLS_ALWAYS_DIG, pack(config.getDurationAndInterval("controls_always_dig", Map.of("duration", 30, "interval", 300))));
		STATUS_EFFECT_MAP.put(CONTROLS_ALWAYS_JUMP, pack(config.getDurationAndInterval("controls_always_jump", Map.of("duration", 60, "interval", 600))));
		STATUS_EFFECT_MAP.put(CONTROLS_CHAOTIC_USE, pack(config.getDurationAndInterval("controls_chaotic_use", Map.of("duration", 120, "interval", 600))));
		STATUS_EFFECT_MAP.put(CONTROLS_CRAB, pack(config.getDurationAndInterval("controls_crab", Map.of("duration", 120, "interval", 600))));
		STATUS_EFFECT_MAP.put(CONTROLS_MIRROR, pack(config.getDurationAndInterval("controls_mirror", Map.of("duration", 120, "interval", 600))));
		STATUS_EFFECT_MAP.put(CONTROLS_SHORT_REACH, pack(config.getDurationAndInterval("controls_short_reach", Map.of("duration", 120, "interval", 600))));
		STATUS_EFFECT_MAP.put(CRAWLER, pack(config.getDurationAndInterval("crawler", Map.of("duration", 60, "interval", 600))));
		STATUS_EFFECT_MAP.put(CREEPERPHOBIA, pack(config.getDurationAndInterval("creeperphobia", Map.of("duration", 120, "interval", 600))));
		STATUS_EFFECT_MAP.put(CURSE_OF_VANISHING, pack(config.getDurationAndInterval("curse_of_vanishing", Map.of("duration", 50, "interval", 100))));
		STATUS_EFFECT_MAP.put(DISABLE_CRAFTING_TABLE, pack(config.getDurationAndInterval("disable_crafting_table", Map.of("duration", 60, "interval", 600))));
		STATUS_EFFECT_MAP.put(DISABLE_INVENTORY, pack(config.getDurationAndInterval("disable_inventory", Map.of("duration", 10, "interval", 100))));
		STATUS_EFFECT_MAP.put(DISABLE_SLEEPING, pack(config.getDurationAndInterval("disable_sleeping", Map.of("duration", 120, "interval", 600))));
		STATUS_EFFECT_MAP.put(ENDERMAN_HOSTILE, pack(config.getDurationAndInterval("enderman_hostile", Map.of("duration", 120, "interval", 600))));
		STATUS_EFFECT_MAP.put(HEAVINESS, pack(config.getDurationAndInterval("heaviness", Map.of("duration", 60, "interval", 600))));
		STATUS_EFFECT_MAP.put(HORSELESS, pack(config.getDurationAndInterval("horseless", Map.of("duration", 5, "interval", 300))));
		STATUS_EFFECT_MAP.put(MISFORTUNE, pack(config.getDurationAndInterval("misfortune", Map.of("duration", -1, "interval", -1))));
		STATUS_EFFECT_MAP.put(OPPRESSED, pack(config.getDurationAndInterval("oppressed", Map.of("duration", 10, "interval", 600))));
		STATUS_EFFECT_MAP.put(REALLY_COLD, pack(config.getDurationAndInterval("really_cold", Map.of("duration", 3, "interval", 120))));
		STATUS_EFFECT_MAP.put(REALLY_HOT, pack(config.getDurationAndInterval("really_hot", Map.of("duration", 3, "interval", 120))));
		STATUS_EFFECT_MAP.put(REPEATER, pack(config.getDurationAndInterval("repeater", Map.of("duration", 120, "interval", 600))));
		STATUS_EFFECT_MAP.put(SCHIZOPHRENIA, pack(config.getDurationAndInterval("schizophrenia", Map.of("duration", -1, "interval", -1))));
		STATUS_EFFECT_MAP.put(SLIPPY, pack(config.getDurationAndInterval("slippy", Map.of("duration", 120, "interval", 600))));
		STATUS_EFFECT_MAP.put(SPIN, pack(config.getDurationAndInterval("spin", Map.of("duration", 10, "interval", 600))));
		STATUS_EFFECT_MAP.put(SWAPPING_HANDS, pack(config.getDurationAndInterval("swapping_hands", Map.of("duration", 10, "interval", 200))));
		STATUS_EFFECT_MAP.put(TARGETED, pack(config.getDurationAndInterval("targeted", Map.of("duration", 120, "interval", 600))));
		STATUS_EFFECT_MAP.put(VOICELESS, pack(config.getDurationAndInterval("voiceless", Map.of("duration", 120, "interval", 600))));
		STATUS_EFFECT_MAP.put(VULNERABLE, pack(config.getDurationAndInterval("vulnerable", Map.of("duration", 120, "interval", 600))));
		STATUS_EFFECT_MAP.put(WATER_FILLING, pack(config.getDurationAndInterval("water_filling", Map.of("duration", -1, "interval", 15))));
		STATUS_EFFECT_MAP.put(BLINDNESS, pack(config.getDurationAndInterval("blindness", Map.of("duration", 60, "interval", 600))));
		STATUS_EFFECT_MAP.put(DARKNESS, pack(config.getDurationAndInterval("darkness", Map.of("duration", 60, "interval", 600))));
		STATUS_EFFECT_MAP.put(HUNGER, pack(config.getDurationAndInterval("hunger", Map.of("duration", 60, "interval", 600))));
		STATUS_EFFECT_MAP.put(INFESTED, pack(config.getDurationAndInterval("infested", Map.of("duration", 120, "interval", 600))));
		STATUS_EFFECT_MAP.put(LEVITATION, pack(config.getDurationAndInterval("levitation", Map.of("duration", 10, "interval", 200))));
		STATUS_EFFECT_MAP.put(MINING_FATIGUE, pack(config.getDurationAndInterval("mining_fatigue", Map.of("duration", 60, "interval", 600))));
		STATUS_EFFECT_MAP.put(NAUSEA, pack(config.getDurationAndInterval("nausea", Map.of("duration", 10, "interval", 200))));
		STATUS_EFFECT_MAP.put(SLOWNESS, pack(config.getDurationAndInterval("slowness", Map.of("duration", 60, "interval", 600))));
		STATUS_EFFECT_MAP.put(WEAKNESS, pack(config.getDurationAndInterval("weakness", Map.of("duration", 120, "interval", 600))));
		STATUS_EFFECT_MAP.put(WITHER, pack(config.getDurationAndInterval("wither", Map.of("duration", 10, "interval", 200))));
	}

	private int pack(Map<String, Integer> durationAndInterval) {
		return (durationAndInterval.get("duration") << 16) + durationAndInterval.get("interval");
	}
}
# Annoying Effects

[中文README](README_zh.md)

A Minecraft mod that adds many status effects that are usually not lethal but will make players feel uncomfortable. This mod will be used in [Lantern-in-Storm](https://github.com/BaicaiBear/Lantern-in-Storm/) for negative effects in the `unstable` area.

All effects are not explicitly stated and need to be discovered by the players themselves.

| Name / `CodeName` | Effect | Tags | Weak/Duration/Interval | Implemented | Icon |
| ----------------- | ------ | ---- | ----------------- | ----------- | ---- |
| Tangling nightmare | Randomly applies status effects from this table. Players cannot use milk. | / | / | ✅ | ✅ Enchanted Pumpkin Head |
| Tangling dreams | Randomly applies non-Nightmare tag status effects from this table. | / | / | ✅ | ✅ Two Candles |
| Anorexia | Cannot use food. | Handicap, Nightmare | 0/200/200 | ✅ | ✅ No Bread |
| Curse of not eating carrot / CarrotCurse | Receives a stack of poisonous potatoes every tick. | Handicap | 60/120/600 (only if the player eats too few carrots) | ✅ | ✅ Poisonous Potato |
| Channeling | (Only during thunderstorms) A lightning bolt strikes around every 20 ticks (with a certain probability of hitting). | Damage, Adaptable, Terrachanging, Nightmare | 0/20/100 (only during thunderstorms) | ✅ | ✅ Trident |
| Essence of fire / ChaoticExplosion | Continuously causes explosions (average 50 ticks). | Damage, Terrachanging, Nightmare | 0/30/600 | ✅ | ✅ Heating Symbol |
| Where am I? / ChaoticTeleportation | Triggers a chorus fruit teleport every 10 ticks. | SevereHandicap, Nightmare | 0/5/200 | ✅ | ✅ Popped Chorus Fruit |
| Mad miner / ControlsAlwaysDig | Continuously digs. | Adaptable, Terrachanging, Nightmare | 0/30/300 | ✅ | ✅ Red Pickaxe |
| Where is the toilet? / ControlsAlwaysJump | Continuously jumps. | Adaptable | 30/60/600 | ✅ | ✅ Toilet |
| Twitching / ControlsChaoticUse | Randomly uses the main hand item (average 50 ticks). | HighlyAdaptable | 60/120/600 | ✅ | ✅ 👋 |
| I am a crab / ControlsCrab | Cannot move forward or backward. | HighlyAdaptable, Nightmare | 0/120/600 | ��� | ✅ 🦀 |
| Mirror / ControlsMirror | Left-click to move right, right-click to move left. | HighlyAdaptable | 60/120/600 | ✅ | ✅ □\|□ |
| Short arms / ControlsShortReach | Reduces reach distance by 2. | Adaptable | 60/120/600 | ✅ | ✅ 🦖 |
| Crawler | Crawls. | Adaptable, Nightmare | 0/60/600 | ✅ | ✅ Trapdoor |
| Creeper? / Creeperphobia | Creepers are no longer rendered. Randomly hears creeper explosion sounds. | Subtle | 60/120/600 | ✅ | ✅ Creeper Head Front |
| Curse of vanishing | One-fifth of the items in the inventory disappear upon death. | Subtle | 25/50/100 | ✅ | ✅ Missing Texture *4 |
| Crafting fatigue / DisableCraftingTable | Cannot interact with crafting tables or crafting stations. | Handicap, Nightmare | 0/60/600 | ✅ | ✅ No Crafting Table |
| Where is my bag? / DisableInventory | Cannot open the inventory (similar to being in a Nether portal). | SevereHandicap | 10/10/100 | ✅ | ✅ No Storage Bag |
| Insomnia / DisableSleeping | Cannot interact with beds. Phantoms spawn in the open. | Handicap, Nightmare | 0/120/600 | ✅ | ✅ No Bed |
| Gaze / EndermanHostile | Attracts the hatred of all Endermen. | Subtle | 60/120/600 | ✅ | ✅ Enderman Head |
| Heaviness | Sinks in any situation. Increased gravity and fall damage. Fall damage in water. | Adaptable, Nightmare | 0/60/600 | ✅ | ✅ Anvil |
| Horseless | Horses around explode. | Subtle, Nightmare | 0/5/300 (riding: 60) | ✅ | ✅ No Horse |
| Misfortune | Drops fewer items when mining. | Subtle, Nightmare | / | ❌ Hard to implement | ✅ No Diamonds |
| Oppressed | Must keep head down, vertical angle cannot exceed 45 degrees. | Adaptable, Uncomfortable | 10/10/600 | ✅ | ✅ Leash |
| ReallyCold | Freezes (similar to powdered snow). Cannot jump. | Damage, Nightmare | 0/3/200 | ✅ | ✅ Powder Snow Bucket |
| ReallyHot | Burns. | Damage, Nightmare | 0/3/200 | ✅ | ✅ Lava Bucket |
| Repeater | Repeats what the last person said. | Joke | 60/120/600 | ▶️ Repeat Player | ✅ Redstone Repeater |
| Schizophrenia | Shifts the viewpoint origin and model rendering position 0.91 east. | SevereHandicap | / | ✅ | ✅ Crack |
| Slippy | Reduced friction and acceleration (similar to walking on ice). | HighlyAdaptable, Nightmare | 0/120/600 | ✅ | ✅ Ice Block |
| Spin | Continuously spins counterclockwise. | SevereHandicap, Uncomfortable | 10/10/600 | ✅ | ✅ 🌀 |
| Swapping hands | Swaps main and off-hand items every 20 ticks. | SevereHandicap | 6/10/200 | ✅ | ✅ ✋↔️🤚 |
| Targeted | Attracts all nearby projectiles, including your own. | Subtle, Nightmare | 0/120/600 | ✅ | ✅ Target |
| Voiceless | Cannot chat. | Joke, Nightmare | 0/120/600 | ✅ | ✅ Sculk Shrieker |
| Vulnerable | Takes 50% more damage. | Subtle | 60/120/600 | ✅ | ✅ 💔 |
| Memories of the old world / WaterFilling | Continuously generates water within a radius of 3 air blocks. Destroys air-containing blocks. Removes conduit power. | Damage, Adaptable, Terrachanging, Nightmare | 0/Water time ÷ 5/15 (only if water time > 75) | ✅ | ✅ Water (Block) |
| Blindness | Vanilla | Adaptable, Nightmare | 0/60/600 | / | / |
| Darkness | Vanilla | Adaptable | 30/60/600 | / | / |
| Hunger | Vanilla | Subtle | 30/60/600 | / | / |
| Infested | Vanilla | Damage, Nightmare | 0/120/600 | / | / |
| Levitation | Vanilla | Damage, SevereHandicap, Nightmare | 0/10/200 | / | / |
| Mining fatigue | Vanilla | Adaptable | 30/60/600 | / | / |
| Nausea | Vanilla | Joke, Nightmare | 0/10/200 | / | / |
| Slowness | Vanilla | Adaptable | 30/60/600 | / | / |
| Weakness | Vanilla | Subtle | 60/120/600 | / | / |
| Wither | Vanilla | Damage, Nightmare | 0/10/600 | / | / |

More possible additions in the future:

- Sleepy: You will suddenly fall asleep while walking.
- Driven: You will keep walking forward.
- Unstable: After being injured, you will drop random items (the probability of dropping a single item is about 1%, regardless of the number of items in the inventory).
- (Only in the Overworld) Set your personal respawn point to the current location.
- When opening a loot chest, all items in the chest turn into poisonous potatoes (enchantments are retained).
- Zero gravity: Lose gravity.
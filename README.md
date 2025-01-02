# Annoying Effects

一个Minecraft模组，加入了许多通常不致命但会使玩家感到难受的状态效果。这个模组将会用于[Lantern-in-Storm](https://github.com/BaicaiBear/Lantern-in-Storm/)中玩家在`unstable`区域的负面效果。

所有效果的效果都是不写明的，需要玩家自己探索发现。

| 名（Name / `CodeName`） | 效果                                                       | 标签 | 时长/间隔 | 实装       | 图标          |
| ---------------- | -------------------------------------------------------------------------- | -------------------------------------- | -------- |----------|-------------|
| 噩梦缠绕（Tangling nightmare） | 随机施加本表格中的状态效果。玩家无法使用牛奶。         | /                      | /                       | ✅       | ✅附魔南瓜头      |
| 厌食（Anorexia）   | 不能使用食物。                                      | Handicap                         | 120/600                 | ✅       | ✅禁止面包       |
| 不吃胡萝卜的下场（Curse of not eating carrot / CarrotCurse） | 每t获得一组毒马铃薯。                                    | Handicap    | 120/600（仅当玩家吃胡萝卜太少） | ▶️（存档） | ✅毒马铃薯       |
| 引雷（Channeling） | （仅雷雨天）平均每20t有一道雷生成在周围（有一定概率劈到）。 | Damage, Adaptable, Terrachanging | 20/100（仅雷雨天）          | ✅       | ✅三叉戟        |
| 火之精粹（Essence of fire / ChaoticExplosion） | 你会不停产生爆炸（平均50t）。                              | Damage, Terrachanging | 30/300         | ✅       | ✅加热符号       |
| 我在哪？（Where am I? / ChaoticTeleportation） | 每隔50t触发一次紫颂果传送。                             | SevereHandicap | 15/300              | ✅       | ✅爆裂紫颂果      |
| 抓狂（Mad miner / ControlsAlwaysDig） | 你会一直挖掘。                                             | Adaptable | 30/300             | ▶️很难右键！ | ✅红色稿子       |
| 想上厕所（Where is the toilet? / ControlsAlwaysJump） | 你会不停跳跃。                                             | Adaptable | 60/600                  | ✅       | ✅马桶         |
| 抽搐（Twitching / ControlsChaoticUse） | 你会随机地使用主手物品（平均50t）。                        | HighlyAdaptable | 120/600             | ✅ | ✅👋         |
| 我是螃蟹（I am a crab / ControlsCrab） | 你不能前进或后退。                                         | HighlyAdaptable | 120/600           | ✅                | ✅🦀         |
| 镜像（Mirror / ControlsMirror） | 左键向右走，右键向左走。                                   | HighlyAdaptable | 120/600                | ✅       | ✅□\|□       |
| 手短（Short arms / ControlsShortReach） | 你的reach distance减少2。                                  | Adaptable | 60/600                  | ✅       | ✅🦖         |
| 阴暗地爬行（Crawler） | 你会爬行。                                                 | Adaptable | 60/600                  | ✅       | ✅活板门        |
| Creeper？（Creeper? / Creeperphobia） | 苦力怕不再渲染。你会随机幻听到苦力怕爆炸的声音。           | Subtle | 120/600                 | ▶️声音 | ✅Creeper头正面 |
| 消失诅咒（Curse of vanishing） | 你死亡时物品栏会清空。                                     | Subtle | 20/100                  | ✅       | ✅材质丢失*4  |
| 无法工作（Crafting fatigue / DisableCraftingTable） | 你不能与工作台或合成器交互。                             | Handicap                         | 120/600                 | ✅       | ✅禁止工作台   |
| 我的包呢？（Where is my bag? / DisableInventory） | 无法打开物品栏。（类似于下界传送门里。）                   | SevereHandicap | 15/150                  | ✅       | ✅禁止收纳袋    |
| 失眠（Insomnia / DisableSleeping） | 你不能与床交互。幻翼会在露天时生成。                       | Handicap | 120/600                 | ▶️幻翼 | ✅禁止床      |
| 凝视（Gaze / EndermanHostile） | 吸引所有末影人的仇恨。                                     | Subtle | 120/600                 | ✅ | ✅末影人头     |
| 沉重（Heaviness）  | 重力增加（跳跃高度降低）。增加掉落伤害。水中会下沉。水不再能完全消除掉落伤害。使用鞘翅时会下落更快。 | Adaptable | 60/600                  | ▶️水、鞘翅 | ✅铁砧       |
| 没有马（Horseless） | 周围的马会爆炸。                                           | Subtle | 5/300（骑马：60）      | ✅ | ✅禁止马      |
| 时运不济（Misfortune） | 你挖矿掉的东西变少。                                       | Subtle | /                               | ❌难以实现 | ✅禁止钻石     |
| 压迫（Oppressed）  | 你必须低着头，视线和竖直方向夹角不能超过45度。             | Adaptable | 30/600                  | ✅       | ✅拴绳       |
| 冷冷冷（ReallyCold） | 冻住（类似于细雪）。无法跳跃。                              | Damage | 6/120                 | ✅       | ✅细雪桶      |
| 热热热（ReallyHot） | 燃烧。                                                     | Damage | 3/120                  | ✅       | ✅岩浆桶      |
| 复读（Repeater）   | 你会重复上一个人说的话。                                   | Joke | 120/600                 | ✅ | ✅红石中继器    |
| 分裂（Schizophrenia） | 你的视角原点和模型渲染位置向东平移2。                      | SevereHandicap | 60/1200                 | ❌难以实现     | ✅裂缝       |
| 脚滑（Slippy）  | 摩擦力和加速度减小（类似于在冰面上走）。                   | Adaptable | 60/600                  |   ✅     | ✅冰块       |
| 旋转（Spin）       | 你会一直逆时针旋转。                                       | SevereHandicap | 30/600                  | ✅ | ✅🌀       |
| 交换（Swapping hands） | 每隔20t交换主副手物品。                                    | SevereHandicap | 30/600              | ✅ | ✅✋↔️🤚    |
| 靶子（Targeted）   | 吸引周围所有弹射物，包括你自己的。                         | Subtle | 120/600                 | ✅ | ✅标靶       |
| 失声（Voiceless） | 你无法发出聊天。                                           | Joke | 120/600                 | ✅ | ✅幽匿尖啸体    |
| 易伤（Vulnerable） | 你受到的伤害增加50%。                        | Subtle | 120/600                 | ✅ | ✅💔       |
| 旧世界的记忆（Memories of the old world / WaterFilling） | 持续在半径4空气中生成水源。破坏附近的门和类似的东西。移除潮涌能量。 | Damage, Adaptable, Terrachanging | 触水时间÷5/15（仅触水时间>75） | ▶️(存档) | ✅水（方块）    |
| 我的包呢？（Where is my bag? / DisableInventory） | 无法打开物品栏。（类似于下界传送门里。）                   | SevereHandicap | 15/150                  | ✅       | 禁止收纳袋    |
| 失眠（Insomnia / DisableSleeping） | 你不能与床交互。幻翼会在露天时生成。                       | Handicap | 120/600                 | ▶️幻翼 | 禁止床      |
| 凝视（Gaze / EndermanHostile） | 吸引所有末影人的仇恨。                                     | Subtle | 120/600                 | ✅ | 末影人头     |
| 沉重（Heaviness）  | 重力增加（跳跃高度降低）。增加掉落伤害。水中会下沉。水不再能完全消除掉落伤害。使用鞘翅时会下落更快。 | Adaptable | 60/600                  | ▶️水、鞘翅 | 铁砧       |
| 没有马（Horseless） | 周围的马会爆炸。                                           | Subtle | 5/300（骑马：60）      | ✅ | 禁止马      |
| 时运不济（Misfortune） | 你挖矿掉的东西变少。                                       | Subtle | /                               | ❌难以实现 | 禁止钻石     |
| 压迫（Oppressed）  | 你必须低着头，视线和竖直方向夹角不能超过45度。             | Adaptable | 30/600                  | ✅       | 拴绳       |
| 冷冷冷（ReallyCold） | 冻住（类似于细雪）。无法跳跃。                              | Damage | 6/120                 | ✅       | 细雪桶      |
| 热热热（ReallyHot） | 燃烧。                                                     | Damage | 3/120                  | ✅       | 岩浆桶      |
| 复读（Repeater）   | 你会重复上一个人说的话。                                   | Joke | 120/600                 | ✅ | 红石中继器    |
| 分裂（Schizophrenia） | 你的视角原点和模型渲染位置向东平移2。                      | SevereHandicap | 60/1200                 | ❌难以实现 | 裂缝       |
| 脚滑（Slippy）  | 摩擦力和加速度减小（类似于在冰面上走）。                   | Adaptable | 60/600                  | ✅ | 冰块       |
| 旋转（Spin）       | 你会一直逆时针旋转。                                       | SevereHandicap | 30/600                  | ✅ | 🌀       |
| 交换（Swapping hands） | 每隔20t交换主副手物品。                                    | SevereHandicap | 30/600              | ✅ | ✋↔️🤚    |
| 靶子（Targeted）   | 吸引周围所有弹射物，包括你自己的。                         | Subtle | 120/600                 | ✅ | 标靶       |
| 失声（Voiceless） | 你无法发出聊天。                                           | Joke | 120/600                 | ✅ | 幽匿尖啸体    |
| 易伤（Vulnerable） | 你受到的伤害增加50%。                        | Subtle | 120/600                 | ✅ | 💔       |
| 旧世界的记忆（Memories of the old world / WaterFilling） | 持续在半径4空气中生成水源。破坏附近的门和类似的东西。移除潮涌能量。 | Damage, Adaptable, Terrachanging | 触水时间÷5/15（仅触水时间>75） | ▶️(存档) | 水（方块）    |
| 失明（Blindness）  | 原版                                                       | Adaptable | 60/600               | /        | /        |
| 黑暗（Darkness）   | 原版                                                       | Adaptable | 60/600               | /        | /        |
| 饥饿（Hunger）  | 原版                                                       | Subtle | 60/600           | /        | /        |
| 寄生（Infested）   | 原版                                                       | Subtle | 120/600                 | /        | /        |
| 漂浮（Levitation） | 原版                                                       | Damage, SevereHandicap | 10/200             | /        | /        |
| 挖掘疲劳（Mining fatigue） | 原版                                                       | Adaptable | 60/600                  | /        | /        |
| 反胃（Nausea）     | 原版                                                       | Joke | 10/200               | /        | /        |
| 缓慢（Slowness）   | 原版                                                       | Adaptable | 60/600                  | /        | /        |
| 虚弱（Weakness）   | 原版                                                       | Subtle | 120/600                 | /        | /        |
| 凋零（Wither）     | 原版                                                       | Damage | 10/200                  | /        | /        |

更多可能在未来添加的：

- 困倦：你会走在路上突然睡着。
- 驱动：你会一直往前走。
- 不稳定：受伤后，你会掉落随机物品。（单个物品掉落概率是1%左右，与背包中物品数量无关。）
- ：（仅在主世界）将你的个人重生点设为当前位置。
- ：打开战利品宝箱时，宝箱里的所有物品变为毒马铃薯。（附魔会保留。）

package potatowolfie.arlo_the_little_guy.datagen;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import potatowolfie.arlo_the_little_guy.item.ModItems;

public class ModLootTableModifier {

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, wrapperLookup) -> {
            Identifier id = key.identifier();

            if (id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/village/village_cartographer")) ||
                    id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/village/village_desert_house")) ||
                    id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/village/village_fisher")) ||
                    id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/village/village_plains_house")) ||
                    id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/village/village_savanna_house")) ||
                    id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/village/village_shepherd")) ||
                    id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/village/village_snowy_house")) ||
                    id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/village/village_taiga_house")) ||
                    id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/village/village_temple"))) {

                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition.randomChance(0.07f))
                        .add(LootItem.lootTableItem(ModItems.BOWLER_HAT)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))));
                tableBuilder.withPool(poolBuilder);
            }

            if (id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/desert_pyramid"))) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition.randomChance(0.08f))
                        .add(LootItem.lootTableItem(ModItems.COWBOY_HAT)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))));
                tableBuilder.withPool(poolBuilder);
            }

            if (id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/bastion_treasure"))) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition.randomChance(0.06f))
                        .add(LootItem.lootTableItem(ModItems.CROWN)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))));
                tableBuilder.withPool(poolBuilder);
            }

            if (id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/village/village_cartographer")) ||
                    id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/village/village_desert_house")) ||
                    id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/village/village_fisher")) ||
                    id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/village/village_plains_house")) ||
                    id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/village/village_savanna_house")) ||
                    id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/village/village_shepherd")) ||
                    id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/village/village_snowy_house")) ||
                    id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/village/village_taiga_house")) ||
                    id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/village/village_temple"))) {

                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition.randomChance(0.05f))
                        .add(LootItem.lootTableItem(ModItems.STRAW_HAT)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))));
                tableBuilder.withPool(poolBuilder);
            }

            if (id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/village/village_desert_house"))) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition.randomChance(0.10f))
                        .add(LootItem.lootTableItem(ModItems.SUN_HAT)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))));
                tableBuilder.withPool(poolBuilder);
            }

            if (id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/village/village_plains_house")) ||
                    id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/village/village_snowy_house")) ||
                    id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/woodland_mansion"))) {

                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition.randomChance(0.07f))
                        .add(LootItem.lootTableItem(ModItems.TOP_HAT)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))));
                tableBuilder.withPool(poolBuilder);
            }

            if (id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/shipwreck_treasure")) ||
                    id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/shipwreck_map")) ||
                    id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/shipwreck_supply"))) {

                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition.randomChance(0.12f))
                        .add(LootItem.lootTableItem(ModItems.TRICORN)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))));
                tableBuilder.withPool(poolBuilder);
            }

            if (id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/underwater_ruin_big"))) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition.randomChance(0.06f))
                        .add(LootItem.lootTableItem(ModItems.PRISMARINE_PIPIS)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))));
                tableBuilder.withPool(poolBuilder);
            }
        });
    }
}
package potatowolfie.arlo_the_little_guy.datagen;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;
import potatowolfie.arlo_the_little_guy.item.ModItems;

public class ModLootTableModifier {

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, wrapperLookup) -> {
            Identifier id = key.getValue();

            if (id.equals(Identifier.of("minecraft", "chests/village/village_cartographer")) ||
                    id.equals(Identifier.of("minecraft", "chests/village/village_desert_house")) ||
                    id.equals(Identifier.of("minecraft", "chests/village/village_fisher")) ||
                    id.equals(Identifier.of("minecraft", "chests/village/village_plains_house")) ||
                    id.equals(Identifier.of("minecraft", "chests/village/village_savanna_house")) ||
                    id.equals(Identifier.of("minecraft", "chests/village/village_shepherd")) ||
                    id.equals(Identifier.of("minecraft", "chests/village/village_snowy_house")) ||
                    id.equals(Identifier.of("minecraft", "chests/village/village_taiga_house")) ||
                    id.equals(Identifier.of("minecraft", "chests/village/village_temple"))) {

                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(net.minecraft.loot.condition.RandomChanceLootCondition.builder(0.07f))
                        .with(ItemEntry.builder(ModItems.BOWLER_HAT)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))));
                tableBuilder.pool(poolBuilder);
            }

            if (id.equals(Identifier.of("minecraft", "chests/desert_pyramid"))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(net.minecraft.loot.condition.RandomChanceLootCondition.builder(0.08f))
                        .with(ItemEntry.builder(ModItems.COWBOY_HAT)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))));
                tableBuilder.pool(poolBuilder);
            }

            if (id.equals(Identifier.of("minecraft", "chests/bastion_treasure"))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(net.minecraft.loot.condition.RandomChanceLootCondition.builder(0.06f))
                        .with(ItemEntry.builder(ModItems.CROWN)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))));
                tableBuilder.pool(poolBuilder);
            }

            if (id.equals(Identifier.of("minecraft", "chests/village/village_cartographer")) ||
                    id.equals(Identifier.of("minecraft", "chests/village/village_desert_house")) ||
                    id.equals(Identifier.of("minecraft", "chests/village/village_fisher")) ||
                    id.equals(Identifier.of("minecraft", "chests/village/village_plains_house")) ||
                    id.equals(Identifier.of("minecraft", "chests/village/village_savanna_house")) ||
                    id.equals(Identifier.of("minecraft", "chests/village/village_shepherd")) ||
                    id.equals(Identifier.of("minecraft", "chests/village/village_snowy_house")) ||
                    id.equals(Identifier.of("minecraft", "chests/village/village_taiga_house")) ||
                    id.equals(Identifier.of("minecraft", "chests/village/village_temple"))) {

                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(net.minecraft.loot.condition.RandomChanceLootCondition.builder(0.05f))
                        .with(ItemEntry.builder(ModItems.STRAW_HAT)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))));
                tableBuilder.pool(poolBuilder);
            }

            if (id.equals(Identifier.of("minecraft", "chests/village/village_desert_house"))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(net.minecraft.loot.condition.RandomChanceLootCondition.builder(0.10f))
                        .with(ItemEntry.builder(ModItems.SUN_HAT)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))));
                tableBuilder.pool(poolBuilder);
            }

            if (id.equals(Identifier.of("minecraft", "chests/village/village_plains_house")) ||
                    id.equals(Identifier.of("minecraft", "chests/village/village_snowy_house")) ||
                    id.equals(Identifier.of("minecraft", "chests/woodland_mansion"))) {

                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(net.minecraft.loot.condition.RandomChanceLootCondition.builder(0.07f))
                        .with(ItemEntry.builder(ModItems.TOP_HAT)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))));
                tableBuilder.pool(poolBuilder);
            }

            if (id.equals(Identifier.of("minecraft", "chests/shipwreck_treasure")) ||
                    id.equals(Identifier.of("minecraft", "chests/shipwreck_map")) ||
                    id.equals(Identifier.of("minecraft", "chests/shipwreck_supply"))) {

                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(net.minecraft.loot.condition.RandomChanceLootCondition.builder(0.12f))
                        .with(ItemEntry.builder(ModItems.TRICORN)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))));
                tableBuilder.pool(poolBuilder);
            }
        });
    }
}
package potatowolfie.arlo_the_little_guy.block;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CactusBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;
import potatowolfie.arlo_the_little_guy.block.custom.MiniCactusBlock;

import static net.minecraft.block.Blocks.*;

public class ModBlocks {

    public static final Block MINI_CACTUS = registerBlock("mini_cactus",
            new MiniCactusBlock(AbstractBlock.Settings.copy(Blocks.CACTUS)
                    .registryKey(createBlockRegistryKey("mini_cactus"))));

    public static final Block CHISELED_ARLO_SANDSTONE = registerBlock("chiseled_arlo_sandstone",
            new Block(AbstractBlock.Settings.copy(Blocks.CHISELED_SANDSTONE)
                    .registryKey(createBlockRegistryKey("chiseled_arlo_sandstone"))));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, createBlockRegistryKey(name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ArloTheLittleGuy.MOD_ID, name));
        Registry.register(Registries.ITEM, itemKey,
                new BlockItem(block, new Item.Settings()
                        .useBlockPrefixedTranslationKey()
                        .registryKey(itemKey)));
    }

    private static RegistryKey<Block> createBlockRegistryKey(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(ArloTheLittleGuy.MOD_ID, name));
    }

    private static void customNaturalBlocks(FabricItemGroupEntries entries) {
        entries.addAfter(CACTUS, MINI_CACTUS);
    }

    private static void customBuildingBlocks(FabricItemGroupEntries entries) {
        entries.addAfter(CHISELED_SANDSTONE, CHISELED_ARLO_SANDSTONE);
    }

    public static void registerModBlocks () {
        ArloTheLittleGuy.LOGGER.info("Registering Mod Blocks for " + ArloTheLittleGuy.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(ModBlocks::customNaturalBlocks);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(ModBlocks::customBuildingBlocks);
    }
}

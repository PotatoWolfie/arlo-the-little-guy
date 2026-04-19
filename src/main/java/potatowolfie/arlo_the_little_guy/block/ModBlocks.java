package potatowolfie.arlo_the_little_guy.block;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;
import potatowolfie.arlo_the_little_guy.block.custom.MiniCactusBlock;

import java.util.List;

import static net.minecraft.world.level.block.Blocks.CACTUS;
import static net.minecraft.world.level.block.Blocks.CHISELED_SANDSTONE;

public class ModBlocks {

    public static final Block MINI_CACTUS = registerBlock("mini_cactus",
            new MiniCactusBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CACTUS)
                    .setId(createBlockRegistryKey("mini_cactus"))));

    public static final Block CHISELED_ARLO_SANDSTONE = registerBlock("chiseled_arlo_sandstone",
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_SANDSTONE)
                    .setId(createBlockRegistryKey("chiseled_arlo_sandstone"))));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK, createBlockRegistryKey(name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, name));
        Registry.register(BuiltInRegistries.ITEM, itemKey,
                new BlockItem(block, new Item.Properties()
                        .useBlockDescriptionPrefix()
                        .setId(itemKey)));
    }

    private static ResourceKey<Block> createBlockRegistryKey(String name) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, name));
    }

    public static void registerModBlocks () {
        ArloTheLittleGuy.LOGGER.info("Registering Mod Blocks for " + ArloTheLittleGuy.MOD_ID);

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS)
                .register(output -> {
                    output.insertAfter(CACTUS, List.of(
                            new ItemStack(MINI_CACTUS)
                    ), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                });
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS)
                .register(output -> {
                    output.insertAfter(CHISELED_SANDSTONE, List.of(
                            new ItemStack(CHISELED_ARLO_SANDSTONE)
                    ), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                });
    }
}

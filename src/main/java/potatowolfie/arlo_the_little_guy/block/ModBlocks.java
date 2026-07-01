package potatowolfie.arlo_the_little_guy.block;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;
import potatowolfie.arlo_the_little_guy.block.custom.*;

import java.util.List;

import static net.minecraft.world.level.block.Blocks.*;

public class ModBlocks {

    public static final Block MINI_CACTUS = registerBlock("mini_cactus",
            new MiniCactusBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CACTUS)
                    .setId(createBlockRegistryKey("mini_cactus"))));

    public static final Block CHISELED_ARLO_SANDSTONE = registerBlock("chiseled_arlo_sandstone",
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_SANDSTONE)
                    .setId(createBlockRegistryKey("chiseled_arlo_sandstone"))));

    public static final Block CACTUS_WALLPAPER = registerBlock("cactus_wallpaper",
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
                    .mapColor(MapColor.COLOR_YELLOW)
                    .sound(SoundType.WOOD)
                    .setId(createBlockRegistryKey("cactus_wallpaper"))));
    public static final Block CACTUS_WALLPAPER_STAIRS = registerBlock("cactus_wallpaper_stairs",
            new StairBlock(ModBlocks.CACTUS_WALLPAPER.defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(CACTUS_WALLPAPER)
                            .setId(createBlockRegistryKey("cactus_wallpaper_stairs"))));
    public static final Block CACTUS_WALLPAPER_SLAB = registerBlock("cactus_wallpaper_slab",
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(CACTUS_WALLPAPER)
                    .setId(createBlockRegistryKey("cactus_wallpaper_slab"))));

    public static final Block TILED_WALL = registerBlock("tiled_wall",
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK)
                    .mapColor(MapColor.QUARTZ)
                    .setId(createBlockRegistryKey("tiled_wall"))));
    public static final Block TILED_WALL_STAIRS = registerBlock("tiled_wall_stairs",
            new StairBlock(ModBlocks.TILED_WALL.defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(TILED_WALL)
                            .setId(createBlockRegistryKey("tiled_wall_stairs"))));
    public static final Block TILED_WALL_SLAB = registerBlock("tiled_wall_slab",
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(TILED_WALL)
                    .setId(createBlockRegistryKey("tiled_wall_slab"))));

    public static final Block BLUE_TILED_WALL = registerBlock("blue_tiled_wall",
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK)
                    .mapColor(MapColor.COLOR_BLUE)
                    .setId(createBlockRegistryKey("tiled_wall"))));
    public static final Block BLUE_TILED_WALL_STAIRS = registerBlock("blue_tiled_wall_stairs",
            new StairBlock(ModBlocks.TILED_WALL.defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(TILED_WALL)
                            .setId(createBlockRegistryKey("blue_tiled_wall_stairs"))));
    public static final Block BLUE_TILED_WALL_SLAB = registerBlock("blue_tiled_wall_slab",
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(TILED_WALL)
                    .setId(createBlockRegistryKey("blue_tiled_wall_slab"))));

    public static final Block STRIPED_TILED_WALL = registerBlock("striped_tiled_wall",
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK)
                    .mapColor(MapColor.QUARTZ)
                    .setId(createBlockRegistryKey("tiled_wall"))));

    public static final Block NO_CLIP_CACTUS_WALLPAPER = registerBlock("no_clip_cactus_wallpaper",
            new NoClipDecorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
                    .mapColor(MapColor.COLOR_YELLOW)
                    .sound(SoundType.WOOD)
                    .noCollision()
                    .forceSolidOn()
                    .setId(createBlockRegistryKey("no_clip_cactus_wallpaper"))));

    public static final Block CACTUS_WALLPAPER_SHEET = registerBlock("cactus_wallpaper_sheet",
            new CactusWallpaperSheetBlock(BlockBehaviour.Properties.ofFullCopy(CACTUS_WALLPAPER)
                    .setId(createBlockRegistryKey("cactus_wallpaper_sheet"))));

    public static final Block MOIST_WOOL = registerBlock("moist_wool",
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.WOOL.yellow())
                    .mapColor(MapColor.TERRACOTTA_YELLOW)
                    .sound(SoundType.WOOL)
                    .setId(createBlockRegistryKey("moist_wool"))));
    public static final Block MOIST_WOOL_STAIRS = registerBlock("moist_wool_stairs",
            new StairBlock(ModBlocks.MOIST_WOOL.defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.WOOL.yellow())
                            .setId(createBlockRegistryKey("moist_wool_stairs"))));
    public static final Block MOIST_WOOL_SLAB = registerBlock("moist_wool_slab",
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WOOL.yellow())
                    .setId(createBlockRegistryKey("moist_wool_slab"))));

    public static final Block MOIST_WOOL_CARPET = registerBlock("moist_wool_carpet",
            new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(MOIST_WOOL)
                    .setId(createBlockRegistryKey("moist_wool_carpet"))));

    public static final Block NO_CLIP_MOIST_WOOL = registerBlock("no_clip_moist_wool",
            new NoClipDecorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WOOL.yellow())
                    .mapColor(MapColor.TERRACOTTA_YELLOW)
                    .sound(SoundType.WOOL)
                    .noCollision()
                    .forceSolidOn()
                    .setId(createBlockRegistryKey("no_clip_moist_wool"))));

    public static final Block CEILING_TILE = registerBlock("ceiling_tile",
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.WOOL.yellow())
                    .mapColor(MapColor.COLOR_YELLOW)
                    .sound(SoundType.NETHER_WART)
                    .setId(createBlockRegistryKey("ceiling_tile"))));

    public static final Block NO_CLIP_CEILING_TILE = registerBlock("no_clip_ceiling_tile",
            new NoClipDecorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WOOL.yellow())
                    .mapColor(MapColor.COLOR_YELLOW)
                    .sound(SoundType.NETHER_WART)
                    .noCollision()
                    .forceSolidOn()
                    .setId(createBlockRegistryKey("no_clip_ceiling_tile"))));

    public static final Block CEILING_LIGHT = registerBlock("ceiling_light",
            new CeilingLightBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.GLOWSTONE)
                            .mapColor(MapColor.COLOR_YELLOW)
                            .lightLevel((state) -> state.getValue(CeilingLightBlock.LIT) ? 15 : 0)
                            .sound(SoundType.GLASS)
                            .setId(createBlockRegistryKey("ceiling_light"))));

    public static final Block DOOR_MARKER = registerBlock("door_marker",
            new DoorMarkerBlock(BlockBehaviour.Properties.ofFullCopy(BEDROCK)
                    .setId(createBlockRegistryKey("door_marker"))));
    public static final Block POOL_ROOMS_DOOR_MARKER = registerBlock("pool_rooms_door_marker",
            new DoorMarkerBlock(BlockBehaviour.Properties.ofFullCopy(BEDROCK)
                    .setId(createBlockRegistryKey("pool_rooms_door_marker"))));
    public static final Block DECORATION_MARKER = registerBlock("decoration_marker",
            new DecorationMarkerBlock(BlockBehaviour.Properties.ofFullCopy(BEDROCK)
                    .setId(createBlockRegistryKey("decoration_marker"))));

    public static final Block NO_CLIP_SAND = registerBlock("no_clip_sand",
            new NoClipSandBlock(
                    new ColorRGBA(14406560),
                    BlockBehaviour.Properties.ofFullCopy(SAND)
                            .noCollision()
                            .forceSolidOn()
                            .setId(createBlockRegistryKey("no_clip_sand"))));

    public static final Block CASSETTE_TAPE_PLAYER = registerBlock("cassette_tape_player",
            new CassetteTapePlayerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUKEBOX)
                    .mapColor(MapColor.WOOD)
                    .sound(SoundType.WOOD)
                    .dynamicShape()
                    .setId(createBlockRegistryKey("cassette_tape_player"))));

    public static final Block STOP_SIGN = registerBlock("stop_sign",
            new StopSignBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUKEBOX)
                    .mapColor(MapColor.WOOD)
                    .sound(SoundType.WOOD)
                    .noOcclusion()
                    .dynamicShape()
                    .setId(createBlockRegistryKey("stop_sign"))));

    public static final Block CACTUS_PLAYER_HEAD = registerBlockOnly("cactus_player_head",
            new CactusPlayerHeadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PLAYER_HEAD)
                    .setId(createBlockRegistryKey("cactus_player_head"))));
    public static final Block WALL_CACTUS_PLAYER_HEAD = registerBlockOnly("wall_cactus_player_head",
            new WallCactusPlayerHeadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PLAYER_WALL_HEAD)
                    .setId(createBlockRegistryKey("wall_cactus_player_head"))));

    public static final Item CACTUS_PLAYER_HEAD_ITEM = registerStandingAndWallItem(
            "cactus_player_head", CACTUS_PLAYER_HEAD, WALL_CACTUS_PLAYER_HEAD);

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK, createBlockRegistryKey(name), block);
    }

    private static Block registerBlockOnly(String name, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, createBlockRegistryKey(name), block);
    }

    private static Item registerStandingAndWallItem(String name, Block floorBlock, Block wallBlock) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, name));
        return Registry.register(BuiltInRegistries.ITEM, itemKey,
                new StandingAndWallBlockItem(floorBlock, wallBlock, Direction.DOWN,
                        new Item.Properties()
                                .useBlockDescriptionPrefix()
                                .setId(itemKey)));
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

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS)
                .register(output -> {
                    output.insertAfter(SAND, List.of(
                            new ItemStack(NO_CLIP_SAND)
                    ), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS)
                .register(output -> {
                    output.insertAfter(CHISELED_SANDSTONE, List.of(
                            new ItemStack(CHISELED_ARLO_SANDSTONE)
                    ), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS)
                .register(output -> {
                    output.insertBefore(END_ROD, List.of(
                            new ItemStack(STOP_SIGN)
                    ), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS)
                .register(output -> {
                    output.insertAfter(PLAYER_HEAD, List.of(
                            new ItemStack(CACTUS_PLAYER_HEAD)
                    ), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS)
                .register(output -> {
                    output.insertAfter(WARPED_BUTTON, List.of(
                            new ItemStack(CACTUS_WALLPAPER),
                            new ItemStack(CACTUS_WALLPAPER_STAIRS),
                            new ItemStack(CACTUS_WALLPAPER_SLAB),
                            new ItemStack(CACTUS_WALLPAPER_SHEET),
                            new ItemStack(TILED_WALL),
                            new ItemStack(TILED_WALL_STAIRS),
                            new ItemStack(TILED_WALL_SLAB),
                            new ItemStack(BLUE_TILED_WALL),
                            new ItemStack(BLUE_TILED_WALL_STAIRS),
                            new ItemStack(BLUE_TILED_WALL_SLAB),
                            new ItemStack(STRIPED_TILED_WALL),
                            new ItemStack(MOIST_WOOL),
                            new ItemStack(MOIST_WOOL_STAIRS),
                            new ItemStack(MOIST_WOOL_SLAB),
                            new ItemStack(MOIST_WOOL_CARPET),
                            new ItemStack(CEILING_TILE)
                    ), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                });
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS)
                .register(output -> {
                    output.insertAfter(PEARLESCENT_FROGLIGHT, List.of(
                            new ItemStack(CEILING_LIGHT)
                    ), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS)
                .register(output -> {
                    output.insertAfter(JUKEBOX, List.of(
                            new ItemStack(CASSETTE_TAPE_PLAYER)
                    ), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.OP_BLOCKS)
                .register(output -> {
                    output.insertAfter(STRUCTURE_BLOCK, List.of(
                            new ItemStack(DOOR_MARKER),
                            new ItemStack(POOL_ROOMS_DOOR_MARKER),
                            new ItemStack(DECORATION_MARKER)
                    ), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.OP_BLOCKS)
                .register(output -> {
                    output.insertAfter(TEST_BLOCK, List.of(
                            new ItemStack(NO_CLIP_CACTUS_WALLPAPER),
                            new ItemStack(NO_CLIP_MOIST_WOOL),
                            new ItemStack(NO_CLIP_CEILING_TILE)
                    ), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                });
    }
}
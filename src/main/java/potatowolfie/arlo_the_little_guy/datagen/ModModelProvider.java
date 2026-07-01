package potatowolfie.arlo_the_little_guy.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.model.*;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import potatowolfie.arlo_the_little_guy.block.ModBlocks;
import potatowolfie.arlo_the_little_guy.component.ModDataComponentTypes;
import potatowolfie.arlo_the_little_guy.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        blockStateModelGenerator.createTrivialCube(ModBlocks.CEILING_TILE);

        BlockModelGenerators.BlockFamilyProvider cactusWallpaperTexturePool = blockStateModelGenerator.family(ModBlocks.CACTUS_WALLPAPER);
        cactusWallpaperTexturePool.stairs(ModBlocks.CACTUS_WALLPAPER_STAIRS);
        cactusWallpaperTexturePool.slab(ModBlocks.CACTUS_WALLPAPER_SLAB);

        BlockModelGenerators.BlockFamilyProvider tiledWallTexturePool = blockStateModelGenerator.family(ModBlocks.TILED_WALL);
        tiledWallTexturePool.stairs(ModBlocks.TILED_WALL_STAIRS);
        tiledWallTexturePool.slab(ModBlocks.TILED_WALL_SLAB);

        BlockModelGenerators.BlockFamilyProvider blueTiledWallTexturePool = blockStateModelGenerator.family(ModBlocks.BLUE_TILED_WALL);
        blueTiledWallTexturePool.stairs(ModBlocks.BLUE_TILED_WALL_STAIRS);
        blueTiledWallTexturePool.slab(ModBlocks.BLUE_TILED_WALL_SLAB);

        BlockModelGenerators.BlockFamilyProvider moistWoolTexturePool = blockStateModelGenerator.family(ModBlocks.MOIST_WOOL);
        moistWoolTexturePool.stairs(ModBlocks.MOIST_WOOL_STAIRS);
        moistWoolTexturePool.slab(ModBlocks.MOIST_WOOL_SLAB);

        createCarpetBlock(
                blockStateModelGenerator,
                ModBlocks.MOIST_WOOL,
                ModBlocks.MOIST_WOOL_CARPET
        );

        blockStateModelGenerator.createMultifaceBlockStates(ModBlocks.CACTUS_WALLPAPER_SHEET);


        blockStateModelGenerator.createTrivialCube(ModBlocks.NO_CLIP_CACTUS_WALLPAPER);
        blockStateModelGenerator.createTrivialCube(ModBlocks.NO_CLIP_MOIST_WOOL);
        blockStateModelGenerator.createTrivialCube(ModBlocks.NO_CLIP_CEILING_TILE);
        blockStateModelGenerator.createTrivialCube(ModBlocks.NO_CLIP_SAND);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        registerHeadItem(itemModelGenerator, ModItems.BOWLER_HAT);
        registerHeadItem(itemModelGenerator, ModItems.TRICORN);
        registerHeadItem(itemModelGenerator, ModItems.STRAW_HAT);
        registerHeadItem(itemModelGenerator, ModItems.COWBOY_HAT);
        registerHeadItem(itemModelGenerator, ModItems.TOP_HAT);
        registerHeadItem(itemModelGenerator, ModItems.SUN_HAT);
        registerHeadItem(itemModelGenerator, ModItems.CROWN);
        registerHeadItem(itemModelGenerator, ModItems.PRISMARINE_PIPIS);

        registerFlashlightItem(itemModelGenerator);

        itemModelGenerator.generateFlatItem(ModItems.CACTUS_BATTERY, ModelTemplates.FLAT_ITEM);

        itemModelGenerator.generateFlatItem(ModItems.CASSETTE_TAPE_1, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.CASSETTE_TAPE_2, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.CASSETTE_TAPE_3, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.CASSETTE_TAPE_4, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.CASSETTE_TAPE_5, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.CASSETTE_TAPE_6, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.CASSETTE_TAPE_7, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.CASSETTE_TAPE_QUESTION, ModelTemplates.FLAT_ITEM);
    }

    public static void createCarpetBlock(
            BlockModelGenerators generator,
            Block textureSource,
            Block carpet
    ) {
        MultiVariant model = BlockModelGenerators.plainVariant(
                TexturedModel.CARPET
                        .get(textureSource)
                        .create(carpet, generator.modelOutput)
        );

        generator.blockStateOutput.accept(
                BlockModelGenerators.createSimpleBlock(carpet, model)
        );
    }

    private void registerFlashlightItem(ItemModelGenerators generator) {
        Identifier baseModelId = ModelLocationUtils.getModelLocation(ModItems.CACTUS_FLASHLIGHT);
        Identifier onModelId = baseModelId.withSuffix("_on");

        generator.modelOutput.accept(baseModelId, () -> {
            JsonObject json = new JsonObject();
            json.addProperty("parent", "minecraft:item/generated");

            JsonObject textures = new JsonObject();
            textures.addProperty("layer0", baseModelId.toString());
            json.add("textures", textures);

            return json;
        });

        generator.modelOutput.accept(onModelId, () -> {
            JsonObject json = new JsonObject();
            json.addProperty("parent", "minecraft:item/generated");

            JsonObject textures = new JsonObject();
            textures.addProperty("layer0", onModelId.toString());
            json.add("textures", textures);

            return json;
        });

        generator.itemModelOutput.accept(ModItems.CACTUS_FLASHLIGHT,
                ItemModelUtils.conditional(
                        ItemModelUtils.hasComponent(ModDataComponentTypes.FLASHLIGHT_ON),
                        ItemModelUtils.plainModel(onModelId),
                        ItemModelUtils.plainModel(baseModelId)
                )
        );
    }

    private void registerHeadItem(ItemModelGenerators generator, net.minecraft.world.item.Item item) {
        Identifier modelId = uploadHeadItemModel(generator, item);
        generator.itemModelOutput.accept(item, ItemModelUtils.plainModel(modelId));
    }

    private Identifier uploadHeadItemModel(ItemModelGenerators generator, net.minecraft.world.item.Item item) {
        Identifier modelId = ModelLocationUtils.getModelLocation(item);

        generator.modelOutput.accept(modelId, () -> {
            JsonObject json = new JsonObject();
            json.addProperty("parent", "minecraft:item/generated");

            JsonObject textures = new JsonObject();
            textures.addProperty("layer0", modelId.toString());
            json.add("textures", textures);

            JsonObject display = new JsonObject();
            JsonObject head = new JsonObject();

            JsonArray scale = new JsonArray();
            scale.add(0);
            scale.add(0);
            scale.add(0);
            head.add("scale", scale);

            display.add("head", head);
            json.add("display", display);

            return json;
        });

        return modelId;
    }
}
package potatowolfie.arlo_the_little_guy.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Blocks;
import potatowolfie.arlo_the_little_guy.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModLootTableGenerator extends FabricBlockLootSubProvider {
    public ModLootTableGenerator(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        dropSelf(ModBlocks.MINI_CACTUS);
        dropSelf(ModBlocks.CHISELED_ARLO_SANDSTONE);

        dropSelf(ModBlocks.CACTUS_WALLPAPER);
        dropSelf(ModBlocks.CACTUS_WALLPAPER_STAIRS);

        dropSelf(ModBlocks.NO_CLIP_CACTUS_WALLPAPER);

        dropSelf(ModBlocks.MOIST_WOOL);
        dropSelf(ModBlocks.MOIST_WOOL_STAIRS);

        dropSelf(ModBlocks.NO_CLIP_MOIST_WOOL);

        dropSelf(ModBlocks.CEILING_TILE);
        dropSelf(ModBlocks.NO_CLIP_CEILING_TILE);
        dropSelf(ModBlocks.CEILING_LIGHT);
        dropOther(ModBlocks.NO_CLIP_SAND, Blocks.SAND);

        add(ModBlocks.CACTUS_WALLPAPER_SLAB, block -> createSlabItemTable(block));
        add(ModBlocks.MOIST_WOOL_SLAB, block -> createSlabItemTable(block));
    }
}
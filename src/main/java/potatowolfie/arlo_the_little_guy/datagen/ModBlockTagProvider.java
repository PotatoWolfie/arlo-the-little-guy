package potatowolfie.arlo_the_little_guy.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import potatowolfie.arlo_the_little_guy.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
    public ModBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        builder(BlockTags.HAPPY_GHAST_AVOIDS)
                .add(BuiltInRegistries.BLOCK.wrapAsHolder(ModBlocks.MINI_CACTUS).unwrapKey().orElseThrow());

        builder(BlockTags.ENDERMAN_HOLDABLE)
                .add(BuiltInRegistries.BLOCK.wrapAsHolder(ModBlocks.MINI_CACTUS).unwrapKey().orElseThrow());

        builder(BlockTags.SAND)
                .add(BuiltInRegistries.BLOCK.wrapAsHolder(ModBlocks.NO_CLIP_SAND).unwrapKey().orElseThrow());
    }
}
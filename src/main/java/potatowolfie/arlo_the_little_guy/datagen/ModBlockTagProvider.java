package potatowolfie.arlo_the_little_guy.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import potatowolfie.arlo_the_little_guy.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        valueLookupBuilder(BlockTags.HAPPY_GHAST_AVOIDS)
                .add(ModBlocks.MINI_CACTUS
                );
        valueLookupBuilder(BlockTags.ENDERMAN_HOLDABLE)
                .add(ModBlocks.MINI_CACTUS
                );
    }
}
package potatowolfie.arlo_the_little_guy.world;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import potatowolfie.arlo_the_little_guy.world.feature.ModPlacedFeatures;

public class ModBiomeModifications {
    public static void registerBiomeModifications() {
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.DESERT),
                GenerationStep.Feature.VEGETAL_DECORATION,
                ModPlacedFeatures.PATCH_MINI_CACTUS_DESERT_PLACED
        );
    }
}
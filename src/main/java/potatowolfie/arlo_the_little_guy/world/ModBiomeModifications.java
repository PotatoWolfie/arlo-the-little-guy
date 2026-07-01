package potatowolfie.arlo_the_little_guy.world;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import potatowolfie.arlo_the_little_guy.world.feature.ModPlacedFeatures;

public class ModBiomeModifications {
    public static void registerBiomeModifications() {
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.DESERT),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ModPlacedFeatures.PATCH_MINI_CACTUS_DESERT_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.DESERT),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ModPlacedFeatures.ARLROOMS_ENTRANCE_PLACED
        );
    }
}
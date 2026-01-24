package potatowolfie.arlo_the_little_guy.world.feature;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BlockFilterPlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;
import potatowolfie.arlo_the_little_guy.block.ModBlocks;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_MINI_CACTUS = registerKey("patch_mini_cactus");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        register(context, PATCH_MINI_CACTUS, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(10,
                        PlacedFeatures.createEntry(ModFeatures.ARLO_FEATURE,
                                new SimpleBlockFeatureConfig(
                                        BlockStateProvider.of(ModBlocks.MINI_CACTUS)
                                ),
                                BlockFilterPlacementModifier.of(
                                        BlockPredicate.bothOf(
                                                BlockPredicate.IS_AIR,
                                                BlockPredicate.wouldSurvive(ModBlocks.MINI_CACTUS.getDefaultState(), BlockPos.ORIGIN)
                                        )
                                )
                        )
                )
        );
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(ArloTheLittleGuy.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(
            Registerable<ConfiguredFeature<?, ?>> context,
            RegistryKey<ConfiguredFeature<?, ?>> key,
            F feature,
            FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
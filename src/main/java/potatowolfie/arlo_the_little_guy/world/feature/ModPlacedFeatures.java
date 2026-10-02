package potatowolfie.arlo_the_little_guy.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.*;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> PATCH_MINI_CACTUS_DESERT_PLACED = registerKey("patch_mini_cactus_desert");
    public static final ResourceKey<PlacedFeature> ARLROOMS_ENTRANCE_PLACED = registerKey("arlrooms_entrance");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<Feature> configuredFeatures = context.lookup(Registries.FEATURE);

        register(context, PATCH_MINI_CACTUS_DESERT_PLACED,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.MINI_CACTUS),
                RarityFilter.onAverageOnceEvery(8),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING),
                CountPlacement.of(10),
                OffsetPlacement.ofTriangle(7, 3),
                BiomeFilter.biome());

        register(context, ARLROOMS_ENTRANCE_PLACED,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.ARLROOMS_ENTRANCE),
                RarityFilter.onAverageOnceEvery(75),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING),
                BiomeFilter.biome());
    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
                                 Holder<Feature> configuration,
                                 PlacementModifier... modifiers) {
        context.register(key, new PlacedFeature(configuration, List.of(modifiers)));
    }
}
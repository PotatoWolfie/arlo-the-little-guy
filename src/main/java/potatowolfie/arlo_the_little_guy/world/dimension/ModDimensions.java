package potatowolfie.arlo_the_little_guy.world.dimension;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TimelineTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.attribute.*;
import net.minecraft.world.level.CardinalLighting;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;

import java.util.List;
import java.util.Optional;

public class ModDimensions {
    public static final ResourceKey<LevelStem> ARLROOMS_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, "arlrooms"));
    public static final ResourceKey<Level> ARLROOMS_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, "arlrooms"));
    public static final ResourceKey<DimensionType> ARLROOMS_TYPE_KEY = ResourceKey.create(Registries.DIMENSION_TYPE,
            Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, "arlrooms_type"));

    public static void bootstrapType(BootstrapContext<DimensionType> context) {
        var timelines = context.lookup(Registries.TIMELINE);
        var blocks = context.lookup(Registries.BLOCK);

        context.register(ARLROOMS_TYPE_KEY, new DimensionType(
                true,
                false,
                true,
                false,
                1.0,
                0,
                256,
                256,
                blocks.getOrThrow(BlockTags.INFINIBURN_OVERWORLD),
                1.0f,
                new DimensionType.MonsterSettings(ConstantInt.of(0), 0),
                DimensionType.Skybox.END,
                CardinalLighting.Type.DEFAULT,
                EnvironmentAttributeMap.builder()
                        .set(EnvironmentAttributes.STAR_BRIGHTNESS, 0.0f)
                        .set(EnvironmentAttributes.SKY_COLOR, 0xFFEDAC)
                        .set(EnvironmentAttributes.FOG_COLOR, 0x312609)
                        .set(EnvironmentAttributes.SUNRISE_SUNSET_COLOR, 0x312609)
                        .set(EnvironmentAttributes.FOG_START_DISTANCE, 18F)
                        .set(EnvironmentAttributes.FOG_END_DISTANCE, 48F)
                        .set(EnvironmentAttributes.CLOUD_COLOR, 0x000000)
                        .set(EnvironmentAttributes.BACKGROUND_MUSIC, BackgroundMusic.EMPTY)
                        .set(EnvironmentAttributes.AMBIENT_SOUNDS, AmbientSounds.EMPTY)
                        .set(EnvironmentAttributes.AMBIENT_LIGHT_COLOR, 0x000000)
                        .set(EnvironmentAttributes.BLOCK_LIGHT_TINT, 0xFFE67A)
                        .set(EnvironmentAttributes.BED_RULE, BedRule.CAN_SLEEP_WHEN_DARK)
                        .build(),
                timelines.getOrThrow(TimelineTags.UNIVERSAL),
                Optional.empty()));
    }

    public static void bootstrapStem(BootstrapContext<LevelStem> context) {
        var biomes = context.lookup(Registries.BIOME);
        var dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);

        var voidBiomeHolder = biomes.getOrThrow(Biomes.THE_VOID);

        FlatLevelGeneratorSettings voidSettings = new FlatLevelGeneratorSettings(
                Optional.empty(),
                voidBiomeHolder,
                List.of()
        );

        ChunkGenerator voidChunkGenerator = new FlatLevelSource(voidSettings);

        LevelStem stem = new LevelStem(dimensionTypes.getOrThrow(ModDimensions.ARLROOMS_TYPE_KEY), voidChunkGenerator);
        context.register(ARLROOMS_KEY, stem);
    }
}
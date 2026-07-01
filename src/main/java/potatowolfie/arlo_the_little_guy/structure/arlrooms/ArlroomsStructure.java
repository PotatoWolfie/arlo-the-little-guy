package potatowolfie.arlo_the_little_guy.structure.arlrooms;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.ChunkPos;
import potatowolfie.arlo_the_little_guy.structure.ModStructureTypes;

import java.util.Optional;

public class ArlroomsStructure extends Structure {

    private final Holder<StructureTemplatePool> startPool;
    private final int maxDepth;

    public static final MapCodec<ArlroomsStructure> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    settingsCodec(instance),
                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(s -> s.startPool),
                    com.mojang.serialization.Codec.intRange(1, 1).fieldOf("max_depth").forGetter(s -> s.maxDepth)
            ).apply(instance, ArlroomsStructure::new)
    );

    public ArlroomsStructure(StructureSettings settings, Holder<StructureTemplatePool> startPool, int maxDepth) {
        super(settings);
        this.startPool = startPool;
        this.maxDepth = maxDepth;
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        BlockPos start = new BlockPos(0, 76, 0);
        ChunkPos cp = context.chunkPos();

        if (start.getX() < cp.getMinBlockX() || start.getX() > cp.getMaxBlockX()
                || start.getZ() < cp.getMinBlockZ() || start.getZ() > cp.getMaxBlockZ()) {
            return Optional.empty();
        }

        return JigsawPlacement.addPieces(
                context,
                this.startPool,
                Optional.empty(),
                1,
                start,
                false,
                Optional.of(Heightmap.Types.WORLD_SURFACE_WG),
                new JigsawStructure.MaxDistance(80),
                PoolAliasLookup.EMPTY,
                JigsawStructure.DEFAULT_DIMENSION_PADDING,
                LiquidSettings.APPLY_WATERLOGGING
        );
    }

    @Override
    public StructureType<?> type() {
        return ModStructureTypes.ARLROOMS;
    }
}
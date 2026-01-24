package potatowolfie.arlo_the_little_guy.structure;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

import java.util.Optional;

public class CactusMonumentStructure extends Structure {
    public static final MapCodec<CactusMonumentStructure> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return instance.group(
                configCodecBuilder(instance)
        ).apply(instance, CactusMonumentStructure::new);
    });

    public CactusMonumentStructure(Structure.Config config) {
        super(config);
    }

    public Optional<Structure.StructurePosition> getStructurePosition(Structure.Context context) {
        return getStructurePosition(context, Heightmap.Type.WORLD_SURFACE_WG, (collector) -> {
            this.addPieces(collector, context);
        });
    }

    private void addPieces(StructurePiecesCollector collector, Structure.Context context) {
        BlockRotation blockRotation = BlockRotation.random(context.random());
        CactusMonumentGenerator.addPieces(
                context.structureTemplateManager(),
                context.chunkPos().getStartPos(),
                blockRotation,
                collector,
                context.random()
        );
    }

    public StructureType<?> getType() {
        return ModStructureTypes.CACTUS_MONUMENT;
    }
}
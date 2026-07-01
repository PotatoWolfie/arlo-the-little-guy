package potatowolfie.arlo_the_little_guy.structure.cactus_monument;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import potatowolfie.arlo_the_little_guy.structure.ModStructureTypes;

import java.util.Optional;

public class CactusMonumentStructure extends Structure {
    public static final MapCodec<CactusMonumentStructure> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return instance.group(
                settingsCodec(instance)
        ).apply(instance, CactusMonumentStructure::new);
    });

    public CactusMonumentStructure(StructureSettings config) {
        super(config);
    }

    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        return onTopOfChunkCenter(context, Heightmap.Types.WORLD_SURFACE_WG, (collector) -> {
            this.addPieces(collector, context);
        });
    }

    private void addPieces(StructurePiecesBuilder collector, GenerationContext context) {
        Rotation blockRotation = Rotation.getRandom(context.random());
        CactusMonumentGenerator.addPieces(
                context.structureTemplateManager(),
                context.chunkPos().getWorldPosition(),
                blockRotation,
                collector,
                context.random()
        );
    }

    public StructureType<?> type() {
        return ModStructureTypes.CACTUS_MONUMENT;
    }
}
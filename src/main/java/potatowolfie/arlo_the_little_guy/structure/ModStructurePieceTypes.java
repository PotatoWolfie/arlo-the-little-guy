package potatowolfie.arlo_the_little_guy.structure;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;
import potatowolfie.arlo_the_little_guy.structure.cactus_monument.CactusMonumentGenerator;

public class ModStructurePieceTypes {
    public static final StructurePieceType CACTUS_MONUMENT =
            register((StructurePieceType.StructureTemplateType) CactusMonumentGenerator.Piece::new, "cactus_monument");

    //public static final StructurePieceType ARLROOMS_POST_PROCESSOR =
    //        register(ArlroomsPostProcessorPiece::new, "arlrooms_post_processor");

    private static StructurePieceType register(StructurePieceType type, String id) {
        return Registry.register(BuiltInRegistries.STRUCTURE_PIECE,
                Identifier.fromNamespaceAndPath("arlo-the-little-guy", id), type);
    }

    public static void registerStructurePieceTypes() {
        ArloTheLittleGuy.LOGGER.info("Registering structure piece types for " + ArloTheLittleGuy.MOD_ID);
    }
}
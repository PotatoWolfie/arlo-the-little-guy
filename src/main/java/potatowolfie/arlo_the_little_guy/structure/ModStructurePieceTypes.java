package potatowolfie.arlo_the_little_guy.structure;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;

public class ModStructurePieceTypes {
    public static final StructurePieceType CACTUS_MONUMENT =
            register((StructurePieceType.ManagerAware) CactusMonumentGenerator.Piece::new, "cactus_monument");

    private static StructurePieceType register(StructurePieceType type, String id) {
        return Registry.register(Registries.STRUCTURE_PIECE,
                Identifier.of("arlo-the-little-guy", id), type);
    }

    public static void registerStructurePieceTypes() {
        ArloTheLittleGuy.LOGGER.info("Registering structure piece types for " + ArloTheLittleGuy.MOD_ID);
    }
}
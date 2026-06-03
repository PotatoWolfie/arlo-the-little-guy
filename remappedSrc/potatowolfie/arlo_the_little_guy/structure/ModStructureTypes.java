package potatowolfie.arlo_the_little_guy.structure;

import com.mojang.serialization.MapCodec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;

public class ModStructureTypes {
    public static final StructureType<CactusMonumentStructure> CACTUS_MONUMENT =
            register("cactus_monument", CactusMonumentStructure.CODEC);

    private static <S extends Structure> StructureType<S> register(
            String id,
            MapCodec<S> codec
    ) {
        return Registry.register(
                Registries.STRUCTURE_TYPE,
                Identifier.of(ArloTheLittleGuy.MOD_ID, id),
                () -> codec
        );
    }

    public static void registerStructureTypes() {
        ArloTheLittleGuy.LOGGER.info("Registering structure types for " + ArloTheLittleGuy.MOD_ID);
    }
}
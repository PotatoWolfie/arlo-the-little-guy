package potatowolfie.arlo_the_little_guy.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntityType;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;
import potatowolfie.arlo_the_little_guy.block.ModBlocks;

public class ModBlockEntities {

    public static final BlockEntityType<ArloBlockEntity> ARLO_BLOCK_ENTITY =
            Registry.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath("arlo-the-little-guy", "arlo_block_entity"),
                    FabricBlockEntityTypeBuilder.create(ArloBlockEntity::new, ModBlocks.MINI_CACTUS).build()
            );

    public static final BlockEntityType<CassetteTapePlayerBlockEntity> CASSETTE_TAPE_PLAYER =
            Registry.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath("arlo-the-little-guy", "cassette_tape_player"),
                    FabricBlockEntityTypeBuilder.create(CassetteTapePlayerBlockEntity::new, ModBlocks.CASSETTE_TAPE_PLAYER).build()
            );

    public static void registerBlockEntities() {
        ArloTheLittleGuy.LOGGER.info("Registering Block Entities for " + ArloTheLittleGuy.MOD_ID);
    }
}
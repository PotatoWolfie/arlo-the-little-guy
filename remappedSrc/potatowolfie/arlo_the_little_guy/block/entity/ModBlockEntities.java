package potatowolfie.arlo_the_little_guy.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;
import potatowolfie.arlo_the_little_guy.block.ModBlocks;

public class ModBlockEntities {

    public static final BlockEntityType<ArloBlockEntity> ARLO_BLOCK_ENTITY =
            Registry.register(
                    Registries.BLOCK_ENTITY_TYPE,
                    Identifier.of("arlo-the-little-guy", "arlo_block_entity"),
                    FabricBlockEntityTypeBuilder.create(ArloBlockEntity::new, ModBlocks.MINI_CACTUS).build()
            );

    public static void registerBlockEntities() {
        ArloTheLittleGuy.LOGGER.info("Registering Block Entities for " + ArloTheLittleGuy.MOD_ID);
    }
}
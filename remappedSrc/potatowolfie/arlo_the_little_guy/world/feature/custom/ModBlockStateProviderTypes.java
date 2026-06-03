package potatowolfie.arlo_the_little_guy.world.feature.custom;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;

public class ModBlockStateProviderTypes {
    public static final BlockStateProviderType<RandomDirectionBlockStateProvider> RANDOM_DIRECTION =
            Registry.register(
                    Registries.BLOCK_STATE_PROVIDER_TYPE,
                    Identifier.of(ArloTheLittleGuy.MOD_ID, "random_direction"),
                    new BlockStateProviderType<>(RandomDirectionBlockStateProvider.CODEC)
            );

    public static void register() {
        ArloTheLittleGuy.LOGGER.info("Registering Block State Provider Types for " + ArloTheLittleGuy.MOD_ID);
    }
}
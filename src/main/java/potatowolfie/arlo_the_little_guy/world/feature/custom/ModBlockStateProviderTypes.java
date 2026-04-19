package potatowolfie.arlo_the_little_guy.world.feature.custom;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;

public class ModBlockStateProviderTypes {
    public static final BlockStateProviderType<RandomDirectionBlockStateProvider> RANDOM_DIRECTION =
            Registry.register(
                    BuiltInRegistries.BLOCKSTATE_PROVIDER_TYPE,
                    Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, "random_direction"),
                    new BlockStateProviderType<>(RandomDirectionBlockStateProvider.CODEC)
            );

    public static void register() {
        ArloTheLittleGuy.LOGGER.info("Registering Block State Provider Types for " + ArloTheLittleGuy.MOD_ID);
    }
}
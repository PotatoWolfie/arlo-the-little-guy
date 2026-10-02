package potatowolfie.arlo_the_little_guy.world.feature.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;

public class ModBlockStateProviderTypes {
    public static final MapCodec<RandomDirectionBlockStateProvider> RANDOM_DIRECTION =
            Registry.register(BuiltInRegistries.BLOCK_STATE_PROVIDER_TYPE,
                    Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, "random_direction"),
                    RandomDirectionBlockStateProvider.CODEC);

    public static void register() {
        ArloTheLittleGuy.LOGGER.info("Registering BlockStateProvider types for " + ArloTheLittleGuy.MOD_ID);
    }
}
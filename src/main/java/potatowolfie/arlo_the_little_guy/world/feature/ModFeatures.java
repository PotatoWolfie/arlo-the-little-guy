package potatowolfie.arlo_the_little_guy.world.feature;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;
import potatowolfie.arlo_the_little_guy.world.feature.custom.ArloFeature;
import potatowolfie.arlo_the_little_guy.world.feature.custom.ArlroomsEntranceFeature;

public class ModFeatures {
    public static final MapCodec<ArloFeature> ARLO_FEATURE =
            Registry.register(BuiltInRegistries.FEATURE_TYPE,
                    Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, "arlo_feature"),
                    ArloFeature.CODEC);

    public static final MapCodec<ArlroomsEntranceFeature> ARLROOMS_ENTRANCE_FEATURE =
            Registry.register(BuiltInRegistries.FEATURE_TYPE,
                    Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, "arlrooms_entrance_feature"),
                    ArlroomsEntranceFeature.CODEC);

    public static void registerFeatures() {
        ArloTheLittleGuy.LOGGER.info("Registering Features for " + ArloTheLittleGuy.MOD_ID);
    }
}
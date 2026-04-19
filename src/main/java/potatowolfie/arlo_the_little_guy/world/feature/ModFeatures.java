package potatowolfie.arlo_the_little_guy.world.feature;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;
import potatowolfie.arlo_the_little_guy.world.feature.custom.ArloFeature;

public class ModFeatures {
    public static final Feature<SimpleBlockConfiguration> ARLO_FEATURE =
            Registry.register(
                    BuiltInRegistries.FEATURE,
                    Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, "arlo_feature"),
                    new ArloFeature(SimpleBlockConfiguration.CODEC)
            );

    public static void registerFeatures() {
        ArloTheLittleGuy.LOGGER.info("Registering Features for " + ArloTheLittleGuy.MOD_ID);
    }
}
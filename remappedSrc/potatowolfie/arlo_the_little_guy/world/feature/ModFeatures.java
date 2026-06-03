package potatowolfie.arlo_the_little_guy.world.feature;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;
import potatowolfie.arlo_the_little_guy.world.feature.custom.ArloFeature;

public class ModFeatures {
    public static final Feature<SimpleBlockFeatureConfig> ARLO_FEATURE =
            Registry.register(
                    Registries.FEATURE,
                    Identifier.of(ArloTheLittleGuy.MOD_ID, "arlo_feature"),
                    new ArloFeature(SimpleBlockFeatureConfig.CODEC)
            );

    public static void registerFeatures() {
        ArloTheLittleGuy.LOGGER.info("Registering Features for " + ArloTheLittleGuy.MOD_ID);
    }
}
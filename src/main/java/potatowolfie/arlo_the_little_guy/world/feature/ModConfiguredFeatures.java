package potatowolfie.arlo_the_little_guy.world.feature;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.Feature;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;
import potatowolfie.arlo_the_little_guy.world.feature.custom.ArloFeature;
import potatowolfie.arlo_the_little_guy.world.feature.custom.ArlroomsEntranceFeature;

public class ModConfiguredFeatures {

    public static final ResourceKey<Feature> MINI_CACTUS = registerKey("mini_cactus");
    public static final ResourceKey<Feature> ARLROOMS_ENTRANCE = registerKey("arlrooms_entrance");

    public static void bootstrap(BootstrapContext<Feature> context) {
        context.register(MINI_CACTUS, new ArloFeature());
        context.register(ARLROOMS_ENTRANCE, new ArlroomsEntranceFeature());
    }

    public static ResourceKey<Feature> registerKey(String name) {
        return ResourceKey.create(Registries.FEATURE, Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, name));
    }
}
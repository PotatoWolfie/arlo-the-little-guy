package potatowolfie.arlo_the_little_guy.entity.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;
import potatowolfie.arlo_the_little_guy.block.entity.ArloBlockEntityModel;

@Environment(EnvType.CLIENT)
public class ModEntityModelLayers {
    public static final ModelLayerLocation BOWLER_HAT = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath("arlo-the-little-guy", "bowler_hat"), "main"
    );
    public static final ModelLayerLocation TRICORN = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath("arlo-the-little-guy", "tricorn"), "main"
    );
    public static final ModelLayerLocation STRAW_HAT = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath("arlo-the-little-guy", "straw_hat"), "main"
    );
    public static final ModelLayerLocation COWBOY_HAT = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath("arlo-the-little-guy", "cowboy_hat"), "main"
    );
    public static final ModelLayerLocation TOP_HAT = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath("arlo-the-little-guy", "top_hat"), "main"
    );
    public static final ModelLayerLocation SUN_HAT = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath("arlo-the-little-guy", "sun_hat"), "main"
    );
    public static final ModelLayerLocation CROWN = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath("arlo-the-little-guy", "crown"), "main"
    );
    public static final ModelLayerLocation PIG_CROWN = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath("arlo-the-little-guy", "pig_crown"), "main"
    );
    public static final ModelLayerLocation SNOW_GOLEM_TOP_HAT = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath("arlo-the-little-guy", "snow_golem_top_hat"), "main"
    );

    public static final ModelLayerLocation ARLO = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath("arlo-the-little-guy", "arlo"), "main"
    );

    public static void registerModelLayers() {
        ModelLayerRegistry.registerModelLayer(ARLO, ArloBlockEntityModel::getTexturedModelData);
    }
}
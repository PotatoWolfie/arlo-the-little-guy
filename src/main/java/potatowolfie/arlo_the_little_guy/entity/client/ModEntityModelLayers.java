package potatowolfie.arlo_the_little_guy.entity.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import potatowolfie.arlo_the_little_guy.block.entity.ArloBlockEntityModel;

@Environment(EnvType.CLIENT)
public class ModEntityModelLayers {
    public static final EntityModelLayer BOWLER_HAT = new EntityModelLayer(
            Identifier.of("arlo-the-little-guy", "bowler_hat"), "main"
    );
    public static final EntityModelLayer TRICORN = new EntityModelLayer(
            Identifier.of("arlo-the-little-guy", "tricorn"), "main"
    );
    public static final EntityModelLayer STRAW_HAT = new EntityModelLayer(
            Identifier.of("arlo-the-little-guy", "straw_hat"), "main"
    );
    public static final EntityModelLayer COWBOY_HAT = new EntityModelLayer(
            Identifier.of("arlo-the-little-guy", "cowboy_hat"), "main"
    );
    public static final EntityModelLayer TOP_HAT = new EntityModelLayer(
            Identifier.of("arlo-the-little-guy", "top_hat"), "main"
    );
    public static final EntityModelLayer SUN_HAT = new EntityModelLayer(
            Identifier.of("arlo-the-little-guy", "sun_hat"), "main"
    );
    public static final EntityModelLayer CROWN = new EntityModelLayer(
            Identifier.of("arlo-the-little-guy", "crown"), "main"
    );
    public static final EntityModelLayer PIG_CROWN = new EntityModelLayer(
            Identifier.of("arlo-the-little-guy", "pig_crown"), "main"
    );
    public static final EntityModelLayer SNOW_GOLEM_TOP_HAT = new EntityModelLayer(
            Identifier.of("arlo-the-little-guy", "snow_golem_top_hat"), "main"
    );

    public static final EntityModelLayer ARLO = new EntityModelLayer(
            Identifier.of("arlo-the-little-guy", "arlo"), "main"
    );

    public static void registerModelLayers() {
        EntityModelLayerRegistry.registerModelLayer(ARLO, ArloBlockEntityModel::getTexturedModelData);
    }
}
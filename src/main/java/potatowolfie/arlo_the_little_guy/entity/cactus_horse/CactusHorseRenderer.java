package potatowolfie.arlo_the_little_guy.entity.cactus_horse;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.animal.equine.BabyHorseModel;
import net.minecraft.client.model.animal.equine.EquineSaddleModel;
import net.minecraft.client.model.animal.equine.HorseModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.SimpleEquipmentLayer;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;

@Environment(EnvType.CLIENT)
public final class CactusHorseRenderer extends AbstractHorseRenderer<CactusHorseEntity, CactusHorseRenderState, HorseModel> {
    private static final Identifier CACTUS_HORSE_LOCATION = Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, "textures/entity/cactus_horse/cactus_horse.png");
    private static final Identifier CACTUS_HORSE_BABY_LOCATION = Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, "textures/entity/cactus_horse/cactus_horse_baby.png");

    public CactusHorseRenderer(EntityRendererProvider.Context context) {
        super(
                context,
                new HorseModel(context.bakeLayer(ModelLayers.HORSE)),
                new BabyHorseModel(context.bakeLayer(ModelLayers.HORSE_BABY))
        );

        this.addLayer(new SimpleEquipmentLayer<>(
                this,
                context.getEquipmentRenderer(),
                EquipmentClientInfo.LayerType.HORSE_BODY,
                state -> state.bodyArmorItem,
                new HorseModel(context.bakeLayer(ModelLayers.HORSE_ARMOR)),
                null,
                2
        ));

        this.addLayer(new SimpleEquipmentLayer<>(
                this,
                context.getEquipmentRenderer(),
                EquipmentClientInfo.LayerType.HORSE_SADDLE,
                state -> state.saddle,
                new EquineSaddleModel(context.bakeLayer(ModelLayers.HORSE_SADDLE)),
                null,
                2
        ));
    }

    @Override
    public Identifier getTextureLocation(CactusHorseRenderState state) {
        return state.isBaby ? CACTUS_HORSE_BABY_LOCATION : CACTUS_HORSE_LOCATION;
    }

    @Override
    public CactusHorseRenderState createRenderState() {
        return new CactusHorseRenderState();
    }

    @Override
    public void extractRenderState(CactusHorseEntity entity, CactusHorseRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
    }
}
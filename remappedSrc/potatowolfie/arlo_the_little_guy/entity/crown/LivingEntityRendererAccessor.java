package potatowolfie.arlo_the_little_guy.entity.crown;

import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.EntityRenderState;

public interface LivingEntityRendererAccessor<S extends EntityRenderState, M extends EntityModel<? super S>> {
    void arloTheLittleGuy$addFeature(FeatureRenderer<S, M> feature);
}
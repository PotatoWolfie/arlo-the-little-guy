package potatowolfie.arlo_the_little_guy.entity.crown;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public interface LivingEntityRendererAccessor<S extends EntityRenderState, M extends EntityModel<? super S>> {
    void arloTheLittleGuy$addFeature(RenderLayer<S, M> feature);
}
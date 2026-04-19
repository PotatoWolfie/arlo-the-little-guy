package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import potatowolfie.arlo_the_little_guy.entity.crown.LivingEntityRendererAccessor;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<S extends EntityRenderState, M extends EntityModel<? super S>>
        implements LivingEntityRendererAccessor<S, M> {

    @Shadow
    protected abstract boolean addLayer(RenderLayer<S, M> feature);

    @Override
    public void arloTheLittleGuy$addFeature(RenderLayer<S, M> feature) {
        this.addLayer(feature);
    }
}
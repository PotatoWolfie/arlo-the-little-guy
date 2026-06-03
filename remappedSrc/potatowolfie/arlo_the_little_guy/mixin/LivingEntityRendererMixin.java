package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.EntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import potatowolfie.arlo_the_little_guy.entity.crown.LivingEntityRendererAccessor;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<S extends EntityRenderState, M extends EntityModel<? super S>>
        implements LivingEntityRendererAccessor<S, M> {

    @Shadow
    protected abstract boolean addFeature(FeatureRenderer<S, M> feature);

    @Override
    public void arloTheLittleGuy$addFeature(FeatureRenderer<S, M> feature) {
        this.addFeature(feature);
    }
}
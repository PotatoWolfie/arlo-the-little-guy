package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.client.model.animal.golem.SnowGolemModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.SnowGolemRenderer;
import net.minecraft.client.renderer.entity.state.SnowGolemRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import potatowolfie.arlo_the_little_guy.entity.client.ModEntityModelLayers;
import potatowolfie.arlo_the_little_guy.entity.crown.LivingEntityRendererAccessor;
import potatowolfie.arlo_the_little_guy.entity.top_hat.SnowGolemTopHatModel;
import potatowolfie.arlo_the_little_guy.entity.top_hat.SnowGolemTopHatRenderer;

@Mixin(SnowGolemRenderer.class)
public class SnowGolemEntityRendererMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addTopHatFeature(EntityRendererProvider.Context context, CallbackInfo ci) {
        LivingEntityRendererAccessor<SnowGolemRenderState, SnowGolemModel> accessor =
                (LivingEntityRendererAccessor<SnowGolemRenderState, SnowGolemModel>) (Object) this;

        accessor.arloTheLittleGuy$addFeature(new SnowGolemTopHatRenderer(
                (RenderLayerParent<SnowGolemRenderState, SnowGolemModel>) (Object) this,
                new SnowGolemTopHatModel(context.bakeLayer(ModEntityModelLayers.SNOW_GOLEM_TOP_HAT))
        ));
    }
}
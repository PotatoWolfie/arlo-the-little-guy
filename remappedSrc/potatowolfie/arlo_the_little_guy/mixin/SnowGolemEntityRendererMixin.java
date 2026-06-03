package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.SnowGolemEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.SnowGolemEntityModel;
import net.minecraft.client.render.entity.state.SnowGolemEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import potatowolfie.arlo_the_little_guy.entity.client.ModEntityModelLayers;
import potatowolfie.arlo_the_little_guy.entity.crown.LivingEntityRendererAccessor;
import potatowolfie.arlo_the_little_guy.entity.top_hat.SnowGolemTopHatModel;
import potatowolfie.arlo_the_little_guy.entity.top_hat.SnowGolemTopHatRenderer;

@Mixin(SnowGolemEntityRenderer.class)
public class SnowGolemEntityRendererMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addTopHatFeature(EntityRendererFactory.Context context, CallbackInfo ci) {
        LivingEntityRendererAccessor<SnowGolemEntityRenderState, SnowGolemEntityModel> accessor =
                (LivingEntityRendererAccessor<SnowGolemEntityRenderState, SnowGolemEntityModel>) (Object) this;

        accessor.arloTheLittleGuy$addFeature(new SnowGolemTopHatRenderer(
                (FeatureRendererContext<SnowGolemEntityRenderState, SnowGolemEntityModel>) (Object) this,
                new SnowGolemTopHatModel(context.getPart(ModEntityModelLayers.SNOW_GOLEM_TOP_HAT))
        ));
    }
}
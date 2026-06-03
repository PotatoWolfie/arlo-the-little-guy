package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PigEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.client.render.entity.state.PigEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import potatowolfie.arlo_the_little_guy.entity.client.ModEntityModelLayers;
import potatowolfie.arlo_the_little_guy.entity.crown.LivingEntityRendererAccessor;
import potatowolfie.arlo_the_little_guy.entity.crown.PigCrownModel;
import potatowolfie.arlo_the_little_guy.entity.crown.PigCrownRenderer;

@Mixin(PigEntityRenderer.class)
public class PigEntityRendererMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addCrownFeature(EntityRendererFactory.Context context, CallbackInfo ci) {
        LivingEntityRendererAccessor<PigEntityRenderState, PigEntityModel> accessor =
                (LivingEntityRendererAccessor<PigEntityRenderState, PigEntityModel>) (Object) this;

        accessor.arloTheLittleGuy$addFeature(new PigCrownRenderer(
                (FeatureRendererContext<PigEntityRenderState, PigEntityModel>) (Object) this,
                new PigCrownModel(context.getPart(ModEntityModelLayers.PIG_CROWN))
        ));
    }
}
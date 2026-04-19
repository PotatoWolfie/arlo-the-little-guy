package potatowolfie.arlo_the_little_guy.entity.top_hat;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.animal.golem.SnowGolemModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.SnowGolemRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import potatowolfie.arlo_the_little_guy.item.ModItems;

@Environment(EnvType.CLIENT)
public class SnowGolemTopHatRenderer extends RenderLayer<SnowGolemRenderState, SnowGolemModel> {
    private static final Identifier SNOW_GOLEM_TOP_HAT_TEXTURE =
            Identifier.fromNamespaceAndPath("arlo-the-little-guy", "textures/models/armor/snow_golem_top_hat.png");

    private final SnowGolemTopHatModel topHatModel;

    public SnowGolemTopHatRenderer(RenderLayerParent<SnowGolemRenderState, SnowGolemModel> context,
                                   SnowGolemTopHatModel topHatModel) {
        super(context);
        this.topHatModel = topHatModel;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, SnowGolemRenderState state, float yRot, float xRot) {
        if (!state.headBlock.isEmpty()) {
            return;
        }

        ItemStack armor = ((SnowGolemRenderStateAccessor)state).arloTheLittleGuy$getBodyArmor();

        if (armor.isEmpty() || armor.getItem() != ModItems.TOP_HAT) {
            return;
        }

        SnowGolemModel golemModel = this.getParentModel();
        ModelPart golemHead = ((SnowGolemModelAccessor)golemModel).arloTheLittleGuy$getHead();

        poseStack.pushPose();

        poseStack.translate(
                golemHead.x / 16.0f,
                golemHead.y / 16.0f,
                golemHead.z / 16.0f
        );

        if (golemHead.zRot != 0.0F) {
            poseStack.mulPose(Axis.ZP.rotation(golemHead.zRot));
        }
        if (golemHead.yRot != 0.0F) {
            poseStack.mulPose(Axis.YP.rotation(golemHead.yRot));
        }
        if (golemHead.xRot != 0.0F) {
            poseStack.mulPose(Axis.XP.rotation(golemHead.xRot));
        }

        submitNodeCollector.submitModelPart(
                this.topHatModel.getTopHat(),
                poseStack,
                this.topHatModel.renderType(SNOW_GOLEM_TOP_HAT_TEXTURE),
                lightCoords,
                OverlayTexture.NO_OVERLAY,
                null,
                false,
                false,
                -1,
                null,
                0
        );

        poseStack.popPose();
    }
}
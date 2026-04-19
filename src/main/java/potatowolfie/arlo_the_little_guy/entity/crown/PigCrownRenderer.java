package potatowolfie.arlo_the_little_guy.entity.crown;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.animal.pig.PigModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.PigRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import potatowolfie.arlo_the_little_guy.item.ModItems;

@Environment(EnvType.CLIENT)
public class PigCrownRenderer extends RenderLayer<PigRenderState, PigModel> {
    private static final Identifier PIG_CROWN_TEXTURE =
            Identifier.fromNamespaceAndPath("arlo-the-little-guy", "textures/models/armor/pig_crown.png");

    private final PigCrownModel crownModel;

    public PigCrownRenderer(RenderLayerParent<PigRenderState, PigModel> context,
                            PigCrownModel crownModel) {
        super(context);
        this.crownModel = crownModel;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, PigRenderState state, float yRot, float xRot) {
        ItemStack armor = ((PigRenderStateAccessor)state).arloTheLittleGuy$getBodyArmor();

        if (armor.isEmpty() || armor.getItem() != ModItems.CROWN) {
            return;
        }

        PigModel pigModel = this.getParentModel();
        ModelPart pigHead = ((PigModelAccessor)pigModel).arloTheLittleGuy$getHead();

        poseStack.pushPose();

        poseStack.translate(
                pigHead.x / 16.0f,
                pigHead.y / 16.0f,
                pigHead.z / 16.0f
        );

        if (pigHead.zRot != 0.0F) {
            poseStack.mulPose(Axis.ZP.rotation(pigHead.zRot));
        }
        if (pigHead.yRot != 0.0F) {
            poseStack.mulPose(Axis.YP.rotation(pigHead.yRot));
        }
        if (pigHead.xRot != 0.0F) {
            poseStack.mulPose(Axis.XP.rotation(pigHead.xRot));
        }

        submitNodeCollector.submitModelPart(
                this.crownModel.getCrown(),
                poseStack,
                this.crownModel.renderType(PIG_CROWN_TEXTURE),
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
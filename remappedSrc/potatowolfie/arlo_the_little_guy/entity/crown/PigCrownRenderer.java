package potatowolfie.arlo_the_little_guy.entity.crown;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.client.render.entity.state.PigEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import potatowolfie.arlo_the_little_guy.item.ModItems;

@Environment(EnvType.CLIENT)
public class PigCrownRenderer extends FeatureRenderer<PigEntityRenderState, PigEntityModel> {
    private static final Identifier PIG_CROWN_TEXTURE =
            Identifier.of("arlo-the-little-guy", "textures/models/armor/pig_crown.png");

    private final PigCrownModel crownModel;

    public PigCrownRenderer(FeatureRendererContext<PigEntityRenderState, PigEntityModel> context,
                            PigCrownModel crownModel) {
        super(context);
        this.crownModel = crownModel;
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light,
                       PigEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack armor = ((PigRenderStateAccessor)state).arloTheLittleGuy$getBodyArmor();

        if (armor.isEmpty() || armor.getItem() != ModItems.CROWN) {
            return;
        }

        PigEntityModel pigModel = this.getContextModel();
        ModelPart pigHead = ((PigModelAccessor)pigModel).arloTheLittleGuy$getHead();

        matrices.push();

        matrices.translate(
                pigHead.originX / 16.0f,
                pigHead.originY / 16.0f,
                pigHead.originZ / 16.0f
        );

        if (pigHead.roll != 0.0F) {
            matrices.multiply(RotationAxis.POSITIVE_Z.rotation(pigHead.roll));
        }
        if (pigHead.yaw != 0.0F) {
            matrices.multiply(RotationAxis.POSITIVE_Y.rotation(pigHead.yaw));
        }
        if (pigHead.pitch != 0.0F) {
            matrices.multiply(RotationAxis.POSITIVE_X.rotation(pigHead.pitch));
        }

        queue.submitModelPart(
                this.crownModel.getCrown(),
                matrices,
                this.crownModel.getLayer(PIG_CROWN_TEXTURE),
                light,
                OverlayTexture.DEFAULT_UV,
                null,
                false,
                false,
                -1,
                null,
                0
        );

        matrices.pop();
    }
}
package potatowolfie.arlo_the_little_guy.entity.top_hat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.SnowGolemEntityModel;
import net.minecraft.client.render.entity.state.SnowGolemEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import potatowolfie.arlo_the_little_guy.item.ModItems;

@Environment(EnvType.CLIENT)
public class SnowGolemTopHatRenderer extends FeatureRenderer<SnowGolemEntityRenderState, SnowGolemEntityModel> {
    private static final Identifier SNOW_GOLEM_TOP_HAT_TEXTURE =
            Identifier.of("arlo-the-little-guy", "textures/models/armor/snow_golem_top_hat.png");

    private final SnowGolemTopHatModel topHatModel;

    public SnowGolemTopHatRenderer(FeatureRendererContext<SnowGolemEntityRenderState, SnowGolemEntityModel> context,
                                   SnowGolemTopHatModel topHatModel) {
        super(context);
        this.topHatModel = topHatModel;
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light,
                       SnowGolemEntityRenderState state, float limbAngle, float limbDistance) {
        if (state.hasPumpkin) {
            return;
        }

        ItemStack armor = ((SnowGolemRenderStateAccessor)state).arloTheLittleGuy$getBodyArmor();

        if (armor.isEmpty() || armor.getItem() != ModItems.TOP_HAT) {
            return;
        }

        SnowGolemEntityModel golemModel = this.getContextModel();
        ModelPart golemHead = ((SnowGolemModelAccessor)golemModel).arloTheLittleGuy$getHead();

        matrices.push();

        matrices.translate(
                golemHead.originX / 16.0f,
                golemHead.originY / 16.0f,
                golemHead.originZ / 16.0f
        );

        if (golemHead.roll != 0.0F) {
            matrices.multiply(RotationAxis.POSITIVE_Z.rotation(golemHead.roll));
        }
        if (golemHead.yaw != 0.0F) {
            matrices.multiply(RotationAxis.POSITIVE_Y.rotation(golemHead.yaw));
        }
        if (golemHead.pitch != 0.0F) {
            matrices.multiply(RotationAxis.POSITIVE_X.rotation(golemHead.pitch));
        }

        queue.submitModelPart(
                this.topHatModel.getTopHat(),
                matrices,
                this.topHatModel.getLayer(SNOW_GOLEM_TOP_HAT_TEXTURE),
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
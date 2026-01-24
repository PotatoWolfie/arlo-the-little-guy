package potatowolfie.arlo_the_little_guy.entity.crown;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import potatowolfie.arlo_the_little_guy.entity.client.ModEntityModelLayers;
import potatowolfie.arlo_the_little_guy.entity.straw_hat.StrawHatModel;

@Environment(EnvType.CLIENT)
public class CrownArmorRenderer implements ArmorRenderer {
    private static final Identifier TEXTURE = Identifier.of("arlo-the-little-guy", "textures/models/armor/crown.png");
    private CrownModel model;

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, ItemStack stack,
                       BipedEntityRenderState renderState, EquipmentSlot slot, int light,
                       BipedEntityModel<BipedEntityRenderState> contextModel) {

        if (this.model == null) {
            this.model = new CrownModel(
                    MinecraftClient.getInstance().getLoadedEntityModels().getModelPart(ModEntityModelLayers.CROWN)
            );
        }

        ModelPart contextHead = contextModel.head;

        matrices.push();

        matrices.translate(
                contextHead.originX / 16.0f,
                contextHead.originY / 16.0f,
                contextHead.originZ / 16.0f
        );

        if (contextHead.roll != 0.0F) {
            matrices.multiply(RotationAxis.POSITIVE_Z.rotation(contextHead.roll));
        }
        if (contextHead.yaw != 0.0F) {
            matrices.multiply(RotationAxis.POSITIVE_Y.rotation(contextHead.yaw));
        }
        if (contextHead.pitch != 0.0F) {
            matrices.multiply(RotationAxis.POSITIVE_X.rotation(contextHead.pitch));
        }

        queue.submitModelPart(
                this.model.getCrown(),
                matrices,
                this.model.getLayer(TEXTURE),
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
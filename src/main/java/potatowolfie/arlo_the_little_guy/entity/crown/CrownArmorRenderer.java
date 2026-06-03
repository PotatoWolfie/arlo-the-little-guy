package potatowolfie.arlo_the_little_guy.entity.crown;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import potatowolfie.arlo_the_little_guy.entity.client.ModEntityModelLayers;

@Environment(EnvType.CLIENT)
public class CrownArmorRenderer implements ArmorRenderer {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("arlo-the-little-guy", "textures/models/armor/crown.png");
    private CrownModel model;

    @Override
    public void render(PoseStack matrices, SubmitNodeCollector queue, ItemStack stack,
                       HumanoidRenderState renderState, EquipmentSlot slot, int light,
                       HumanoidModel<HumanoidRenderState> contextModel) {

        if (this.model == null) {
            this.model = new CrownModel(
                    Minecraft.getInstance().getEntityModels().bakeLayer(ModEntityModelLayers.CROWN)
            );
        }

        ModelPart contextHead = contextModel.head;

        matrices.pushPose();

        matrices.translate(
                contextHead.x / 16.0f,
                contextHead.y / 16.0f,
                contextHead.z / 16.0f
        );

        if (contextHead.zRot != 0.0F) {
            matrices.mulPose(Axis.ZP.rotation(contextHead.zRot));
        }
        if (contextHead.yRot != 0.0F) {
            matrices.mulPose(Axis.YP.rotation(contextHead.yRot));
        }
        if (contextHead.xRot != 0.0F) {
            matrices.mulPose(Axis.XP.rotation(contextHead.xRot));
        }

        queue.submitModelPart(
                this.model.getCrown(),
                matrices,
                this.model.renderType(TEXTURE),
                light,
                OverlayTexture.NO_OVERLAY,
                null
        );

        matrices.popPose();
    }
}
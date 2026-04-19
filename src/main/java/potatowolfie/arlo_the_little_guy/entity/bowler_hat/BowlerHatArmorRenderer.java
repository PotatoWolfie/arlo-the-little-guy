package potatowolfie.arlo_the_little_guy.entity.bowler_hat;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import potatowolfie.arlo_the_little_guy.entity.client.ModEntityModelLayers;

@Environment(EnvType.CLIENT)
public class BowlerHatArmorRenderer implements ArmorRenderer {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("arlo-the-little-guy", "textures/models/armor/bowler_hat.png");
    private BowlerHatModel model;

    @Override
    public void render(PoseStack matrices, SubmitNodeCollector queue, ItemStack stack,
                       HumanoidRenderState renderState, EquipmentSlot slot, int light,
                       HumanoidModel<HumanoidRenderState> contextModel) {

        if (this.model == null) {
            this.model = new BowlerHatModel(
                    Minecraft.getInstance().getEntityModels().bakeLayer(ModEntityModelLayers.BOWLER_HAT)
            );
        }

        contextModel.head.visible = false;
        this.model.setAngles(renderState);
        this.model.copyTransformFromContextModel(contextModel.head);

        queue.submitModelPart(
                this.model.root(),
                matrices,
                this.model.renderType(TEXTURE),
                light,
                OverlayTexture.NO_OVERLAY,
                null,
                false,
                false,
                -1,
                null,
                0
        );
        contextModel.head.visible = true;
    }
}
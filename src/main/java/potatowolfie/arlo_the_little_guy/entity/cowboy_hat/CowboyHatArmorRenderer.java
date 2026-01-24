package potatowolfie.arlo_the_little_guy.entity.cowboy_hat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import potatowolfie.arlo_the_little_guy.entity.client.ModEntityModelLayers;

@Environment(EnvType.CLIENT)
public class CowboyHatArmorRenderer implements ArmorRenderer {
    private static final Identifier TEXTURE = Identifier.of("arlo-the-little-guy", "textures/models/armor/cowboy_hat.png");
    private CowboyHatModel model;

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, ItemStack stack,
                       BipedEntityRenderState renderState, EquipmentSlot slot, int light,
                       BipedEntityModel<BipedEntityRenderState> contextModel) {

        if (this.model == null) {
            this.model = new CowboyHatModel(
                    MinecraftClient.getInstance().getLoadedEntityModels().getModelPart(ModEntityModelLayers.COWBOY_HAT)
            );
        }

        contextModel.head.visible = false;
        this.model.setAngles(renderState);
        this.model.copyTransformFromContextModel(contextModel.head);

        queue.submitModelPart(
                this.model.getRootPart(),
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
        contextModel.head.visible = true;
    }
}
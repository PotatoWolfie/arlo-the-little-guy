package potatowolfie.arlo_the_little_guy.entity.top_hat;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.SnowGolemEntityRenderState;

public class SnowGolemTopHatModel extends EntityModel<SnowGolemEntityRenderState> {
    private final ModelPart head;
    private final ModelPart tophat;

    public SnowGolemTopHatModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.tophat = this.head.getChild("tophat");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create(), ModelTransform.NONE);

        head.addChild("tophat", ModelPartBuilder.create()
                .uv(0, 0).cuboid(-7.0F, -18.0F, -5.0F, 12.0F, 0.0F, 12.0F, new Dilation(0.0F))
                .uv(0, 12).cuboid(-5.0F, -27.02F, -3.0F, 8.0F, 9.0F, 8.0F, new Dilation(0.0F)),
                ModelTransform.origin(1.0F, 10.5F, -1.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(SnowGolemEntityRenderState state) {
        super.setAngles(state);
        this.head.yaw = state.relativeHeadYaw * 0.017453292F;
    }

    public ModelPart getHead() {
        return this.head;
    }

    public ModelPart getTopHat() {
        return this.tophat;
    }
}
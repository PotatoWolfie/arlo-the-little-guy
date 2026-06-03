package potatowolfie.arlo_the_little_guy.entity.crown;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.PigEntityRenderState;

public class PigCrownModel extends EntityModel<PigEntityRenderState> {
    private final ModelPart head;
    private final ModelPart crown;

    public PigCrownModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.crown = this.head.getChild("crown");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create(), ModelTransform.NONE);

        head.addChild("crown", ModelPartBuilder.create()
                .uv(2, 1).cuboid(-4.0F, -31.50F, -4.0F, 8.0F, 3.0F, 8.0F, new Dilation(0.25F)),
                ModelTransform.origin(0.0F, 27.0F, -4.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(PigEntityRenderState state) {
        super.setAngles(state);
        this.head.yaw = state.relativeHeadYaw * 0.017453292F;
        this.head.pitch = state.pitch * 0.017453292F;
    }

    public void copyTransformFromPigHead(ModelPart pigHead) {
        this.head.originX = pigHead.originX;
        this.head.originY = pigHead.originY;
        this.head.originZ = pigHead.originZ;
        this.head.xScale = pigHead.xScale;
        this.head.yScale = pigHead.yScale;
        this.head.zScale = pigHead.zScale;
    }

    public ModelPart getHead() {
        return this.head;
    }

    public ModelPart getCrown() {
        return this.crown;
    }
}
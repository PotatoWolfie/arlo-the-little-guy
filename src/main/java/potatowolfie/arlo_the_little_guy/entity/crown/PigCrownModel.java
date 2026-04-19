package potatowolfie.arlo_the_little_guy.entity.crown;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.PigRenderState;

public class PigCrownModel extends EntityModel<PigRenderState> {
    private final ModelPart head;
    private final ModelPart crown;

    public PigCrownModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.crown = this.head.getChild("crown");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);

        head.addOrReplaceChild("crown", CubeListBuilder.create()
                .texOffs(2, 1).addBox(-4.0F, -31.50F, -4.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.25F)),
                PartPose.offset(0.0F, 27.0F, -4.0F));
        return LayerDefinition.create(modelData, 64, 64);
    }

    public void setAngles(PigRenderState state) {
        super.setupAnim(state);
        this.head.yRot = state.yRot * 0.017453292F;
        this.head.xRot = state.xRot * 0.017453292F;
    }

    public void copyTransformFromPigHead(ModelPart pigHead) {
        this.head.x = pigHead.x;
        this.head.y = pigHead.y;
        this.head.z = pigHead.z;
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
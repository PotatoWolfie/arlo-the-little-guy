package potatowolfie.arlo_the_little_guy.entity.prismarine_pipis;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

// Made with Blockbench 5.1.4

public class PrismarinePipisModel extends EntityModel<HumanoidRenderState> {
    private final ModelPart head;
    private final ModelPart PrismarinePipis;

    public PrismarinePipisModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.PrismarinePipis = this.head.getChild("PrismarinePipis");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);

        PartDefinition PrismarinePipis = head.addOrReplaceChild("PrismarinePipis", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3.0F, -1.5F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -9.5F, 0.0F));

        return LayerDefinition.create(modelData, 32, 32);
    }

    public void setAngles(HumanoidRenderState state) {
        super.setupAnim(state);
        this.head.yRot = state.yRot * 0.017453292F;
        this.head.xRot = state.xRot * 0.017453292F;
    }

    public void copyTransformFromContextModel(ModelPart contextHead) {
        this.head.x = contextHead.x;
        this.head.y = contextHead.y;
        this.head.z = contextHead.z;
        this.head.xRot = contextHead.xRot;
        this.head.yRot = contextHead.yRot;
        this.head.zRot = contextHead.zRot;
        this.head.xScale = contextHead.xScale;
        this.head.yScale = contextHead.yScale;
        this.head.zScale = contextHead.zScale;
    }
}
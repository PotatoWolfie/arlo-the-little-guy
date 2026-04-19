package potatowolfie.arlo_the_little_guy.entity.straw_hat;

// Made with Blockbench 5.0.7

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

public class StrawHatModel extends EntityModel<HumanoidRenderState> {
	private final ModelPart straw_hat;
	private final ModelPart head;

	public StrawHatModel(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.straw_hat = this.head.getChild("straw_hat");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();

		PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);

		head.addOrReplaceChild("straw_hat", CubeListBuilder.create()
						.texOffs(-15, 1).addBox(-11.0F, 0.0F, -5.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
						.texOffs(0, 18).addBox(-7.0F, -4.51F, -1.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.51F)),
				PartPose.offset(3.0F, -6.0F, -3.0F));
		return LayerDefinition.create(modelData, 64, 64);
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
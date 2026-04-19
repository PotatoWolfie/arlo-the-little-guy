package potatowolfie.arlo_the_little_guy.entity.top_hat;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

// Made with Blockbench 5.0.7

public class TopHatModel extends EntityModel<HumanoidRenderState> {
	private final ModelPart tophat;
	private final ModelPart head;

	public TopHatModel(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.tophat = this.head.getChild("tophat");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();

		PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);

		head.addOrReplaceChild("tophat", CubeListBuilder.create()
						.texOffs(0, 0).addBox(-7.0F, -17.0F, -5.0F, 12.0F, -1.0F, 12.0F, new CubeDeformation(0.51F))
						.texOffs(0, 12).addBox(-5.0F, -27.02F, -3.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.51F)),
				PartPose.offset(1.0F, 10.5F, -1.0F));
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
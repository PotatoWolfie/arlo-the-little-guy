package potatowolfie.arlo_the_little_guy.entity.tricorn;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

// Made with Blockbench 5.0.7

public class TricornHatModel extends EntityModel<HumanoidRenderState> {
	private final ModelPart pirate_hat;
	private final ModelPart head;

	public TricornHatModel(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.pirate_hat = this.head.getChild("pirate_hat");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();

		PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);

		head.addOrReplaceChild("pirate_hat", CubeListBuilder.create()
				.texOffs(0, 16).addBox(-3.5F, -23.0F, -3.5F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-5.5F, -25.0F, -4.5F, 11.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 10.0F, 0.0F));
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
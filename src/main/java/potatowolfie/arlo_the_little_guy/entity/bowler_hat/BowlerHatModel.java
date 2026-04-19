package potatowolfie.arlo_the_little_guy.entity.bowler_hat;// Made with Blockbench 5.0.7

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

public class BowlerHatModel extends EntityModel<HumanoidRenderState> {
	private final ModelPart bowler_hat;
	private final ModelPart head;

	public BowlerHatModel(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.bowler_hat = this.head.getChild("bowler_hat");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();

		PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);

		head.addOrReplaceChild("bowler_hat", CubeListBuilder.create()
				.texOffs(0, 5).addBox(-1.5F, -2.245F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-2.5F, -0.245F, -2.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.75F, -7.755F, 2.75F, -0.4207F, -0.115F, -0.2822F));
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
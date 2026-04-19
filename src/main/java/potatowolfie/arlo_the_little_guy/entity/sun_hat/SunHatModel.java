package potatowolfie.arlo_the_little_guy.entity.sun_hat;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

// Made with Blockbench 5.0.7

public class SunHatModel extends EntityModel<HumanoidRenderState> {
	private final ModelPart sun_hat;
	private final ModelPart head;

	public SunHatModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
		this.sun_hat = this.head.getChild("sun_hat");
	}
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();

		PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);

		head.addOrReplaceChild("sun_hat", CubeListBuilder.create()
						.texOffs(0, 0).addBox(-6.5F, -17.0F, -6.5F, 13.0F, -1.0F, 13.0F, new CubeDeformation(0.5F))
						.texOffs(0, 13).addBox(-4.0F, -21.02F, -4.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.51F)),
				PartPose.offset(0.0F, 11.0F, 0.0F));

		head.addOrReplaceChild("cube_r1", CubeListBuilder.create()
				.texOffs(2, 23).addBox(0.0F, 0.01F, -0.5F, 0.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(5.0F, -6.5F, -0.5F, 0.0F, 0.0F, 0.1745F));

		head.addOrReplaceChild("cube_r2", CubeListBuilder.create()
				.texOffs(0, 23).addBox(0.0F, 0.01F, -0.5F, 0.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-5.0F, -6.5F, -0.5F, 0.0F, 0.0F, -0.1745F));
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
package potatowolfie.arlo_the_little_guy.entity.cowboy_hat;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

// Made with Blockbench 5.0.7

public class CowboyHatModel extends EntityModel<HumanoidRenderState> {
	private final ModelPart Hat;
	private final ModelPart hatpart;
	private final ModelPart head;

	public CowboyHatModel(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.Hat = this.head.getChild("Hat");
		this.hatpart = this.Hat.getChild("hatpart");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();

		PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition Hat = head.addOrReplaceChild("Hat", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition hatpart = Hat.addOrReplaceChild("hatpart", CubeListBuilder.create()
				.texOffs(-12, 0).addBox(-6.0F, 1.26F, -6.0F, 12.0F, -1.0F, 12.0F, new CubeDeformation(0.5F))
				.texOffs(0, 12).addBox(-4.0F, -2.76F, -4.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.51F)), PartPose.offset(0.0F, -0.76F, 0.0F));

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
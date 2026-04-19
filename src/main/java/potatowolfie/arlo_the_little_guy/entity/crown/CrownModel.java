package potatowolfie.arlo_the_little_guy.entity.crown;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

// Made with Blockbench 5.0.7

public class CrownModel extends EntityModel<HumanoidRenderState> {
	private final ModelPart crown;
	private final ModelPart head;

	public CrownModel(ModelPart root) {
        super(root);
		this.head = root.getChild("head");
        this.crown = this.head.getChild("crown");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();

		PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);

		head.addOrReplaceChild("crown", CubeListBuilder.create()
				.texOffs(0, 0).addBox(-4.5F, -33.01F, -4.5F, 9.0F, 3.0F, 9.0F, new CubeDeformation(0.01F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));

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
		this.head.xScale = contextHead.xScale;
		this.head.yScale = contextHead.yScale;
		this.head.zScale = contextHead.zScale;
	}

	public ModelPart getCrown() {
		return this.crown;
	}
}
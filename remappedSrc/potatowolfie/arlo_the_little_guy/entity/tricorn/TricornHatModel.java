package potatowolfie.arlo_the_little_guy.entity.tricorn;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;

// Made with Blockbench 5.0.7

public class TricornHatModel extends EntityModel<BipedEntityRenderState> {
	private final ModelPart pirate_hat;
	private final ModelPart head;

	public TricornHatModel(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.pirate_hat = this.head.getChild("pirate_hat");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create(), ModelTransform.NONE);

		head.addChild("pirate_hat", ModelPartBuilder.create()
				.uv(0, 16).cuboid(-3.5F, -23.0F, -3.5F, 7.0F, 5.0F, 7.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-5.5F, -25.0F, -4.5F, 11.0F, 7.0F, 9.0F, new Dilation(0.0F)),
				ModelTransform.origin(0.0F, 10.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(BipedEntityRenderState state) {
		super.setAngles(state);
		this.head.yaw = state.relativeHeadYaw * 0.017453292F;
		this.head.pitch = state.pitch * 0.017453292F;
	}

	public void copyTransformFromContextModel(ModelPart contextHead) {
		this.head.originX = contextHead.originX;
		this.head.originY = contextHead.originY;
		this.head.originZ = contextHead.originZ;
		this.head.pitch = contextHead.pitch;
		this.head.yaw = contextHead.yaw;
		this.head.roll = contextHead.roll;
		this.head.xScale = contextHead.xScale;
		this.head.yScale = contextHead.yScale;
		this.head.zScale = contextHead.zScale;
	}
}
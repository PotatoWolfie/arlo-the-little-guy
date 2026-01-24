package potatowolfie.arlo_the_little_guy.entity.straw_hat;

// Made with Blockbench 5.0.7

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;

public class StrawHatModel extends EntityModel<BipedEntityRenderState> {
	private final ModelPart straw_hat;
	private final ModelPart head;

	public StrawHatModel(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.straw_hat = this.head.getChild("straw_hat");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create(), ModelTransform.NONE);

		head.addChild("straw_hat", ModelPartBuilder.create()
						.uv(-15, 1).cuboid(-11.0F, 0.0F, -5.0F, 16.0F, 0.0F, 16.0F, new Dilation(0.0F))
						.uv(0, 18).cuboid(-7.0F, -4.51F, -1.0F, 8.0F, 4.0F, 8.0F, new Dilation(0.51F)),
				ModelTransform.origin(3.0F, -6.0F, -3.0F));
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
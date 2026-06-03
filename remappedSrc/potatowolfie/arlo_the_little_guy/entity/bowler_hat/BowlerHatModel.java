package potatowolfie.arlo_the_little_guy.entity.bowler_hat;// Made with Blockbench 5.0.7

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;

public class BowlerHatModel extends EntityModel<BipedEntityRenderState> {
	private final ModelPart bowler_hat;
	private final ModelPart head;

	public BowlerHatModel(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.bowler_hat = this.head.getChild("bowler_hat");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create(), ModelTransform.NONE);

		head.addChild("bowler_hat", ModelPartBuilder.create()
				.uv(0, 5).cuboid(-1.5F, -2.245F, -1.5F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-2.5F, -0.245F, -2.5F, 5.0F, 0.0F, 5.0F, new Dilation(0.0F)),
				ModelTransform.of(-2.75F, -7.755F, 2.75F, -0.4207F, -0.115F, -0.2822F));
		return TexturedModelData.of(modelData, 32, 32);
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
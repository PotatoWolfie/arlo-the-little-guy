package potatowolfie.arlo_the_little_guy.entity.top_hat;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;

// Made with Blockbench 5.0.7

public class TopHatModel extends EntityModel<BipedEntityRenderState> {
	private final ModelPart tophat;
	private final ModelPart head;

	public TopHatModel(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.tophat = this.head.getChild("tophat");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create(), ModelTransform.NONE);

		head.addChild("tophat", ModelPartBuilder.create()
						.uv(0, 0).cuboid(-7.0F, -17.0F, -5.0F, 12.0F, -1.0F, 12.0F, new Dilation(0.51F))
						.uv(0, 12).cuboid(-5.0F, -27.02F, -3.0F, 8.0F, 9.0F, 8.0F, new Dilation(0.51F)),
				ModelTransform.origin(1.0F, 10.5F, -1.0F));
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
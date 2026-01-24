package potatowolfie.arlo_the_little_guy.entity.sun_hat;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;

// Made with Blockbench 5.0.7

public class SunHatModel extends EntityModel<BipedEntityRenderState> {
	private final ModelPart sun_hat;
	private final ModelPart head;

	public SunHatModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
		this.sun_hat = this.head.getChild("sun_hat");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create(), ModelTransform.NONE);

		head.addChild("sun_hat", ModelPartBuilder.create()
						.uv(0, 0).cuboid(-6.5F, -17.0F, -6.5F, 13.0F, -1.0F, 13.0F, new Dilation(0.5F))
						.uv(0, 13).cuboid(-4.0F, -21.02F, -4.0F, 8.0F, 3.0F, 8.0F, new Dilation(0.51F)),
				ModelTransform.origin(0.0F, 11.0F, 0.0F));

		head.addChild("cube_r1", ModelPartBuilder.create()
				.uv(2, 23).cuboid(0.0F, 0.01F, -0.5F, 0.0F, 6.0F, 1.0F, new Dilation(0.0F)),
				ModelTransform.of(5.0F, -6.5F, -0.5F, 0.0F, 0.0F, 0.1745F));

		head.addChild("cube_r2", ModelPartBuilder.create()
				.uv(0, 23).cuboid(0.0F, 0.01F, -0.5F, 0.0F, 6.0F, 1.0F, new Dilation(0.0F)),
				ModelTransform.of(-5.0F, -6.5F, -0.5F, 0.0F, 0.0F, -0.1745F));
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
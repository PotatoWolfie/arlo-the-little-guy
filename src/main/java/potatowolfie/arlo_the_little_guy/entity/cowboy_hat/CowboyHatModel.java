package potatowolfie.arlo_the_little_guy.entity.cowboy_hat;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;

// Made with Blockbench 5.0.7

public class CowboyHatModel extends EntityModel<BipedEntityRenderState> {
	private final ModelPart Hat;
	private final ModelPart hatpart;
	private final ModelPart head;

	public CowboyHatModel(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.Hat = this.head.getChild("Hat");
		this.hatpart = this.Hat.getChild("hatpart");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create(), ModelTransform.NONE);

		ModelPartData Hat = head.addChild("Hat", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -6.0F, 0.0F));

		ModelPartData hatpart = Hat.addChild("hatpart", ModelPartBuilder.create()
				.uv(-12, 0).cuboid(-6.0F, 1.26F, -6.0F, 12.0F, -1.0F, 12.0F, new Dilation(0.5F))
				.uv(0, 12).cuboid(-4.0F, -2.76F, -4.0F, 8.0F, 3.0F, 8.0F, new Dilation(0.51F)), ModelTransform.origin(0.0F, -0.76F, 0.0F));

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
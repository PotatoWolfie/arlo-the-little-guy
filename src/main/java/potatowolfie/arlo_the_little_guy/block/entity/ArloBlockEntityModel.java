package potatowolfie.arlo_the_little_guy.block.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.util.math.MatrixStack;
import potatowolfie.arlo_the_little_guy.animation.ArloAnimations;

@Environment(EnvType.CLIENT)
public class ArloBlockEntityModel extends Model<ArloBlockEntityRenderState> {
	private final ModelPart root;
	private final ModelPart arlo;
	private final ModelPart arm;
	private final ModelPart bowler_hat;
	private final ModelPart cactus_flower;
	private final ModelPart cowboy_hat;
	private final ModelPart crown_hat;
	private final ModelPart straw_hat;
	private final ModelPart sun_hat;
	private final ModelPart bucket_hat;
	private final ModelPart tophat;
	private final ModelPart pirate_hat;

	private final Animation idleAnimation;
	private final Animation interactAnimation;
	private final Animation randomAnimation;

	public ArloBlockEntityModel(ModelPart root) {
		super(root, RenderLayers::entityCutoutNoCull);
		this.root = root;
		this.arlo = root.getChild("arlo");
		this.arm = this.arlo.getChild("arm");
		this.bowler_hat = this.arlo.getChild("bowler_hat");
		this.cactus_flower = this.arlo.getChild("cactus_flower");
		this.cowboy_hat = this.arlo.getChild("cowboy_hat");
		this.crown_hat = this.arlo.getChild("crown_hat");
		this.straw_hat = this.arlo.getChild("straw_hat");
		this.sun_hat = this.arlo.getChild("sun_hat");
		this.bucket_hat = this.arlo.getChild("bucket_hat");
		this.tophat = this.arlo.getChild("tophat");
		this.pirate_hat = this.arlo.getChild("pirate_hat");

		this.idleAnimation = ArloAnimations.ARLO_IDLE.createAnimation(root);
		this.interactAnimation = ArloAnimations.ARLO_SHAKE.createAnimation(root);
		this.randomAnimation = ArloAnimations.ARLO_WIGGLE.createAnimation(root);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		// Arlo is the main parent
		ModelPartData arlo = modelPartData.addChild("arlo", ModelPartBuilder.create()
						.uv(0, 0).cuboid(-3.0F, -18.0F, -3.0F, 6.0F, 18.0F, 6.0F, new Dilation(0.0F))
						.uv(36, 0).cuboid(3.0F, -18.0F, -4.0F, 1.0F, 18.0F, 1.0F, new Dilation(0.0F))
						.uv(24, 0).cuboid(3.0F, -18.0F, 3.0F, 1.0F, 18.0F, 1.0F, new Dilation(0.0F))
						.uv(28, 0).cuboid(-4.0F, -18.0F, 3.0F, 1.0F, 18.0F, 1.0F, new Dilation(0.0F))
						.uv(32, 0).cuboid(-4.0F, -18.0F, -4.0F, 1.0F, 18.0F, 1.0F, new Dilation(0.0F)),
				ModelTransform.origin(0.0F, 24.0F, 0.0F));

		// All parts are children of arlo
		arlo.addChild("arm", ModelPartBuilder.create()
						.uv(0, 24).cuboid(0.0F, -1.5F, -1.5F, 5.0F, 3.0F, 3.0F, new Dilation(0.0F))
						.uv(16, 24).cuboid(2.0F, -4.5F, -1.5F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
						.uv(40, 0).cuboid(1.0F, -4.5F, -2.5F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F))
						.uv(40, 7).cuboid(5.0F, -4.5F, -2.5F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F))
						.uv(44, 0).cuboid(5.0F, -4.5F, 1.5F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F))
						.uv(48, 0).cuboid(1.0F, -4.5F, 1.5F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F)),
				ModelTransform.origin(3.0F, -9.5F, 0.0F));

		arlo.addChild("bowler_hat", ModelPartBuilder.create()
						.uv(27, 19).cuboid(-1.5F, -2.245F, -1.5F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
						.uv(23, 24).cuboid(-2.5F, -0.245F, -2.5F, 5.0F, 0.0F, 5.0F, new Dilation(0.0F)),
				ModelTransform.of(-2.0F, -17.755F, 2.0F, -0.1334F, -0.0933F, -0.108F));

		ModelPartData cactus_flower = arlo.addChild("cactus_flower", ModelPartBuilder.create(),
				ModelTransform.origin(0.0F, 0.0F, 0.0F));

		cactus_flower.addChild("cube_r1", ModelPartBuilder.create()
						.uv(0, 30).cuboid(-5.0F, -2.5F, 0.0F, 10.0F, 7.0F, 0.0F, new Dilation(0.0F)),
				ModelTransform.of(0.0F, -22.5F, 0.0F, 0.0F, 0.7854F, 0.0F));

		cactus_flower.addChild("cube_r2", ModelPartBuilder.create()
						.uv(0, 30).cuboid(-5.0F, -2.5F, 0.0F, 10.0F, 7.0F, 0.0F, new Dilation(0.0F)),
				ModelTransform.of(0.0F, -22.5F, 0.0F, 0.0F, -0.7854F, 0.0F));

		arlo.addChild("cowboy_hat", ModelPartBuilder.create()
						.uv(42, 0).cuboid(-5.0F, -6.0F, -5.0F, 10.0F, 0.0F, 10.0F, new Dilation(0.0F))
						.uv(40, 10).cuboid(-3.0F, -8.02F, -3.0F, 6.0F, 2.0F, 6.0F, new Dilation(0.01F)),
				ModelTransform.origin(0.0F, -12.0F, 0.0F));

		arlo.addChild("crown_hat", ModelPartBuilder.create()
						.uv(38, 18).cuboid(-3.5F, -21.01F, -3.5F, 7.0F, 3.0F, 7.0F, new Dilation(0.0F)),
				ModelTransform.origin(0.0F, 0.0F, 0.0F));

		arlo.addChild("straw_hat", ModelPartBuilder.create()
						.uv(-10, 37).cuboid(-5.0F, -18.0F, -5.0F, 10.0F, 0.0F, 10.0F, new Dilation(0.0F))
						.uv(20, 30).cuboid(-3.0F, -21.02F, -3.0F, 6.0F, 3.0F, 6.0F, new Dilation(0.01F)),
				ModelTransform.origin(0.0F, 0.0F, 0.0F));

		ModelPartData sun_hat = arlo.addChild("sun_hat", ModelPartBuilder.create()
						.uv(-11, 47).cuboid(-5.5F, -18.0F, -5.5F, 11.0F, 0.0F, 11.0F, new Dilation(0.0F))
						.uv(22, 39).cuboid(-3.0F, -21.02F, -3.0F, 6.0F, 3.0F, 6.0F, new Dilation(0.01F)),
				ModelTransform.origin(0.0F, 0.0F, 0.0F));

		sun_hat.addChild("cube_r3", ModelPartBuilder.create()
						.uv(20, 40).cuboid(0.0F, 0.01F, -0.5F, 0.0F, 6.0F, 1.0F, new Dilation(0.0F)),
				ModelTransform.of(4.0F, -18.0F, -0.5F, 0.0F, 0.0F, 0.1745F));

		sun_hat.addChild("cube_r4", ModelPartBuilder.create()
						.uv(20, 40).cuboid(0.0F, 0.01F, -0.5F, 0.0F, 6.0F, 1.0F, new Dilation(0.0F)),
				ModelTransform.of(-4.0F, -18.0F, -0.5F, 0.0F, 0.0F, -0.1745F));

		arlo.addChild("bucket_hat", ModelPartBuilder.create()
						.uv(22, 48).cuboid(-3.5F, -20.25F, -3.5F, 7.0F, 9.0F, 7.0F, new Dilation(0.0F)),
				ModelTransform.origin(0.0F, 0.0F, 0.0F));

		arlo.addChild("tophat", ModelPartBuilder.create()
						.uv(-10, 58).cuboid(-5.0F, -18.0F, -5.0F, 10.0F, 0.0F, 10.0F, new Dilation(0.0F))
						.uv(20, 64).cuboid(-3.0F, -24.02F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.01F)),
				ModelTransform.origin(0.0F, 0.0F, 0.0F));

		arlo.addChild("pirate_hat", ModelPartBuilder.create()
						.uv(44, 28).cuboid(-3.5F, -23.0F, -3.5F, 7.0F, 5.0F, 7.0F, new Dilation(0.0F))
						.uv(50, 40).cuboid(-5.5F, -25.0F, -4.5F, 11.0F, 7.0F, 9.0F, new Dilation(0.0F)),
				ModelTransform.origin(0.0F, 0.0F, 0.0F));

		return TexturedModelData.of(modelData, 128, 128);
	}

	public void setAngles(ArloBlockEntityRenderState renderState) {
		this.root.traverse().forEach(part -> {
			part.resetTransform();
		});

		this.bowler_hat.visible = false;
		this.cactus_flower.visible = false;
		this.cowboy_hat.visible = false;
		this.crown_hat.visible = false;
		this.straw_hat.visible = false;
		this.sun_hat.visible = false;
		this.bucket_hat.visible = false;
		this.tophat.visible = false;
		this.pirate_hat.visible = false;

		switch (renderState.hatType) {
			case "arlo-the-little-guy:bowler_hat" -> this.bowler_hat.visible = true;
			case "minecraft:cactus_flower" -> this.cactus_flower.visible = true;
			case "arlo-the-little-guy:cowboy_hat" -> this.cowboy_hat.visible = true;
			case "arlo-the-little-guy:crown" -> this.crown_hat.visible = true;
			case "arlo-the-little-guy:straw_hat" -> this.straw_hat.visible = true;
			case "arlo-the-little-guy:sun_hat" -> this.sun_hat.visible = true;
			case "minecraft:bucket" -> this.bucket_hat.visible = true;
			case "arlo-the-little-guy:top_hat" -> this.tophat.visible = true;
			case "arlo-the-little-guy:tricorn" -> this.pirate_hat.visible = true;
		}

		if (renderState.arloState == ArloBlockEntity.ArloState.INTERACTING) {
			this.interactAnimation.apply(renderState.interactAnimationState, renderState.age);
		} else if (renderState.arloState == ArloBlockEntity.ArloState.RANDOM_WIGGLE) {
			this.randomAnimation.apply(renderState.randomAnimationState, renderState.age);
		} else {
			this.idleAnimation.apply(renderState.idleAnimationState, renderState.age);
		}
	}

	public ModelPart getArlo() {
		return this.arlo;
	}

	public ModelPart getArm() {
		return this.arm;
	}

	public ModelPart getBowlerHat() {
		return this.bowler_hat;
	}

	public ModelPart getCactusFlower() {
		return this.cactus_flower;
	}

	public ModelPart getCowboyHat() {
		return this.cowboy_hat;
	}

	public ModelPart getCrownHat() {
		return this.crown_hat;
	}

	public ModelPart getStrawHat() {
		return this.straw_hat;
	}

	public ModelPart getSunHat() {
		return this.sun_hat;
	}

	public ModelPart getBucketHat() {
		return this.bucket_hat;
	}

	public ModelPart getTophat() {
		return this.tophat;
	}

	public ModelPart getPirateHat() {
		return this.pirate_hat;
	}

	public ModelPart getRoot() {
		return this.root;
	}
}
package potatowolfie.arlo_the_little_guy.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.rendertype.RenderTypes;
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
	private final ModelPart goggles;
	private final ModelPart prismarine_pipis;
	private final ModelPart stop_sign;

	private final KeyframeAnimation idleAnimation;
	private final KeyframeAnimation interactAnimation;
	private final KeyframeAnimation randomAnimation;

	public ArloBlockEntityModel(ModelPart root) {
		super(root, RenderTypes::armorCutoutNoCull);
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
		this.goggles = this.arlo.getChild("goggles");
		this.prismarine_pipis = this.arlo.getChild("prismarine_pipis");
		this.stop_sign = this.arm.getChild("stop_sign");

		this.idleAnimation = ArloAnimations.ARLO_IDLE.bake(root);
		this.interactAnimation = ArloAnimations.ARLO_SHAKE.bake(root);
		this.randomAnimation = ArloAnimations.ARLO_WIGGLE.bake(root);
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();

		PartDefinition arlo = modelPartData.addOrReplaceChild("arlo", CubeListBuilder.create()
						.texOffs(0, 0).addBox(-3.0F, -18.0F, -3.0F, 6.0F, 18.0F, 6.0F, new CubeDeformation(0.0F))
						.texOffs(36, 0).addBox(3.0F, -18.0F, -4.0F, 1.0F, 18.0F, 1.0F, new CubeDeformation(0.0F))
						.texOffs(24, 0).addBox(3.0F, -18.0F, 3.0F, 1.0F, 18.0F, 1.0F, new CubeDeformation(0.0F))
						.texOffs(28, 0).addBox(-4.0F, -18.0F, 3.0F, 1.0F, 18.0F, 1.0F, new CubeDeformation(0.0F))
						.texOffs(32, 0).addBox(-4.0F, -18.0F, -4.0F, 1.0F, 18.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition arm = arlo.addOrReplaceChild("arm", CubeListBuilder.create()
						.texOffs(0, 24).addBox(0.0F, -1.5F, -1.5F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
						.texOffs(16, 24).addBox(2.0F, -4.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
						.texOffs(40, 0).addBox(1.0F, -4.5F, -2.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
						.texOffs(40, 7).addBox(5.0F, -4.5F, -2.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
						.texOffs(44, 0).addBox(5.0F, -4.5F, 1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
						.texOffs(48, 0).addBox(1.0F, -4.5F, 1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(3.0F, -9.5F, 0.0F));

		arlo.addOrReplaceChild("bowler_hat", CubeListBuilder.create()
						.texOffs(27, 19).addBox(-1.5F, -2.245F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
						.texOffs(23, 24).addBox(-2.5F, -0.245F, -2.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.0F, -17.755F, 2.0F, -0.1334F, -0.0933F, -0.108F));

		PartDefinition cactus_flower = arlo.addOrReplaceChild("cactus_flower", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		cactus_flower.addOrReplaceChild("cube_r1", CubeListBuilder.create()
						.texOffs(0, 30).addBox(-5.0F, -2.5F, 0.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -22.5F, 0.0F, 0.0F, 0.7854F, 0.0F));

		cactus_flower.addOrReplaceChild("cube_r2", CubeListBuilder.create()
						.texOffs(0, 30).addBox(-5.0F, -2.5F, 0.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -22.5F, 0.0F, 0.0F, -0.7854F, 0.0F));

		arlo.addOrReplaceChild("cowboy_hat", CubeListBuilder.create()
						.texOffs(42, 0).addBox(-5.0F, -6.0F, -5.0F, 10.0F, 0.0F, 10.0F, new CubeDeformation(0.0F))
						.texOffs(40, 10).addBox(-3.0F, -8.02F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.01F)),
				PartPose.offset(0.0F, -12.0F, 0.0F));

		arlo.addOrReplaceChild("crown_hat", CubeListBuilder.create()
						.texOffs(38, 18).addBox(-3.5F, -21.01F, -3.5F, 7.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		arlo.addOrReplaceChild("straw_hat", CubeListBuilder.create()
						.texOffs(-10, 37).addBox(-5.0F, -18.0F, -5.0F, 10.0F, 0.0F, 10.0F, new CubeDeformation(0.0F))
						.texOffs(20, 30).addBox(-3.0F, -21.02F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.01F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition sun_hat = arlo.addOrReplaceChild("sun_hat", CubeListBuilder.create()
						.texOffs(-11, 47).addBox(-5.5F, -18.0F, -5.5F, 11.0F, 0.0F, 11.0F, new CubeDeformation(0.0F))
						.texOffs(22, 39).addBox(-3.0F, -21.02F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.01F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		sun_hat.addOrReplaceChild("cube_r3", CubeListBuilder.create()
						.texOffs(20, 40).addBox(0.0F, 0.01F, -0.5F, 0.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(4.0F, -18.0F, -0.5F, 0.0F, 0.0F, 0.1745F));

		sun_hat.addOrReplaceChild("cube_r4", CubeListBuilder.create()
						.texOffs(20, 40).addBox(0.0F, 0.01F, -0.5F, 0.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.0F, -18.0F, -0.5F, 0.0F, 0.0F, -0.1745F));

		arlo.addOrReplaceChild("bucket_hat", CubeListBuilder.create()
						.texOffs(22, 48).addBox(-3.5F, -20.25F, -3.5F, 7.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		arlo.addOrReplaceChild("tophat", CubeListBuilder.create()
						.texOffs(-10, 58).addBox(-5.0F, -18.0F, -5.0F, 10.0F, 0.0F, 10.0F, new CubeDeformation(0.0F))
						.texOffs(20, 64).addBox(-3.0F, -24.02F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.01F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		arlo.addOrReplaceChild("pirate_hat", CubeListBuilder.create()
						.texOffs(44, 28).addBox(-3.5F, -23.0F, -3.5F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
						.texOffs(50, 40).addBox(-5.5F, -25.0F, -4.5F, 11.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		arlo.addOrReplaceChild("goggles", CubeListBuilder.create()
						.texOffs(50, 56).addBox(-3.0F, -16.5F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.01F))
						.texOffs(50, 65).addBox(-3.0F, -16.5F, -4.01F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
						.texOffs(62, 65).addBox(-4.0F, -15.5F, -4.01F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
						.texOffs(50, 67).addBox(-3.0F, -14.5F, -4.01F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
						.texOffs(56, 65).addBox(1.0F, -16.5F, -4.01F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
						.texOffs(56, 67).addBox(1.0F, -14.5F, -4.01F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -2.5F, 0.0F));

		arlo.addOrReplaceChild("prismarine_pipis", CubeListBuilder.create()
						.texOffs(44, 69).addBox(-3.0F, -1.5F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.25F)),
				PartPose.offset(0.0F, -19.5F, 0.0F));

		PartDefinition stop_sign = arm.addOrReplaceChild("stop_sign", CubeListBuilder.create()
						.texOffs(76, 57).addBox(3.0F, -25.5F, -0.72F, 7.0F, 7.0F, 0.01F, new CubeDeformation(0.0F)),
				PartPose.offset(-3.0F, 9.5F, 0.0F));

		stop_sign.addOrReplaceChild("cube_r5", CubeListBuilder.create()
						.texOffs(91, 57).addBox(-1.0F, -9.0F, 0.0F, 2.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(6.5F, -15.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		stop_sign.addOrReplaceChild("cube_r6", CubeListBuilder.create()
						.texOffs(91, 57).addBox(-1.0F, -9.0F, 0.0F, 2.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(6.5F, -15.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		return LayerDefinition.create(modelData, 128, 128);
	}

	public void setAngles(ArloBlockEntityRenderState renderState) {
		this.root.getAllParts().forEach(part -> {
			part.resetPose();
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
		this.goggles.visible = false;
		this.prismarine_pipis.visible = false;
		this.stop_sign.visible = false;

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
			case "arlo-the-little-guy:goggles" -> this.goggles.visible = true;
			case "arlo-the-little-guy:prismarine_pipis" -> this.prismarine_pipis.visible = true;
			case "arlo-the-little-guy:stop_sign" -> this.stop_sign.visible = true;
		}

		if (renderState.arloState == ArloBlockEntity.ArloState.INTERACTING) {
			this.interactAnimation.apply(renderState.interactAnimationState, renderState.age);
		} else if (renderState.arloState == ArloBlockEntity.ArloState.RANDOM_WIGGLE) {
			this.randomAnimation.apply(renderState.randomAnimationState, renderState.age);
		} else {
			this.idleAnimation.apply(renderState.idleAnimationState, renderState.age);
		}
	}

	public ModelPart getArlo() { return this.arlo; }
	public ModelPart getArm() { return this.arm; }
	public ModelPart getBowlerHat() { return this.bowler_hat; }
	public ModelPart getCactusFlower() { return this.cactus_flower; }
	public ModelPart getCowboyHat() { return this.cowboy_hat; }
	public ModelPart getCrownHat() { return this.crown_hat; }
	public ModelPart getStrawHat() { return this.straw_hat; }
	public ModelPart getSunHat() { return this.sun_hat; }
	public ModelPart getBucketHat() { return this.bucket_hat; }
	public ModelPart getTophat() { return this.tophat; }
	public ModelPart getPirateHat() { return this.pirate_hat; }
	public ModelPart getGoggles() { return this.goggles; }
	public ModelPart getPrismarinePipis() { return this.prismarine_pipis; }
	public ModelPart getStopSign() { return this.stop_sign; }
	public ModelPart getRoot() { return this.root; }
}
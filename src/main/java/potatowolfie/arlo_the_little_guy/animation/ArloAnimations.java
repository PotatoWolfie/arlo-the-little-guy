package potatowolfie.arlo_the_little_guy.animation;

import net.minecraft.client.render.entity.animation.AnimationDefinition;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

/**
 * Made with Blockbench 5.0.7
 * Exported for Minecraft version 1.19 or later with Yarn mappings
 * @author PotatoWolfie
 */
public class ArloAnimations {
	public static final AnimationDefinition ARLO_SHAKE = AnimationDefinition.Builder.create(1.0F)
			.addBoneAnimation("arlo", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0.0F, -15.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 12.5F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.7917F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("arm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0833F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0F, -22.5F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167F, AnimationHelper.createRotationalVector(0.0F, -22.5F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5833F, AnimationHelper.createRotationalVector(0.0F, 22.5F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(0.0F, 22.5F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.7917F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition ARLO_IDLE = AnimationDefinition.Builder.create(1.0F).looping()
			.addBoneAnimation("arlo", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("arm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition ARLO_WIGGLE = AnimationDefinition.Builder.create(1.0F)
			.addBoneAnimation("arlo", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0.0F, -1.5F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 1.5F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.7917F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("arm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0417F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(0.0F, -1.5F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167F, AnimationHelper.createRotationalVector(0.0F, -3.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5417F, AnimationHelper.createRotationalVector(0.0F, -2.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.7917F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.build();
}
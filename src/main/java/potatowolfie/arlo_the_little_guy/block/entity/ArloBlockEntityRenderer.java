package potatowolfie.arlo_the_little_guy.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;
import potatowolfie.arlo_the_little_guy.block.custom.MiniCactusBlock;
import potatowolfie.arlo_the_little_guy.entity.client.ModEntityModelLayers;

@Environment(EnvType.CLIENT)
public class ArloBlockEntityRenderer implements BlockEntityRenderer<ArloBlockEntity, ArloBlockEntityRenderState> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, "textures/entity/arlo/arlo.png");
    private final ArloBlockEntityModel model;

    public ArloBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.model = new ArloBlockEntityModel(ctx.bakeLayer(ModEntityModelLayers.ARLO));
    }

    @Override
    public ArloBlockEntityRenderState createRenderState() {
        return new ArloBlockEntityRenderState();
    }

    @Override
    public void submit(ArloBlockEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        if (state.hatType == null || state.hatType.equals("none")) {
            return;
        }

        poseStack.pushPose();
        Direction facing = state.facing;
        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));

        switch (facing) {
            case NORTH -> poseStack.mulPose(Axis.YP.rotationDegrees(270));
            case EAST -> poseStack.mulPose(Axis.YP.rotationDegrees(0));
            case SOUTH -> poseStack.mulPose(Axis.YP.rotationDegrees(90));
            case WEST -> poseStack.mulPose(Axis.YP.rotationDegrees(180));
        }

        RenderType renderLayer = RenderTypes.armorCutoutNoCull(TEXTURE);
        final ArloBlockEntityRenderState capturedState = state;

        final int light = state.lightCoords;

        submitNodeCollector.submitCustomGeometry(poseStack, renderLayer, (poseStackEntry, vertexConsumer) -> {
            model.setAngles(capturedState);

            PoseStack tempMatrices = new PoseStack();
            tempMatrices.last().pose().set(poseStackEntry.pose());
            tempMatrices.last().normal().set(poseStackEntry.normal());

            model.root().render(tempMatrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, -1);
        });

        if (state.breakProgress != null) {
            RenderType crumblingLayer = ModelBakery.DESTROY_TYPES.get(state.breakProgress.progress());

            submitNodeCollector.submitCustomGeometry(poseStack, crumblingLayer, (poseStackEntry, vertexConsumer) -> {
                VertexConsumer overlayConsumer = new SheetedDecalTextureGenerator(
                        vertexConsumer,
                        state.breakProgress.cameraPose(),
                        1.0F
                );

                model.setAngles(capturedState);

                PoseStack tempMatrices = new PoseStack();
                tempMatrices.last().pose().set(poseStackEntry.pose());
                tempMatrices.last().normal().set(poseStackEntry.normal());

                model.root().render(tempMatrices, overlayConsumer, light, OverlayTexture.NO_OVERLAY, -1);
            });
        }

        poseStack.popPose();
    }

    @Override
    public void extractRenderState(ArloBlockEntity entity, ArloBlockEntityRenderState renderState,
                                   float tickProgress, Vec3 cameraPos,
                                   ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderState.extractBase(entity, renderState, crumblingOverlay);

        BlockState blockState = entity.getBlockState();
        if (blockState.hasProperty(MiniCactusBlock.FACING)) {
            renderState.facing = blockState.getValue(MiniCactusBlock.FACING);
        }

        renderState.interactAnimationState.copyFrom(entity.interactAnimationState);
        renderState.idleAnimationState.copyFrom(entity.idleAnimationState);
        renderState.randomAnimationState.copyFrom(entity.randomAnimationState);
        renderState.hatType = entity.getHatType();
        renderState.arloState = entity.getArloState();
        renderState.interactAnimationStateTimer = entity.getInteractAnimationStateTimer();
        renderState.randomAnimationStateTimer = entity.getRandomAnimationStateTimer();

        if (entity.getLevel() != null) {
            renderState.age = entity.getLevel().getGameTime() + tickProgress;
        }
    }
}
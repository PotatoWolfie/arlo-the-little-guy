package potatowolfie.arlo_the_little_guy.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.model.ModelBaker;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;
import potatowolfie.arlo_the_little_guy.block.custom.MiniCactusBlock;
import potatowolfie.arlo_the_little_guy.entity.client.ModEntityModelLayers;

@Environment(EnvType.CLIENT)
public class ArloBlockEntityRenderer implements BlockEntityRenderer<ArloBlockEntity, ArloBlockEntityRenderState> {
    private static final Identifier TEXTURE = Identifier.of(ArloTheLittleGuy.MOD_ID, "textures/entity/arlo/arlo.png");
    private final ArloBlockEntityModel model;

    public ArloBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.model = new ArloBlockEntityModel(ctx.getLayerModelPart(ModEntityModelLayers.ARLO));
    }

    @Override
    public ArloBlockEntityRenderState createRenderState() {
        return new ArloBlockEntityRenderState();
    }

    @Override
    public void updateRenderState(ArloBlockEntity entity, ArloBlockEntityRenderState renderState,
                                  float tickProgress, Vec3d cameraPos,
                                  ModelCommandRenderer.@Nullable CrumblingOverlayCommand crumblingOverlay) {
        BlockEntityRenderState.updateBlockEntityRenderState(entity, renderState, crumblingOverlay);

        renderState.interactAnimationState.copyFrom(entity.interactAnimationState);
        renderState.idleAnimationState.copyFrom(entity.idleAnimationState);
        renderState.randomAnimationState.copyFrom(entity.randomAnimationState);
        renderState.hatType = entity.getHatType();
        renderState.arloState = entity.getArloState();
        renderState.interactAnimationStateTimer = entity.getInteractAnimationStateTimer();
        renderState.randomAnimationStateTimer = entity.getRandomAnimationStateTimer();
        if (entity.getWorld() != null) {
            renderState.age = entity.getWorld().getTime() + tickProgress;
            renderState.light = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos());
        }
    }

    @Override
    public void render(ArloBlockEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        if (state.hatType == null || state.hatType.equals("none")) {
            return;
        }

        matrices.push();
        Direction facing = state.blockState.get(MiniCactusBlock.FACING);
        matrices.translate(0.5, 1.5, 0.5);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));

        switch (facing) {
            case NORTH -> matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270));
            case EAST -> matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(0));
            case SOUTH -> matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
            case WEST -> matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
        }

        RenderLayer renderLayer = RenderLayers.entityCutoutNoCull(TEXTURE);
        final ArloBlockEntityRenderState capturedState = state;
        final int light = state.light;

        queue.submitCustom(matrices, renderLayer, (matricesEntry, vertexConsumer) -> {
            model.setAngles(capturedState);

            MatrixStack tempMatrices = new MatrixStack();
            tempMatrices.peek().getPositionMatrix().set(matricesEntry.getPositionMatrix());
            tempMatrices.peek().getNormalMatrix().set(matricesEntry.getNormalMatrix());

            model.getRootPart().render(tempMatrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, -1);
        });

        if (state.crumblingOverlay != null) {
            RenderLayer crumblingLayer = ModelBaker.BLOCK_DESTRUCTION_RENDER_LAYERS.get(state.crumblingOverlay.progress());

            queue.submitCustom(matrices, crumblingLayer, (matricesEntry, vertexConsumer) -> {
                VertexConsumer overlayConsumer = new OverlayVertexConsumer(
                        vertexConsumer,
                        state.crumblingOverlay.cameraMatricesEntry(),
                        1.0F
                );

                model.setAngles(capturedState);

                MatrixStack tempMatrices = new MatrixStack();
                tempMatrices.peek().getPositionMatrix().set(matricesEntry.getPositionMatrix());
                tempMatrices.peek().getNormalMatrix().set(matricesEntry.getNormalMatrix());

                model.getRootPart().render(tempMatrices, overlayConsumer, light, OverlayTexture.DEFAULT_UV, -1);
            });
        }

        matrices.pop();
    }
}
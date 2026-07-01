package potatowolfie.arlo_the_little_guy;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.animal.equine.AbstractEquineModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.GlowParticle;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import potatowolfie.arlo_the_little_guy.block.ModBlocks;
import potatowolfie.arlo_the_little_guy.block.entity.ArloBlockEntityRenderer;
import potatowolfie.arlo_the_little_guy.block.entity.ModBlockEntities;
import potatowolfie.arlo_the_little_guy.client.light.FlashlightDynamicLight;
import potatowolfie.arlo_the_little_guy.entity.ModEntities;
import potatowolfie.arlo_the_little_guy.entity.bowler_hat.BowlerHatArmorRenderer;
import potatowolfie.arlo_the_little_guy.entity.bowler_hat.BowlerHatModel;
import potatowolfie.arlo_the_little_guy.entity.prismarine_pipis.PrismarinePipisArmorRenderer;
import potatowolfie.arlo_the_little_guy.entity.prismarine_pipis.PrismarinePipisModel;
import potatowolfie.arlo_the_little_guy.entity.cactus_horse.CactusHorseRenderer;
import potatowolfie.arlo_the_little_guy.entity.client.ModEntityModelLayers;
import potatowolfie.arlo_the_little_guy.entity.cowboy_hat.CowboyHatArmorRenderer;
import potatowolfie.arlo_the_little_guy.entity.cowboy_hat.CowboyHatModel;
import potatowolfie.arlo_the_little_guy.entity.crown.CrownArmorRenderer;
import potatowolfie.arlo_the_little_guy.entity.crown.CrownModel;
import potatowolfie.arlo_the_little_guy.entity.crown.PigCrownModel;
import potatowolfie.arlo_the_little_guy.entity.straw_hat.StrawHatArmorRenderer;
import potatowolfie.arlo_the_little_guy.entity.straw_hat.StrawHatModel;
import potatowolfie.arlo_the_little_guy.entity.sun_hat.SunHatArmorRenderer;
import potatowolfie.arlo_the_little_guy.entity.sun_hat.SunHatModel;
import potatowolfie.arlo_the_little_guy.entity.top_hat.SnowGolemTopHatModel;
import potatowolfie.arlo_the_little_guy.entity.top_hat.TopHatArmorRenderer;
import potatowolfie.arlo_the_little_guy.entity.top_hat.TopHatModel;
import potatowolfie.arlo_the_little_guy.entity.tricorn.TricornHatArmorRenderer;
import potatowolfie.arlo_the_little_guy.entity.tricorn.TricornHatModel;
import potatowolfie.arlo_the_little_guy.item.ModItems;
import potatowolfie.arlo_the_little_guy.particle.CeilingLightSparkParticle;
import potatowolfie.arlo_the_little_guy.sound.ArlroomsAmbientSound;
import potatowolfie.arlo_the_little_guy.structure.arlrooms.ArlroomsClientEvents;
import potatowolfie.arlo_the_little_guy.structure.arlrooms.DoorLookPayload;
import potatowolfie.arlo_the_little_guy.world.dimension.ModDimensions;

import java.util.HashMap;
import java.util.Map;

public class ArloTheLittleGuyClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModEntityModelLayers.registerModelLayers();

        ParticleProviderRegistry.getInstance().register(ArloTheLittleGuy.CEILING_LIGHT_SPARK, CeilingLightSparkParticle.Provider::new);

        final ArlroomsAmbientSound[] ambientSound = {null};

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            if (ambientSound[0] != null) {
                client.getSoundManager().stop(ambientSound[0]);
                ambientSound[0] = null;
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.level == null) {
                return;
            }

            boolean inArlrooms = client.level.dimension() == ModDimensions.ARLROOMS_LEVEL_KEY;
            SoundManager soundManager = client.getSoundManager();

            if (inArlrooms) {
                if (ambientSound[0] == null || !soundManager.isActive(ambientSound[0])) {
                    ambientSound[0] = new ArlroomsAmbientSound();
                    soundManager.play(ambientSound[0]);
                }
            } else {
                if (ambientSound[0] != null) {
                    soundManager.stop(ambientSound[0]);
                    ambientSound[0] = null;
                }
            }
        });

        ModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.BOWLER_HAT,
                BowlerHatModel::getTexturedModelData
        );
        ModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.TRICORN,
                TricornHatModel::getTexturedModelData
        );
        ModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.STRAW_HAT,
                StrawHatModel::getTexturedModelData
        );
        ModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.COWBOY_HAT,
                CowboyHatModel::getTexturedModelData
        );
        ModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.TOP_HAT,
                TopHatModel::getTexturedModelData
        );
        ModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.SUN_HAT,
                SunHatModel::getTexturedModelData
        );
        ModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.CROWN,
                CrownModel::getTexturedModelData
        );
        ModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.PRISMARINE_PIPIS,
                PrismarinePipisModel::getTexturedModelData
        );
        ModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.PIG_CROWN,
                PigCrownModel::getTexturedModelData
        );
        ModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.SNOW_GOLEM_TOP_HAT,
                SnowGolemTopHatModel::getTexturedModelData
        );

        ModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.CACTUS_HORSE,
                () -> LayerDefinition.create(AbstractEquineModel.createBodyMesh(CubeDeformation.NONE), 64, 64)
        );
        EntityRendererRegistry.register(ModEntities.CACTUS_HORSE, CactusHorseRenderer::new);

        BlockEntityRenderers.register(
                ModBlockEntities.ARLO_BLOCK_ENTITY,
                ArloBlockEntityRenderer::new
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            FlashlightDynamicLight.register();
        });

        final BlockPos[] lastPlayerBlock = {null};
        final long[] lastScanTick = {-1L};
        final Map<BlockPos, Long> LAST_SENT = new HashMap<>();
        final long RESEND_INTERVAL_TICKS = 40L;
        final long IDLE_RESCAN_TICKS = 60L;

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.level == null) return;

            if (client.level.dimension() != ModDimensions.ARLROOMS_LEVEL_KEY) return;

            BlockPos current = client.player.blockPosition();
            long now = client.level.getGameTime();

            boolean moved = lastPlayerBlock[0] == null || !lastPlayerBlock[0].equals(current);
            boolean idleRescanDue = lastScanTick[0] >= 0 && (now - lastScanTick[0]) >= IDLE_RESCAN_TICKS;

            if (!moved && !idleRescanDue) return;

            lastPlayerBlock[0] = current;
            lastScanTick[0] = now;

            int radiusXZ = 48;
            int radiusY = 20;

            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

            for (int x = -radiusXZ; x <= radiusXZ; x++) {
                for (int y = -radiusY; y <= radiusY; y++) {
                    for (int z = -radiusXZ; z <= radiusXZ; z++) {

                        pos.set(
                                current.getX() + x,
                                current.getY() + y,
                                current.getZ() + z
                        );

                        if (!client.level.hasChunkAt(pos)) continue;

                        BlockState state = client.level.getBlockState(pos);
                        boolean isAnyDoorMarker = state.is(ModBlocks.DOOR_MARKER)
                                || state.is(ModBlocks.POOL_ROOMS_DOOR_MARKER);

                        if (!isAnyDoorMarker) {
                            LAST_SENT.remove(pos);
                            continue;
                        }

                        BlockPos immutable = pos.immutable();
                        Long lastSent = LAST_SENT.get(immutable);

                        if (lastSent != null && (now - lastSent) < RESEND_INTERVAL_TICKS) {
                            continue;
                        }

                        LAST_SENT.put(immutable, now);
                        ClientPlayNetworking.send(new DoorLookPayload(immutable));
                    }
                }
            }
        });

        ArmorRenderer.register(new BowlerHatArmorRenderer(), ModItems.BOWLER_HAT);
        ArmorRenderer.register(new TricornHatArmorRenderer(), ModItems.TRICORN);
        ArmorRenderer.register(new StrawHatArmorRenderer(), ModItems.STRAW_HAT);
        ArmorRenderer.register(new CowboyHatArmorRenderer(), ModItems.COWBOY_HAT);
        ArmorRenderer.register(new TopHatArmorRenderer(), ModItems.TOP_HAT);
        ArmorRenderer.register(new SunHatArmorRenderer(), ModItems.SUN_HAT);
        ArmorRenderer.register(new CrownArmorRenderer(), ModItems.CROWN);
        ArmorRenderer.register(new PrismarinePipisArmorRenderer(), ModItems.PRISMARINE_PIPIS);
    }

    private boolean isTape8(ItemStack stack) {
        if (stack == null) return false;
        return stack.getItem().toString().contains("cassette_tape_question");
    }
}
package potatowolfie.arlo_the_little_guy.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

public class NoClipSandBlock extends SandBlock {
    private static final ResourceKey<Level> ARLROOMS_DIMENSION = ResourceKey.create(
            Registries.DIMENSION,
            Identifier.fromNamespaceAndPath("arlo-the-little-guy", "arlrooms")
    );

    public NoClipSandBlock(ColorRGBA dustColor, Properties properties) {
        super(dustColor, properties);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (FallingBlock.isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinY()) {

            FallingBlockEntity entity =
                    FallingBlockEntity.fall(level, pos, state);

            entity.disableDrop();
        }
    }

    @Override
    public void onBrokenAfterFall(Level level, BlockPos pos, FallingBlockEntity entity) {
        Vec3 centerOfEntity = entity.getBoundingBox().getCenter();

        level.levelEvent(2001, BlockPos.containing(centerOfEntity), Block.getId(entity.getBlockState()));
        level.gameEvent(entity, GameEvent.BLOCK_DESTROY, centerOfEntity);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(16) == 0) {
            BlockPos below = pos.below();
            if (FallingBlock.isFree(level.getBlockState(below))) {
                double xx = (double)pos.getX() + random.nextDouble();
                double yy = (double)pos.getY() - 0.05;
                double zz = (double)pos.getZ() + random.nextDouble();

                level.addParticle(
                        new BlockParticleOption(ParticleTypes.FALLING_DUST, state),
                        xx, yy, zz,
                        0.0, 0.0, 0.0
                );
            }
        }
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos,
                                Entity entity, InsideBlockEffectApplier effectApplier,
                                boolean isPrecise) {

        if (!level.isClientSide() && entity.level() instanceof ServerLevel currentServerLevel) {

            BlockPos entityPos = entity.blockPosition();
            BlockState blockAboveFeet = level.getBlockState(entityPos.above());

            if (blockAboveFeet.is(this)) {

                if (currentServerLevel.dimension().equals(ARLROOMS_DIMENSION)) {

                    if (entity instanceof ServerPlayer player) {

                        TeleportTransition respawnTransition =
                                player.findRespawnPositionAndUseSpawnBlock(
                                        false,
                                        TeleportTransition.DO_NOTHING
                                );

                        if (respawnTransition != null) {
                            player.teleport(respawnTransition);
                        } else {
                            ServerLevel overworld =
                                    currentServerLevel.getServer().getLevel(Level.OVERWORLD);

                            if (overworld != null) {
                                LevelData.RespawnData respawnData = overworld.getRespawnData();

                                player.teleport(
                                        new TeleportTransition(
                                                overworld,
                                                Vec3.atBottomCenterOf(respawnData.pos()),
                                                Vec3.ZERO,
                                                respawnData.yaw(),
                                                respawnData.pitch(),
                                                Set.of(),
                                                TeleportTransition.DO_NOTHING
                                        )
                                );
                            }
                        }
                    } else {

                        ServerLevel overworld =
                                currentServerLevel.getServer().getLevel(Level.OVERWORLD);

                        if (overworld != null) {
                            entity.teleport(
                                    new TeleportTransition(
                                            overworld,
                                            Vec3.atBottomCenterOf(overworld.getRespawnData().pos()),
                                            Vec3.ZERO,
                                            entity.getYRot(),
                                            entity.getXRot(),
                                            Set.of(),
                                            TeleportTransition.DO_NOTHING
                                    )
                            );
                        }
                    }

                } else {

                    ServerLevel targetWorld =
                            currentServerLevel.getServer().getLevel(ARLROOMS_DIMENSION);

                    if (targetWorld != null) {
                        entity.teleport(
                                new TeleportTransition(
                                        targetWorld,
                                        new Vec3(0.5, 80.0, 0.5),
                                        Vec3.ZERO,
                                        entity.getYRot(),
                                        entity.getXRot(),
                                        Set.of(),
                                        TeleportTransition.DO_NOTHING
                                )
                        );
                    }
                }
            }
        }

        super.entityInside(state, level, pos, entity, effectApplier, isPrecise);
    }
}
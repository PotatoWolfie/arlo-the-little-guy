package potatowolfie.arlo_the_little_guy.world.feature.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import potatowolfie.arlo_the_little_guy.block.ModBlocks;
import potatowolfie.arlo_the_little_guy.block.custom.MiniCactusBlock;
import potatowolfie.arlo_the_little_guy.block.entity.ArloBlockEntity;

public record ArlroomsEntranceFeature() implements Feature {
    public static final MapCodec<ArlroomsEntranceFeature> CODEC = MapCodec.unit(ArlroomsEntranceFeature::new);

    private static boolean isCorner(int dx, int dz) {
        return Math.abs(dx) == 2 && Math.abs(dz) == 2;
    }

    public MapCodec<ArlroomsEntranceFeature> codec() {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel world, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
        FluidState fluidAtOrigin = world.getFluidState(origin);
        FluidState fluidBelow = world.getFluidState(origin.below());
        if (fluidAtOrigin.is(Fluids.WATER) || fluidAtOrigin.is(Fluids.FLOWING_WATER)
                || fluidBelow.is(Fluids.WATER) || fluidBelow.is(Fluids.FLOWING_WATER)) {
            return false;
        }

        int expectedY = origin.getY();
        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                int surfaceY = world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                        origin.getX() + dx, origin.getZ() + dz);
                if (surfaceY != expectedY) {
                    return false;
                }
            }
        }

        for (int layer = 1; layer <= 2; layer++) {
            for (int dx = -2; dx <= 2; dx++) {
                for (int dz = -2; dz <= 2; dz++) {
                    if (isCorner(dx, dz)) continue;
                    world.setBlock(origin.offset(dx, -layer, dz),
                            ModBlocks.NO_CLIP_SAND.defaultBlockState(), 3);
                }
            }
        }

        Direction[] horizontalDirections = {
                Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST
        };
        Direction facing = horizontalDirections[random.nextInt(horizontalDirections.length)];

        BlockState cactusState = ModBlocks.MINI_CACTUS.defaultBlockState()
                .setValue(MiniCactusBlock.FACING, facing)
                .setValue(MiniCactusBlock.HAS_HAT, true);

        if (!cactusState.canSurvive(world, origin)) {
            return false;
        }

        if (!world.setBlock(origin, cactusState, 3)) {
            return false;
        }

        BlockEntity blockEntity = world.getBlockEntity(origin);
        if (blockEntity instanceof ArloBlockEntity arloEntity) {
            arloEntity.setHatType("arlo-the-little-guy:stop_sign");
        }

        return true;
    }
}
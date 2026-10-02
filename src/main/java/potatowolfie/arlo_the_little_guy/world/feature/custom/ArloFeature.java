package potatowolfie.arlo_the_little_guy.world.feature.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import potatowolfie.arlo_the_little_guy.block.ModBlocks;
import potatowolfie.arlo_the_little_guy.block.custom.MiniCactusBlock;
import potatowolfie.arlo_the_little_guy.block.entity.ArloBlockEntity;

public record ArloFeature() implements Feature {
    public static final MapCodec<ArloFeature> CODEC = MapCodec.unit(ArloFeature::new);

    private static final WeightedHat[] HATS = {
            new WeightedHat("arlo-the-little-guy:bowler_hat", 20),
            new WeightedHat("minecraft:cactus_flower", 20),
            new WeightedHat("arlo-the-little-guy:cowboy_hat", 15),
            new WeightedHat("arlo-the-little-guy:tricorn", 10),
            new WeightedHat("arlo-the-little-guy:sun_hat", 10),
            new WeightedHat("arlo-the-little-guy:straw_hat", 10),
            new WeightedHat("arlo-the-little-guy:top_hat", 5),
            new WeightedHat("arlo-the-little-guy:crown", 5),
            new WeightedHat("minecraft:bucket", 5)
    };

    public MapCodec<ArloFeature> codec() {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel world, ChunkGenerator chunkGenerator, RandomSource random, BlockPos pos) {
        BlockState state = ModBlocks.MINI_CACTUS.defaultBlockState();

        if (!state.canSurvive(world, pos)) {
            return false;
        }

        if (world.getBlockState(pos).liquid()) {
            return false;
        }

        if (state.hasProperty(MiniCactusBlock.FACING)) {
            Direction[] horizontalDirections = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
            Direction randomDirection = horizontalDirections[random.nextInt(horizontalDirections.length)];
            state = state.setValue(MiniCactusBlock.FACING, randomDirection);
        }

        boolean hasHat = random.nextFloat() < 0.05f;
        if (hasHat) {
            state = state.setValue(MiniCactusBlock.HAS_HAT, true);
        }

        if (!world.setBlock(pos, state, 3)) {
            return false;
        }

        if (hasHat) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ArloBlockEntity arloEntity) {
                arloEntity.setHatType(selectRandomHat(random));
            }
        }

        return true;
    }

    private String selectRandomHat(RandomSource random) {
        int roll = random.nextInt(100);
        int cumulative = 0;

        for (WeightedHat hat : HATS) {
            cumulative += hat.weight;
            if (roll < cumulative) {
                return hat.hatId;
            }
        }

        return HATS[0].hatId;
    }

    private static class WeightedHat {
        final String hatId;
        final int weight;

        WeightedHat(String hatId, int weight) {
            this.hatId = hatId;
            this.weight = weight;
        }
    }
}
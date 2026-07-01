package potatowolfie.arlo_the_little_guy.world.feature.custom;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import potatowolfie.arlo_the_little_guy.block.custom.MiniCactusBlock;
import potatowolfie.arlo_the_little_guy.block.entity.ArloBlockEntity;

public class ArloFeature extends Feature<SimpleBlockConfiguration> {
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

    public ArloFeature(Codec<SimpleBlockConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();
        SimpleBlockConfiguration config = context.config();

        BlockState state = config.toPlace().getState(world, random, pos);

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
                String hatType = selectRandomHat(random);
                arloEntity.setHatType(hatType);
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
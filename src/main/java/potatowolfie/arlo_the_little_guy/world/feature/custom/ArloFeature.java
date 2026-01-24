package potatowolfie.arlo_the_little_guy.world.feature.custom;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import potatowolfie.arlo_the_little_guy.block.custom.MiniCactusBlock;
import potatowolfie.arlo_the_little_guy.block.entity.ArloBlockEntity;

public class ArloFeature extends Feature<SimpleBlockFeatureConfig> {
    private static final WeightedHat[] HATS = {
            new WeightedHat("arlo-the-little-guy:bowler_hat", 20),
            new WeightedHat("minecraft:cactus_flower", 20),
            new WeightedHat("arlo-the-little-guy:cowboy_hat", 15),
            new WeightedHat("arlo-the-little-guy:tricorn", 15),
            new WeightedHat("arlo-the-little-guy:sun_hat", 10),
            new WeightedHat("arlo-the-little-guy:top_hat", 10),
            new WeightedHat("arlo-the-little-guy:straw_hat", 10),
            new WeightedHat("arlo-the-little-guy:crown", 5),
            new WeightedHat("minecraft:bucket", 5)
    };

    public ArloFeature(Codec<SimpleBlockFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<SimpleBlockFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = context.getRandom();
        SimpleBlockFeatureConfig config = context.getConfig();

        BlockState state = config.toPlace().get(random, pos);

        if (state.contains(MiniCactusBlock.FACING)) {
            Direction[] horizontalDirections = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
            Direction randomDirection = horizontalDirections[random.nextInt(horizontalDirections.length)];
            state = state.with(MiniCactusBlock.FACING, randomDirection);
        }

        boolean hasHat = random.nextFloat() < 0.05f;

        if (hasHat) {
            state = state.with(MiniCactusBlock.HAS_HAT, true);
        }

        if (!world.setBlockState(pos, state, 3)) {
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

    private String selectRandomHat(Random random) {
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
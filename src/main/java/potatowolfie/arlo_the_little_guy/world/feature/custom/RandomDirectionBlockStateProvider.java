package potatowolfie.arlo_the_little_guy.world.feature.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import potatowolfie.arlo_the_little_guy.block.custom.MiniCactusBlock;

public record RandomDirectionBlockStateProvider(BlockStateProvider source) implements BlockStateProvider {
    public static final MapCodec<RandomDirectionBlockStateProvider> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                            BlockStateProvider.DIRECT_CODEC.fieldOf("source")
                                    .forGetter(RandomDirectionBlockStateProvider::source)
                    ).apply(instance, RandomDirectionBlockStateProvider::new)
            );

    private static final Direction[] HORIZONTALS = {
            Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST
    };

    @Override
    public MapCodec<? extends BlockStateProvider> codec() {
        return CODEC;
    }

    @Override
    public BlockState getState(LevelAccessor level, RandomSource random, BlockPos pos) {
        BlockState state = this.source.getState(level, random, pos);

        if (state.hasProperty(MiniCactusBlock.FACING)) {
            state = state.setValue(MiniCactusBlock.FACING, HORIZONTALS[random.nextInt(HORIZONTALS.length)]);
        }

        return state;
    }
}
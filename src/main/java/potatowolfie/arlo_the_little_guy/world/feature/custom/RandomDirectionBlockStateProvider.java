package potatowolfie.arlo_the_little_guy.world.feature.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import potatowolfie.arlo_the_little_guy.block.custom.MiniCactusBlock;

public class RandomDirectionBlockStateProvider extends BlockStateProvider {
    public static final MapCodec<RandomDirectionBlockStateProvider> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                            BlockStateProvider.CODEC.fieldOf("source").forGetter(provider -> provider.source)
                    ).apply(instance, RandomDirectionBlockStateProvider::new)
            );

    private final BlockStateProvider source;

    public RandomDirectionBlockStateProvider(BlockStateProvider source) {
        this.source = source;
    }

    @Override
    protected BlockStateProviderType<?> type() {
        return ModBlockStateProviderTypes.RANDOM_DIRECTION;
    }

    @Override
    public BlockState getState(WorldGenLevel level, RandomSource random, BlockPos pos) {
        BlockState state = this.source.getState(level, random, pos);

        if (state.hasProperty(MiniCactusBlock.FACING)) {
            Direction[] horizontalDirections = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
            Direction randomDirection = horizontalDirections[random.nextInt(horizontalDirections.length)];
            state = state.setValue(MiniCactusBlock.FACING, randomDirection);
        }

        return state;
    }
}
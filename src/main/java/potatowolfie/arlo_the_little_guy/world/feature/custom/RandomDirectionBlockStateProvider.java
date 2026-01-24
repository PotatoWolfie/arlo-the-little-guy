package potatowolfie.arlo_the_little_guy.world.feature.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;
import potatowolfie.arlo_the_little_guy.block.custom.MiniCactusBlock;

public class RandomDirectionBlockStateProvider extends BlockStateProvider {
    public static final MapCodec<RandomDirectionBlockStateProvider> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                            BlockStateProvider.TYPE_CODEC.fieldOf("source").forGetter(provider -> provider.source)
                    ).apply(instance, RandomDirectionBlockStateProvider::new)
            );

    private final BlockStateProvider source;

    public RandomDirectionBlockStateProvider(BlockStateProvider source) {
        this.source = source;
    }

    @Override
    protected BlockStateProviderType<?> getType() {
        return ModBlockStateProviderTypes.RANDOM_DIRECTION;
    }

    @Override
    public BlockState get(Random random, BlockPos pos) {
        BlockState state = this.source.get(random, pos);

        if (state.contains(MiniCactusBlock.FACING)) {
            Direction[] horizontalDirections = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
            Direction randomDirection = horizontalDirections[random.nextInt(horizontalDirections.length)];
            state = state.with(MiniCactusBlock.FACING, randomDirection);
        }

        return state;
    }
}
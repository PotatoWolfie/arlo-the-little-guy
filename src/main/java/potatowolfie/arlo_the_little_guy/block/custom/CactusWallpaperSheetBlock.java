package potatowolfie.arlo_the_little_guy.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CactusWallpaperSheetBlock extends MultifaceBlock {
    public static final MapCodec<CactusWallpaperSheetBlock> CODEC = simpleCodec(CactusWallpaperSheetBlock::new);

    private static final VoxelShape DOWN_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
    private static final VoxelShape UP_SHAPE = Block.box(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape NORTH_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape SOUTH_SHAPE = Block.box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    private static final VoxelShape WEST_SHAPE = Block.box(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape EAST_SHAPE = Block.box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);

    public CactusWallpaperSheetBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<CactusWallpaperSheetBlock> codec() {
        return CODEC;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        VoxelShape shape = Shapes.empty();

        if (state.getValue(BlockStateProperties.DOWN)) {
            shape = Shapes.or(shape, DOWN_SHAPE);
        }

        if (state.getValue(BlockStateProperties.UP)) {
            shape = Shapes.or(shape, UP_SHAPE);
        }

        if (state.getValue(BlockStateProperties.NORTH)) {
            shape = Shapes.or(shape, NORTH_SHAPE);
        }

        if (state.getValue(BlockStateProperties.SOUTH)) {
            shape = Shapes.or(shape, SOUTH_SHAPE);
        }

        if (state.getValue(BlockStateProperties.WEST)) {
            shape = Shapes.or(shape, WEST_SHAPE);
        }

        if (state.getValue(BlockStateProperties.EAST)) {
            shape = Shapes.or(shape, EAST_SHAPE);
        }

        return shape.isEmpty() ? DOWN_SHAPE : shape;
    }

}
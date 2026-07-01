package potatowolfie.arlo_the_little_guy.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;

import java.util.EnumMap;
import java.util.Map;

public class StopSignBlock extends Block {

    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WALL = BooleanProperty.create("wall");

    private static final Map<Direction, VoxelShape> WALL_SHAPES = new EnumMap<>(Direction.class);
    private static final Map<Direction, VoxelShape> POST_COLLISION_SHAPES = new EnumMap<>(Direction.class);
    private static final Map<Direction, VoxelShape> POST_OUTLINE_SHAPES = new EnumMap<>(Direction.class);

    static {
        for (Direction facing : Direction.Plane.HORIZONTAL) {
            WALL_SHAPES.put(facing, makeWallShape(facing));
            POST_COLLISION_SHAPES.put(facing, makePostCollisionShape(facing));
            POST_OUTLINE_SHAPES.put(facing, makePostOutlineShape(facing));
        }
    }

    public StopSignBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WALL, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WALL);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction clickedFace = context.getClickedFace();

        if (clickedFace.getAxis().isHorizontal()) {
            return this.defaultBlockState()
                    .setValue(WALL, true)
                    .setValue(FACING, clickedFace);
        }

        return this.defaultBlockState()
                .setValue(WALL, false)
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        return state.getValue(WALL) ? WALL_SHAPES.get(facing) : POST_OUTLINE_SHAPES.get(facing);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        return state.getValue(WALL) ? WALL_SHAPES.get(facing) : POST_COLLISION_SHAPES.get(facing);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    private static VoxelShape makeWallShape(Direction facing) {
        return switch (facing) {
            case SOUTH -> Shapes.box(0.5 / 16, 0.5 / 16, 0.0 / 16, 15.5 / 16, 15.5 / 16, 1.0 / 16);
            case NORTH -> Shapes.box(0.5 / 16, 0.5 / 16, 15.0 / 16, 15.5 / 16, 15.5 / 16, 16.0 / 16);
            case WEST -> Shapes.box(15.0 / 16, 0.5 / 16, 0.5 / 16, 16.0 / 16, 15.5 / 16, 15.5 / 16);
            case EAST -> Shapes.box(0.0 / 16, 0.5 / 16, 0.5 / 16, 1.0 / 16, 15.5 / 16, 15.5 / 16);
            default -> Shapes.box(0.5 / 16, 0.5 / 16, 0.0 / 16, 15.5 / 16, 15.5 / 16, 1.0 / 16);
        };
    }

    private static VoxelShape makePostCollisionShape(Direction facing) {
        VoxelShape post = Shapes.box(7.0 / 16, 0.0 / 16, 7.0 / 16, 9.0 / 16, 16.0 / 16, 9.0 / 16);

        VoxelShape sign = switch (facing) {
            case SOUTH -> Shapes.box(0.5 / 16, 1.0 / 16, 9.0 / 16, 15.5 / 16, 16.0 / 16, 10.0 / 16);
            case NORTH -> Shapes.box(0.5 / 16, 1.0 / 16, 6.0 / 16, 15.5 / 16, 16.0 / 16, 7.0 / 16);
            case EAST -> Shapes.box(9.0 / 16, 1.0 / 16, 0.5 / 16, 10.0 / 16, 16.0 / 16, 15.5 / 16);
            case WEST -> Shapes.box(6.0 / 16, 1.0 / 16, 0.5 / 16, 7.0 / 16, 16.0 / 16, 15.5 / 16);
            default -> Shapes.box(0.5 / 16, 1.0 / 16, 9.0 / 16, 15.5 / 16, 16.0 / 16, 10.0 / 16);
        };

        return Shapes.or(post, sign);
    }

    private static VoxelShape makePostOutlineShape(Direction facing) {
        return switch (facing) {
            case NORTH, SOUTH -> Shapes.box(0.5 / 16, 0.0 / 16, 7.0 / 16, 15.5 / 16, 16.0 / 16, 9.0 / 16);
            case EAST, WEST -> Shapes.box(7.0 / 16, 0.0 / 16, 0.5 / 16, 9.0 / 16, 16.0 / 16, 15.5 / 16);
            default -> Shapes.box(0.5 / 16, 0.0 / 16, 7.0 / 16, 15.5 / 16, 16.0 / 16, 9.0 / 16);
        };
    }
}
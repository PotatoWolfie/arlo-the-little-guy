package potatowolfie.arlo_the_little_guy.block.custom;

import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import potatowolfie.arlo_the_little_guy.structure.arlrooms.ArlroomsExpansionManager;

public class DoorMarkerBlock extends Block {

    public static final EnumProperty<FrontAndTop> ORIENTATION = BlockStateProperties.ORIENTATION;

    public DoorMarkerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ORIENTATION, FrontAndTop.NORTH_UP));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ORIENTATION);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction front = context.getClickedFace();
        Direction top = front.getAxis() == Direction.Axis.Y
                ? context.getHorizontalDirection().getOpposite()
                : Direction.UP;

        return this.defaultBlockState().setValue(ORIENTATION, FrontAndTop.fromFrontAndTop(front, top));
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        FrontAndTop orientation = state.getValue(ORIENTATION);

        Direction front = orientation.front();
        Direction top = orientation.top();

        if (front.getAxis().isHorizontal()) {
            front = rotation.rotate(front);
        }

        if (top.getAxis().isHorizontal()) {
            top = rotation.rotate(top);
        }

        FrontAndTop rotated = FrontAndTop.fromFrontAndTop(front, top);

        if (rotated == null) {
            ArlroomsExpansionManager.LOGGER.error(
                    "Invalid DoorMarker rotation: {} -> front={}, top={}, rotation={}",
                    orientation,
                    front,
                    top,
                    rotation
            );

            return state;
        }

        return state.setValue(ORIENTATION, rotated);
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state;
    }

    private static Direction rotateDirection(Direction dir, Rotation rot) {
        return switch (rot) {
            case NONE -> dir;
            case CLOCKWISE_90 -> dir.getClockWise();
            case CLOCKWISE_180 -> dir.getOpposite();
            case COUNTERCLOCKWISE_90 -> dir.getCounterClockWise();
        };
    }
}
package potatowolfie.arlo_the_little_guy.block.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import potatowolfie.arlo_the_little_guy.advancement.NoOneWillBelieveYouHandler;
import potatowolfie.arlo_the_little_guy.block.entity.CassetteTapePlayerBlockEntity;
import potatowolfie.arlo_the_little_guy.block.entity.ModBlockEntities;
import potatowolfie.arlo_the_little_guy.item.ModItems;
import potatowolfie.arlo_the_little_guy.sound.ModSounds;

import java.util.EnumMap;
import java.util.Map;

public class CassetteTapePlayerBlock extends BaseEntityBlock {
    public static final EnumProperty<TapeType> TAPE = EnumProperty.create("tape", TapeType.class);

    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static final MapCodec<CassetteTapePlayerBlock> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(propertiesCodec()).apply(instance, CassetteTapePlayerBlock::new)
    );

    private static final VoxelShape SHAPE_NORTH =
            Block.box(
                    4.0, 0.0, 2.5, 12.0, 3.0, 14.5
            );

    private static VoxelShape rotateShape(VoxelShape shape, Direction from, Direction to) {
        int times = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4;

        VoxelShape[] buffer = new VoxelShape[]{shape};

        for (int i = 0; i < times; i++) {
            buffer[0] = rotate90(buffer[0]);
        }

        return buffer[0];
    }

    private static VoxelShape rotate90(VoxelShape shape) {
        VoxelShape[] result = new VoxelShape[]{Shapes.empty()};

        shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
            double newMinX = 1 - maxZ;
            double newMinZ = minX;
            double newMaxX = 1 - minZ;
            double newMaxZ = maxX;

            result[0] = Shapes.or(
                    result[0],
                    Shapes.box(newMinX, minY, newMinZ, newMaxX, maxY, newMaxZ)
            );
        });

        return result[0];
    }

    private static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);

    static {
        SHAPES.put(Direction.NORTH, SHAPE_NORTH);
        SHAPES.put(Direction.SOUTH, rotateShape(SHAPE_NORTH, Direction.NORTH, Direction.SOUTH));
        SHAPES.put(Direction.WEST,  rotateShape(SHAPE_NORTH, Direction.NORTH, Direction.WEST));
        SHAPES.put(Direction.EAST,  rotateShape(SHAPE_NORTH, Direction.NORTH, Direction.EAST));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public CassetteTapePlayerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(TAPE, TapeType.EMPTY)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TAPE, FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotation().rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(FACING, mirror.rotation().rotate(state.getValue(FACING)));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        TapeType currentTape = state.getValue(TAPE);
        if (currentTape == TapeType.EMPTY) {
            return InteractionResult.PASS;
        }
        if (!level.isClientSide()) {
            ejectTape(state, level, pos, player);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        TapeType currentTape = state.getValue(TAPE);

        if (currentTape == TapeType.EMPTY) {
            TapeType insertedType = getTapeTypeFromItem(stack.getItem());

            if (insertedType != TapeType.EMPTY) {
                if (!level.isClientSide()) {
                    level.setBlock(pos, state.setValue(TAPE, insertedType), 3);
                    level.playSound(null, pos, ModSounds.CASSETTE_TAPE_INSERT, SoundSource.BLOCKS, 1.0F, 1.0F);

                    if (player instanceof ServerPlayer serverPlayer) {
                        NoOneWillBelieveYouHandler.onTapeInserted(serverPlayer, insertedType);
                    }

                    BlockEntity be = level.getBlockEntity(pos);
                    if (be instanceof CassetteTapePlayerBlockEntity recorderBE) {
                        recorderBE.startTapePlaybackDelay();
                    }

                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }
                }
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        } else {
            if (!level.isClientSide()) {
                ejectTape(state, level, pos, player);
            }
            return InteractionResult.SUCCESS;
        }
    }

    private void ejectTape(BlockState state, Level level, BlockPos pos, Player player) {
        TapeType currentTape = state.getValue(TAPE);
        Item dropItem = getItemFromTapeType(currentTape);
        if (dropItem != null) {
            player.getInventory().placeItemBackInInventory(new ItemStack(dropItem));
        }

        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof CassetteTapePlayerBlockEntity recorderBE) {
            recorderBE.stopPlayback();
        }

        level.setBlock(pos, state.setValue(TAPE, TapeType.EMPTY), 3);
        level.playSound(null, pos, ModSounds.CASSETTE_TAPE_EJECT, SoundSource.BLOCKS, 1.0F, 0.8F);
    }

    public static TapeType getTapeTypeFromItem(Item item) {
        if (item == ModItems.CASSETTE_TAPE_1) return TapeType.TAPE_1;
        if (item == ModItems.CASSETTE_TAPE_2) return TapeType.TAPE_2;
        if (item == ModItems.CASSETTE_TAPE_3) return TapeType.TAPE_3;
        if (item == ModItems.CASSETTE_TAPE_4) return TapeType.TAPE_4;
        if (item == ModItems.CASSETTE_TAPE_5) return TapeType.TAPE_5;
        if (item == ModItems.CASSETTE_TAPE_6) return TapeType.TAPE_6;
        if (item == ModItems.CASSETTE_TAPE_7) return TapeType.TAPE_7;
        if (item == ModItems.CASSETTE_TAPE_QUESTION) return TapeType.TAPE_8;
        return TapeType.EMPTY;
    }

    public static Item getItemFromTapeType(TapeType tape) {
        return switch (tape) {
            case TAPE_1 -> ModItems.CASSETTE_TAPE_1;
            case TAPE_2 -> ModItems.CASSETTE_TAPE_2;
            case TAPE_3 -> ModItems.CASSETTE_TAPE_3;
            case TAPE_4 -> ModItems.CASSETTE_TAPE_4;
            case TAPE_5 -> ModItems.CASSETTE_TAPE_5;
            case TAPE_6 -> ModItems.CASSETTE_TAPE_6;
            case TAPE_7 -> ModItems.CASSETTE_TAPE_7;
            case TAPE_8 -> ModItems.CASSETTE_TAPE_QUESTION;
            default -> null;
        };
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CassetteTapePlayerBlockEntity(pos, state);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.CASSETTE_TAPE_PLAYER, CassetteTapePlayerBlockEntity::serverTick);
    }
}
package potatowolfie.arlo_the_little_guy.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import potatowolfie.arlo_the_little_guy.advancement.BucketOfArloHandler;
import potatowolfie.arlo_the_little_guy.advancement.HappyBirthdayHandler;
import potatowolfie.arlo_the_little_guy.advancement.HatTrickHandler;
import potatowolfie.arlo_the_little_guy.block.entity.ArloBlockEntity;
import potatowolfie.arlo_the_little_guy.block.entity.ModBlockEntities;
import potatowolfie.arlo_the_little_guy.util.ModTags;

import java.util.Iterator;

public class MiniCactusBlock extends BaseEntityBlock {
    public static final MapCodec<MiniCactusBlock> CODEC = simpleCodec(MiniCactusBlock::new);
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty HAS_HAT = BooleanProperty.create("has_hat");

    private static final VoxelShape OUTLINE_SHAPE = Block.column(6.0, 0.0, 18.0);
    private static final VoxelShape COLLISION_SHAPE = Block.column(6.0, 0.0, 17.0);

    public MiniCactusBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(HAS_HAT, false));
    }

    @Override
    public MapCodec<MiniCactusBlock> codec() {
        return CODEC;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return state.getValue(HAS_HAT) ? RenderShape.INVISIBLE : RenderShape.MODEL;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

        if (state.getValue(HAS_HAT) && !world.isClientSide()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ArloBlockEntity arloEntity) {
                arloEntity.triggerInteractAnimation();
            }
        }

        if (stack.is(ModTags.Item.HAT)) {
            if (!world.isClientSide()) {
                BlockEntity blockEntity = world.getBlockEntity(pos);
                ArloBlockEntity arloEntity;

                if (!(blockEntity instanceof ArloBlockEntity)) {
                    arloEntity = new ArloBlockEntity(pos, state);
                    world.setBlockEntity(arloEntity);
                } else {
                    arloEntity = (ArloBlockEntity) blockEntity;
                }

                Identifier newHatId = BuiltInRegistries.ITEM.getKey(stack.getItem());
                String oldHatType = arloEntity.getHatType();

                if (!oldHatType.equals("none")) {
                    Identifier oldHatId = Identifier.tryParse(oldHatType);
                    if (oldHatId != null && !player.isCreative()) {
                        ItemStack oldHatStack = new ItemStack(BuiltInRegistries.ITEM.getValue(oldHatId));
                        player.getInventory().placeItemBackInInventory(oldHatStack);
                    }
                }

                arloEntity.setHatType(newHatId.toString());

                if (!state.getValue(HAS_HAT)) {
                    world.setBlock(pos, state.setValue(HAS_HAT, true), 3);
                }

                if (!player.isCreative()) {
                    stack.shrink(1);
                }

                world.playSound(null, pos, SoundEvents.ARMOR_EQUIP_LEATHER.value(),
                        SoundSource.BLOCKS, 1.0F, 1.0F);

                arloEntity.triggerInteractAnimation();

                if (player instanceof ServerPlayer serverPlayer) {
                    HappyBirthdayHandler.grantHappyBirthdayAdvancement(serverPlayer);
                    HatTrickHandler.onHatPlaced(serverPlayer, newHatId.toString());

                    if (newHatId.toString().equals("minecraft:bucket")) {
                        BucketOfArloHandler.grantBucketOfArloAdvancement(serverPlayer);
                    }
                }
            }

            return InteractionResult.SUCCESS;
        }

        if (stack.is(Items.SHEARS) && state.getValue(HAS_HAT)) {
            if (!world.isClientSide()) {
                BlockEntity blockEntity = world.getBlockEntity(pos);

                if (blockEntity instanceof ArloBlockEntity arloEntity) {
                    String hatType = arloEntity.getHatType();

                    if (!hatType.equals("none")) {
                        Identifier hatId = Identifier.tryParse(hatType);
                        if (hatId != null) {
                            ItemStack hatStack = new ItemStack(BuiltInRegistries.ITEM.getValue(hatId));
                            Block.popResource(world, pos, hatStack);
                        }
                    }

                    arloEntity.setHatType("none");
                }

                world.setBlock(pos, state.setValue(HAS_HAT, false), 3);

                world.playSound(null, pos, SoundEvents.SHEEP_SHEAR,
                        SoundSource.BLOCKS, 1.0F, 1.0F);

                stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));
            }
            return InteractionResult.SUCCESS;
        }

        if (state.getValue(HAS_HAT)) {
            if (!world.isClientSide()) {
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity instanceof ArloBlockEntity arloEntity) {
                    arloEntity.triggerInteractAnimation();
                }
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(HAS_HAT) ? new ArloBlockEntity(pos, state) : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return state.getValue(HAS_HAT) ? createTickerHelper(type, ModBlockEntities.ARLO_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick()) : null;
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (!state.canSurvive(world, pos)) {
            tickView.scheduleTick(pos, this, 1);
        }
        return super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(world, pos)) {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState()
                .setValue(FACING, ctx.getHorizontalDirection().getClockWise())
                .setValue(HAS_HAT, false);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return OUTLINE_SHAPE;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return COLLISION_SHAPE;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        Iterator var4 = Direction.Plane.HORIZONTAL.iterator();

        Direction direction;
        BlockState blockState;
        do {
            if (!var4.hasNext()) {
                BlockState blockState2 = world.getBlockState(pos.below());
                return (blockState2.is(BlockTags.SAND)) && !world.getBlockState(pos.above()).liquid();
            }

            direction = (Direction)var4.next();
            blockState = world.getBlockState(pos.relative(direction));
        } while(!blockState.isSolid() && !world.getFluidState(pos.relative(direction)).is(FluidTags.LAVA));

        return false;
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected void entityInside(BlockState state, Level world, BlockPos pos, Entity entity, InsideBlockEffectApplier handler, boolean bl) {
        entity.hurt(world.damageSources().cactus(), 1.0F);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HAS_HAT);
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (!world.isClientSide() && state.getValue(HAS_HAT)) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ArloBlockEntity arloEntity) {
                String hatType = arloEntity.getHatType();
                if (!hatType.equals("none")) {
                    Identifier hatId = Identifier.tryParse(hatType);
                    if (hatId != null) {
                        ItemStack hatStack = new ItemStack(BuiltInRegistries.ITEM.getValue(hatId));
                        Block.popResource(world, pos, hatStack);
                    }
                }
            }
        }
        return super.playerWillDestroy(world, pos, state, player);
    }
}
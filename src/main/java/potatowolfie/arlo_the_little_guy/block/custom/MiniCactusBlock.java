package potatowolfie.arlo_the_little_guy.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;
import potatowolfie.arlo_the_little_guy.advancement.HappyBirthdayHandler;
import potatowolfie.arlo_the_little_guy.advancement.HatTrickHandler;
import potatowolfie.arlo_the_little_guy.block.entity.ArloBlockEntity;
import potatowolfie.arlo_the_little_guy.block.entity.ModBlockEntities;
import potatowolfie.arlo_the_little_guy.util.ModTags;

import java.util.Iterator;

public class MiniCactusBlock extends BlockWithEntity {
    public static final MapCodec<MiniCactusBlock> CODEC = createCodec(MiniCactusBlock::new);
    public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty HAS_HAT = BooleanProperty.of("has_hat");

    private static final VoxelShape OUTLINE_SHAPE = Block.createColumnShape(6.0, 0.0, 18.0);
    private static final VoxelShape COLLISION_SHAPE = Block.createColumnShape(6.0, 0.0, 17.0);

    public MiniCactusBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(HAS_HAT, false));
    }

    @Override
    public MapCodec<MiniCactusBlock> getCodec() {
        return CODEC;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return state.get(HAS_HAT) ? BlockRenderType.INVISIBLE : BlockRenderType.MODEL;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);

        if (state.get(HAS_HAT) && !world.isClient()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ArloBlockEntity arloEntity) {
                arloEntity.triggerInteractAnimation();
            }
        }

        if (stack.isIn(ModTags.Item.HAT)) {
            if (!world.isClient()) {
                BlockEntity blockEntity = world.getBlockEntity(pos);
                ArloBlockEntity arloEntity;

                if (!(blockEntity instanceof ArloBlockEntity)) {
                    arloEntity = new ArloBlockEntity(pos, state);
                    world.addBlockEntity(arloEntity);
                } else {
                    arloEntity = (ArloBlockEntity) blockEntity;
                }

                Identifier newHatId = Registries.ITEM.getId(stack.getItem());
                String oldHatType = arloEntity.getHatType();

                if (!oldHatType.equals("none")) {
                    Identifier oldHatId = Identifier.tryParse(oldHatType);
                    if (oldHatId != null && !player.isCreative()) {
                        ItemStack oldHatStack = new ItemStack(Registries.ITEM.get(oldHatId));
                        player.getInventory().offerOrDrop(oldHatStack);
                    }
                }

                arloEntity.setHatType(newHatId.toString());

                if (!state.get(HAS_HAT)) {
                    world.setBlockState(pos, state.with(HAS_HAT, true), 3);
                }

                if (!player.isCreative()) {
                    stack.decrement(1);
                }

                world.playSound(null, pos, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER.value(),
                        SoundCategory.BLOCKS, 1.0F, 1.0F);

                arloEntity.triggerInteractAnimation();

                if (player instanceof ServerPlayerEntity serverPlayer) {
                    HappyBirthdayHandler.grantHappyBirthdayAdvancement(serverPlayer);
                    HatTrickHandler.onHatPlaced(serverPlayer, newHatId.toString());
                }
            }
            return ActionResult.SUCCESS;
        }

        if (stack.isOf(Items.SHEARS) && state.get(HAS_HAT)) {
            if (!world.isClient()) {
                BlockEntity blockEntity = world.getBlockEntity(pos);

                if (blockEntity instanceof ArloBlockEntity arloEntity) {
                    String hatType = arloEntity.getHatType();

                    if (!hatType.equals("none")) {
                        Identifier hatId = Identifier.tryParse(hatType);
                        if (hatId != null) {
                            ItemStack hatStack = new ItemStack(Registries.ITEM.get(hatId));
                            Block.dropStack(world, pos, hatStack);
                        }
                    }

                    arloEntity.setHatType("none");
                }

                world.setBlockState(pos, state.with(HAS_HAT, false), 3);

                world.playSound(null, pos, SoundEvents.ENTITY_SHEEP_SHEAR,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);

                stack.damage(1, player, player.getPreferredEquipmentSlot(stack));
            }
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return state.get(HAS_HAT) ? new ArloBlockEntity(pos, state) : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return state.get(HAS_HAT) ? validateTicker(type, ModBlockEntities.ARLO_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick()) : null;
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            tickView.scheduleBlockTick(pos, this, 1);
        }
        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true);
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
                .with(FACING, ctx.getHorizontalPlayerFacing().rotateYClockwise())
                .with(HAS_HAT, false);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPE;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return COLLISION_SHAPE;
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Iterator var4 = Direction.Type.HORIZONTAL.iterator();

        Direction direction;
        BlockState blockState;
        do {
            if (!var4.hasNext()) {
                BlockState blockState2 = world.getBlockState(pos.down());
                return (blockState2.isIn(BlockTags.SAND)) && !world.getBlockState(pos.up()).isLiquid();
            }

            direction = (Direction)var4.next();
            blockState = world.getBlockState(pos.offset(direction));
        } while(!blockState.isSolid() && !world.getFluidState(pos.offset(direction)).isIn(FluidTags.LAVA));

        return false;
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, EntityCollisionHandler handler, boolean bl) {
        entity.serverDamage(world.getDamageSources().cactus(), 1.0F);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, HAS_HAT);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient() && state.get(HAS_HAT)) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ArloBlockEntity arloEntity) {
                String hatType = arloEntity.getHatType();
                if (!hatType.equals("none")) {
                    Identifier hatId = Identifier.tryParse(hatType);
                    if (hatId != null) {
                        ItemStack hatStack = new ItemStack(Registries.ITEM.get(hatId));
                        Block.dropStack(world, pos, hatStack);
                    }
                }
            }
        }
        return super.onBreak(world, pos, state, player);
    }
}
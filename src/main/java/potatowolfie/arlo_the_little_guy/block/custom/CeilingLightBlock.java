package potatowolfie.arlo_the_little_guy.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;
import potatowolfie.arlo_the_little_guy.advancement.LightsOutHandler;
import potatowolfie.arlo_the_little_guy.world.dimension.ModDimensions;

public class CeilingLightBlock extends Block {
    public static final BooleanProperty LIT = BooleanProperty.create("lit");
    public static final BooleanProperty FLICKERING = BooleanProperty.create("flickering");

    private static final int TIMER_MIN_TICKS = 240;
    private static final int TIMER_RANGE_TICKS = 60;
    private static final int FLICK_DURATION = 3;

    public CeilingLightBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(LIT, true)
                .setValue(FLICKERING, false));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        ItemStack heldItem = player.getItemInHand(InteractionHand.MAIN_HAND);

        if (heldItem.is(ItemTags.PICKAXES)) {
            if (!world.isClientSide()) {
                if (!state.getValue(LIT)) {
                    return InteractionResult.SUCCESS;
                }

                world.setBlock(pos, state.setValue(LIT, false).setValue(FLICKERING, false), 3);
                world.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 0.8F, 0.6F);
                world.playSound(null, pos, SoundEvents.COPPER_BULB_TURN_OFF, SoundSource.BLOCKS, 0.5F, 1.1F);

                if (player instanceof ServerPlayer serverPlayer) {
                    LightsOutHandler.grantLightsOutAdvancement(serverPlayer);
                }
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    protected void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean moved) {
        super.onPlace(state, world, pos, oldState, moved);

        if (oldState.is(state.getBlock())) {
            return;
        }

        if (!world.isClientSide()) {
            if (world instanceof ServerLevel serverLevel
                    && serverLevel.dimension() == ModDimensions.ARLROOMS_LEVEL_KEY) {

                RandomSource random = world.getRandom();
                float roll = random.nextFloat();

                if (roll < 0.10F) {
                    world.setBlock(pos, state.setValue(FLICKERING, true).setValue(LIT, true), 3);
                    scheduleTimer(serverLevel, pos, random);
                } else if (roll < 0.20F) {
                    world.setBlock(pos, state.setValue(FLICKERING, false).setValue(LIT, false), 3);
                }
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.getValue(FLICKERING)) return;

        boolean isLit = state.getValue(LIT);

        if (isLit) {
            level.setBlock(pos, state.setValue(LIT, false), 3);
            spawnSparks(level, pos, random);
            level.playSound(null, pos, SoundEvents.COPPER_BULB_TURN_OFF, SoundSource.BLOCKS, 0.25F, 0.8F + random.nextFloat() * 0.2F);

            level.scheduleTick(pos, this, FLICK_DURATION);
        } else {
            level.setBlock(pos, state.setValue(LIT, true), 3);
            level.playSound(null, pos, SoundEvents.COPPER_BULB_TURN_ON, SoundSource.BLOCKS, 0.25F, 0.8F + random.nextFloat() * 0.2F);

            scheduleTimer(level, pos, random);
        }
    }

    private static void scheduleTimer(ServerLevel level, BlockPos pos, RandomSource random) {
        level.scheduleTick(pos, level.getBlockState(pos).getBlock(),
                TIMER_MIN_TICKS + random.nextInt(TIMER_RANGE_TICKS));
    }

    private static void spawnSparks(ServerLevel level, BlockPos pos, RandomSource random) {
        int count = 2 + random.nextInt(2);

        double cx = pos.getX() + 0.5;
        double cy = pos.getY() - 0.0625;
        double cz = pos.getZ() + 0.5;

        double spreadX = 0.35;
        double spreadY = 0.0;
        double spreadZ = 0.35;

        level.sendParticles(
                ArloTheLittleGuy.CEILING_LIGHT_SPARK,
                cx, cy, cz,
                count,
                spreadX, spreadY, spreadZ,
                0.25
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, FLICKERING);
    }
}
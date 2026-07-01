package potatowolfie.arlo_the_little_guy.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;
import potatowolfie.arlo_the_little_guy.block.custom.CassetteTapePlayerBlock;
import potatowolfie.arlo_the_little_guy.block.custom.TapeType;
import potatowolfie.arlo_the_little_guy.sound.ModSounds;

public class CassetteTapePlayerBlockEntity extends BlockEntity {
    private int delayTicks = -1;
    private boolean isWaitingToPlay = false;
    private int questionTapeCooldown = 0;

    public CassetteTapePlayerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CASSETTE_TAPE_PLAYER, pos, state);
    }

    public void startTapePlaybackDelay() {
        this.delayTicks = 30;
        this.isWaitingToPlay = true;
        this.setChanged();

        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }

    public void stopPlayback() {
        if (this.level != null) {
            TapeType currentTape = this.getBlockState().getValue(CassetteTapePlayerBlock.TAPE);
            stopSound(this.level, this.worldPosition, currentTape);
        }

        this.delayTicks = -1;
        this.isWaitingToPlay = false;
        this.questionTapeCooldown = 0;
        this.setChanged();

        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, CassetteTapePlayerBlockEntity blockEntity) {

        if (blockEntity.isWaitingToPlay && blockEntity.delayTicks > 0) {
            blockEntity.delayTicks--;

            if (blockEntity.delayTicks == 0) {
                blockEntity.isWaitingToPlay = false;

                TapeType tape = state.getValue(CassetteTapePlayerBlock.TAPE);
                SoundEvent sound = getSoundForTape(tape);
                if (sound != null && tape != TapeType.TAPE_8) {
                    level.playSound(null, pos, sound, SoundSource.RECORDS, 4.0F, 1.0F);
                }
            }
        }

        if (!blockEntity.isWaitingToPlay) {
            TapeType tape = state.getValue(CassetteTapePlayerBlock.TAPE);

            if (tape == TapeType.TAPE_8) {
                if (blockEntity.questionTapeCooldown <= 0) {
                    level.playSound(null, pos, ModSounds.NO_ONE_WILL_BELIEVE_YOU, SoundSource.RECORDS, 1.0F, 1.0F);
                    blockEntity.questionTapeCooldown = 10 + level.getRandom().nextInt(11);
                } else {
                    blockEntity.questionTapeCooldown--;
                }
            } else {
                blockEntity.questionTapeCooldown = 0;
            }
        }
    }

    private static void stopSound(Level level, BlockPos pos, TapeType tape) {
        SoundEvent sound = getSoundForTape(tape);
        if (sound == null) return;

        if (level instanceof ServerLevel serverLevel) {
            ClientboundStopSoundPacket packet = new ClientboundStopSoundPacket(sound.location(), SoundSource.RECORDS);
            serverLevel.players().forEach(player -> {
                if (player.blockPosition().closerThan(pos, 64)) {
                    player.connection.send(packet);
                }
            });
        }
    }

    private static SoundEvent getSoundForTape(TapeType tape) {
        return switch (tape) {
            case TAPE_1 -> ModSounds.CASSETTE_TAPE_1_AUDIO;
            case TAPE_2 -> ModSounds.CASSETTE_TAPE_2_AUDIO;
            case TAPE_3 -> ModSounds.CASSETTE_TAPE_3_AUDIO;
            case TAPE_4 -> ModSounds.CASSETTE_TAPE_4_AUDIO;
            case TAPE_5 -> ModSounds.CASSETTE_TAPE_5_AUDIO;
            case TAPE_6 -> ModSounds.CASSETTE_TAPE_6_AUDIO;
            case TAPE_7 -> ModSounds.CASSETTE_TAPE_7_AUDIO;
            case TAPE_8 -> ModSounds.NO_ONE_WILL_BELIEVE_YOU;
            default -> null;
        };
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        super.preRemoveSideEffects(pos, state);

        TapeType currentTape = state.getValue(CassetteTapePlayerBlock.TAPE);
        if (currentTape != TapeType.EMPTY) {
            stopSound(this.level, pos, currentTape);

            Item tapeItem = CassetteTapePlayerBlock.getItemFromTapeType(currentTape);
            if (tapeItem != null && this.level != null) {
                Block.popResource(this.level, pos, new ItemStack(tapeItem));
            }
            if (this.level != null) {
                level.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("DelayTicks", this.delayTicks);
        output.putBoolean("WaitingToPlay", this.isWaitingToPlay);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.delayTicks = input.getIntOr("DelayTicks", -1);
        this.isWaitingToPlay = input.getBooleanOr("WaitingToPlay", false);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(this.problemPath(), ArloTheLittleGuy.LOGGER);
        try {
            TagValueOutput output = TagValueOutput.createWithContext(reporter, registries);
            this.saveWithId(output);
            return output.buildResult();
        } catch (Exception e) {
            ArloTheLittleGuy.LOGGER.error("Failed to build update packet for CassetteTapeRecorderBE", e);
            return new CompoundTag();
        } finally {
            reporter.close();
        }
    }
}
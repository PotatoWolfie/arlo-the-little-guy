package potatowolfie.arlo_the_little_guy.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import potatowolfie.arlo_the_little_guy.block.custom.MiniCactusBlock;
import potatowolfie.arlo_the_little_guy.sound.ModSounds;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class ArloBlockEntity extends BlockEntity {
    public final AnimationState interactAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState randomAnimationState = new AnimationState();

    private String hatType = "none";

    public enum ArloState {
        IDLE,
        INTERACTING,
        RANDOM_WIGGLE
    }

    private ArloState arloState = ArloState.IDLE;
    private boolean isChangingState = false;

    private boolean isInteractAnimationRunning = false;
    private boolean isIdleAnimationRunning = false;
    private boolean isRandomAnimationRunning = false;
    private boolean animationStartedThisTick = false;

    private boolean justPlayedWhisper = false;
    private boolean hasAddedStareDelay = false;

    private int randomAnimationTimer = 0;
    private int nextRandomAnimationDelay = 0;

    private int whisperEventTimer = 0;
    private int nextWhisperEventDelay = 0;

    private int interactAnimationStateTimer = 0;
    private int randomAnimationStateTimer = 0;
    private static final int INTERACT_ANIMATION_LENGTH = 20;
    private static final int RANDOM_ANIMATION_LENGTH = 20;

    private static final int MIN_WHISPER_DELAY = 5 * 24 * 60 * 60 * 20;
    private static final int MAX_WHISPER_DELAY = 10 * 24 * 60 * 60 * 20;
    private static final int STARE_DELAY = 100;

    public ArloBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ARLO_BLOCK_ENTITY, pos, state);
    }

    public void tick() {
        if (this.level != null) {
            boolean hasHat = !this.hatType.equals("none");
            animationStartedThisTick = false;

            if (this.level.isClientSide()) {
                if (hasHat) {
                    updateAnimations();
                } else {
                    stopAllAnimations();
                }
            } else {
                if (hasHat) {
                    if (nextRandomAnimationDelay == 0) {
                        nextRandomAnimationDelay = 3600 + this.level.getRandom().nextInt(1201);
                    }

                    if (nextWhisperEventDelay == 0) {
                        nextWhisperEventDelay = MIN_WHISPER_DELAY + this.level.getRandom().nextInt(MAX_WHISPER_DELAY - MIN_WHISPER_DELAY);
                    }

                    if (arloState == ArloState.INTERACTING) {
                        interactAnimationStateTimer++;

                        if (interactAnimationStateTimer >= INTERACT_ANIMATION_LENGTH) {
                            setArloState(ArloState.IDLE);
                            interactAnimationStateTimer = 0;
                        }
                    }

                    if (arloState == ArloState.RANDOM_WIGGLE) {
                        randomAnimationStateTimer++;

                        if (randomAnimationStateTimer >= RANDOM_ANIMATION_LENGTH) {
                            setArloState(ArloState.IDLE);
                            randomAnimationStateTimer = 0;
                        }
                    }

                    randomAnimationTimer++;

                    if (randomAnimationTimer >= nextRandomAnimationDelay && arloState == ArloState.IDLE) {
                        triggerRandomAnimation();
                        randomAnimationTimer = 0;
                        nextRandomAnimationDelay = 3600 + this.level.getRandom().nextInt(1201);
                    }

                    whisperEventTimer++;

                    if (whisperEventTimer >= nextWhisperEventDelay && arloState == ArloState.IDLE) {
                        tryTriggerWhisperEvent();
                    }
                } else {
                    randomAnimationTimer = 0;
                    whisperEventTimer = 0;
                    interactAnimationStateTimer = 0;
                    arloState = ArloState.IDLE;
                }
            }
        }
    }

    private boolean isAprilFools() {
        LocalDate today = LocalDate.now();
        return today.getMonth() == Month.APRIL && today.getDayOfMonth() == 1;
    }

    private void tryTriggerWhisperEvent() {
        if (this.level == null || this.level.isClientSide()) return;
        if (justPlayedWhisper) return;

        Vec3 arloPos = new Vec3(worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5);

        List<ServerPlayer> nearbyPlayers = this.level.getEntitiesOfClass(
                ServerPlayer.class,
                new AABB(worldPosition).inflate(20),
                player -> player.distanceToSqr(arloPos) <= 400
        );

        if (nearbyPlayers.size() != 1) {
            hasAddedStareDelay = false;
            whisperEventTimer = 0;
            nextWhisperEventDelay = MIN_WHISPER_DELAY + this.level.getRandom().nextInt(MAX_WHISPER_DELAY - MIN_WHISPER_DELAY);
            justPlayedWhisper = false;
            return;
        }

        ServerPlayer targetPlayer = nearbyPlayers.get(0);
        double distanceToPlayer = targetPlayer.distanceToSqr(arloPos);

        if (distanceToPlayer > 100) {
            hasAddedStareDelay = false;
            whisperEventTimer = 0;
            nextWhisperEventDelay = MIN_WHISPER_DELAY + this.level.getRandom().nextInt(MAX_WHISPER_DELAY - MIN_WHISPER_DELAY);
            justPlayedWhisper = false;
            return;
        }

        if (isPlayerLookingAtArlo(targetPlayer)) {
            if (!hasAddedStareDelay) {
                nextWhisperEventDelay = whisperEventTimer + STARE_DELAY;
                hasAddedStareDelay = true;
            }
            return;
        }

        hasAddedStareDelay = false;

        justPlayedWhisper = true;
        triggerWhisperEvent(targetPlayer);
        whisperEventTimer = 0;
        nextWhisperEventDelay = MIN_WHISPER_DELAY + this.level.getRandom().nextInt(MAX_WHISPER_DELAY - MIN_WHISPER_DELAY);
        justPlayedWhisper = false;
    }

    private boolean isPlayerLookingAtArlo(ServerPlayer player) {
        Vec3 playerEyePos = player.getEyePosition();
        Vec3 lookVec = player.getViewVector(1.0F);
        double distance = Math.sqrt(player.distanceToSqr(new Vec3(worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5)));
        Vec3 endVec = playerEyePos.add(lookVec.scale(distance + 2));

        BlockHitResult hitResult = this.level.clip(new ClipContext(
                playerEyePos,
                endVec,
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.NONE,
                player
        ));

        return hitResult.getType() == HitResult.Type.BLOCK && hitResult.getBlockPos().equals(worldPosition);
    }

    private void triggerWhisperEvent(ServerPlayer targetPlayer) {
        if (isAprilFools()) {
            this.level.playSound(
                    null,
                    this.worldPosition,
                    ModSounds.APRIL_FOOLS_WHISPER,
                    SoundSource.BLOCKS,
                    1.0f,
                    1.0f
            );
        } else {
            this.level.playSound(
                    null,
                    this.worldPosition,
                    ModSounds.NO_ONE_WILL_BELIEVE_YOU,
                    SoundSource.BLOCKS,
                    1.0f,
                    1.0f
            );
        }

        triggerRandomAnimation();
    }

    private void updateAnimations() {
        if (this.level == null || !this.level.isClientSide()) return;

        if (arloState == ArloState.INTERACTING) {
            if (!isInteractAnimationRunning) {
                this.interactAnimationState.start((int)this.level.getGameTime());
                this.isInteractAnimationRunning = true;
            }
            if (isIdleAnimationRunning) {
                this.idleAnimationState.stop();
                this.isIdleAnimationRunning = false;
            }
            if (isRandomAnimationRunning) {
                this.randomAnimationState.stop();
                this.isRandomAnimationRunning = false;
            }
        } else if (arloState == ArloState.RANDOM_WIGGLE) {
            if (!isRandomAnimationRunning) {
                this.randomAnimationState.start((int)this.level.getGameTime());
                this.isRandomAnimationRunning = true;
            }
            if (isIdleAnimationRunning) {
                this.idleAnimationState.stop();
                this.isIdleAnimationRunning = false;
            }
            if (isInteractAnimationRunning) {
                this.interactAnimationState.stop();
                this.isInteractAnimationRunning = false;
            }
        } else if (arloState == ArloState.IDLE) {
            if (!isIdleAnimationRunning) {
                this.idleAnimationState.startIfStopped((int)this.level.getGameTime());
                this.isIdleAnimationRunning = true;
            }
            if (isInteractAnimationRunning) {
                this.interactAnimationState.stop();
                this.isInteractAnimationRunning = false;
            }
            if (isRandomAnimationRunning) {
                this.randomAnimationState.stop();
                this.isRandomAnimationRunning = false;
            }
        }
    }

    private void stopAllAnimations() {
        if (this.level == null || !this.level.isClientSide()) return;

        if (isIdleAnimationRunning) {
            this.idleAnimationState.stop();
            this.isIdleAnimationRunning = false;
        }
        if (isInteractAnimationRunning) {
            this.interactAnimationState.stop();
            this.isInteractAnimationRunning = false;
        }
        if (isRandomAnimationRunning) {
            this.randomAnimationState.stop();
            this.isRandomAnimationRunning = false;
        }
    }

    public ArloState getArloState() {
        return arloState;
    }

    public void setArloState(ArloState newState) {
        if (this.arloState != newState && !isChangingState) {
            isChangingState = true;
            this.arloState = newState;
            isChangingState = false;

            if (!this.level.isClientSide()) {
                this.setChanged();
                this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
            }
        }
    }

    public void triggerInteractAnimation() {
        if (this.level != null && !this.level.isClientSide()) {
            if (arloState == ArloState.IDLE) {
                setArloState(ArloState.INTERACTING);
                this.interactAnimationStateTimer = 1;
                this.setChanged();
                this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
            }
        }
    }

    public void triggerRandomAnimation() {
        if (this.level != null && !this.level.isClientSide()) {
            if (arloState == ArloState.IDLE) {
                setArloState(ArloState.RANDOM_WIGGLE);
                this.randomAnimationStateTimer = 1;
                this.setChanged();
                this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
            }
        }
    }

    public boolean hasHat() {
        return !this.hatType.equals("none");
    }

    public String getHatType() {
        return this.hatType;
    }

    public void setHatType(String hatType) {
        this.hatType = hatType;
        if (this.level != null && !this.level.isClientSide()) {
            BlockState state = this.getBlockState();
            boolean hasHat = !hatType.equals("none");

            if (state.getValue(MiniCactusBlock.HAS_HAT) != hasHat) {
                this.level.setBlock(this.worldPosition, state.setValue(MiniCactusBlock.HAS_HAT, hasHat), 3);
            }

            if (hasHat) {
                randomAnimationTimer = 0;
                nextRandomAnimationDelay = 3600 + this.level.getRandom().nextInt(1201);
                whisperEventTimer = 0;
                nextWhisperEventDelay = MIN_WHISPER_DELAY + this.level.getRandom().nextInt(MAX_WHISPER_DELAY - MIN_WHISPER_DELAY);
                hasAddedStareDelay = false;
            }

            this.setChanged();
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }

    public void setHasHat(boolean hasHat) {
        setHatType(hasHat ? "default" : "none");
    }

    public int getInteractAnimationStateTimer() {
        return this.interactAnimationStateTimer;
    }

    public int getRandomAnimationStateTimer() {
        return this.randomAnimationStateTimer;
    }

    @Override
    public void saveAdditional(ValueOutput writeView) {
        super.saveAdditional(writeView);
        writeView.putString("HatType", this.hatType);
        writeView.putString("ArloState", this.arloState.name());
        writeView.putInt("InteractAnimationStateTimer", this.interactAnimationStateTimer);
        writeView.putInt("RandomAnimationStateTimer", this.randomAnimationStateTimer);
        writeView.putInt("RandomAnimTimer", this.randomAnimationTimer);
        writeView.putInt("NextRandomDelay", this.nextRandomAnimationDelay);
        writeView.putInt("WhisperEventTimer", this.whisperEventTimer);
        writeView.putInt("NextWhisperDelay", this.nextWhisperEventDelay);
        writeView.putBoolean("HasAddedStareDelay", this.hasAddedStareDelay);
    }

    @Override
    public void loadAdditional(ValueInput readView) {
        super.loadAdditional(readView);
        this.hatType = readView.getStringOr("HatType", "none");
        String stateString = readView.getStringOr("ArloState", "IDLE");
        try {
            this.arloState = ArloState.valueOf(stateString);
        } catch (IllegalArgumentException e) {
            this.arloState = ArloState.IDLE;
        }
        this.interactAnimationStateTimer = readView.getIntOr("InteractAnimationStateTimer", 0);
        this.randomAnimationStateTimer = readView.getIntOr("RandomAnimationStateTimer", 0);
        this.randomAnimationTimer = readView.getIntOr("RandomAnimTimer", 0);
        this.nextRandomAnimationDelay = readView.getIntOr("NextRandomDelay", 3600);
        this.whisperEventTimer = readView.getIntOr("WhisperEventTimer", 0);
        this.nextWhisperEventDelay = readView.getIntOr("NextWhisperDelay", MIN_WHISPER_DELAY);
        this.hasAddedStareDelay = readView.getBooleanOr("HasAddedStareDelay", false);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveCustomOnly(registries);
    }
}
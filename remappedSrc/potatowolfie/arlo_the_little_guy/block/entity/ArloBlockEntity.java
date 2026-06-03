package potatowolfie.arlo_the_little_guy.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.AnimationState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
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
        if (this.world != null) {
            boolean hasHat = !this.hatType.equals("none");
            animationStartedThisTick = false;

            if (this.world.isClient()) {
                if (hasHat) {
                    updateAnimations();
                } else {
                    stopAllAnimations();
                }
            } else {
                if (hasHat) {
                    if (nextRandomAnimationDelay == 0) {
                        nextRandomAnimationDelay = 3600 + this.world.random.nextInt(1201);
                    }

                    if (nextWhisperEventDelay == 0) {
                        nextWhisperEventDelay = MIN_WHISPER_DELAY + this.world.random.nextInt(MAX_WHISPER_DELAY - MIN_WHISPER_DELAY);
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
                        nextRandomAnimationDelay = 3600 + this.world.random.nextInt(1201);
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
        if (this.world == null || this.world.isClient()) return;
        if (justPlayedWhisper) return;

        Vec3d arloPos = new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);

        List<ServerPlayerEntity> nearbyPlayers = this.world.getEntitiesByClass(
                ServerPlayerEntity.class,
                new Box(pos).expand(20),
                player -> player.squaredDistanceTo(arloPos) <= 400
        );

        if (nearbyPlayers.size() != 1) {
            hasAddedStareDelay = false;
            whisperEventTimer = 0;
            nextWhisperEventDelay = MIN_WHISPER_DELAY + this.world.random.nextInt(MAX_WHISPER_DELAY - MIN_WHISPER_DELAY);
            justPlayedWhisper = false;
            return;
        }

        ServerPlayerEntity targetPlayer = nearbyPlayers.get(0);
        double distanceToPlayer = targetPlayer.squaredDistanceTo(arloPos);

        if (distanceToPlayer > 100) {
            hasAddedStareDelay = false;
            whisperEventTimer = 0;
            nextWhisperEventDelay = MIN_WHISPER_DELAY + this.world.random.nextInt(MAX_WHISPER_DELAY - MIN_WHISPER_DELAY);
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
        nextWhisperEventDelay = MIN_WHISPER_DELAY + this.world.random.nextInt(MAX_WHISPER_DELAY - MIN_WHISPER_DELAY);
        justPlayedWhisper = false;
    }

    private boolean isPlayerLookingAtArlo(ServerPlayerEntity player) {
        Vec3d playerEyePos = player.getEyePos();
        Vec3d lookVec = player.getRotationVec(1.0F);
        double distance = Math.sqrt(player.squaredDistanceTo(new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5)));
        Vec3d endVec = playerEyePos.add(lookVec.multiply(distance + 2));

        BlockHitResult hitResult = this.world.raycast(new RaycastContext(
                playerEyePos,
                endVec,
                RaycastContext.ShapeType.OUTLINE,
                RaycastContext.FluidHandling.NONE,
                player
        ));

        return hitResult.getType() == HitResult.Type.BLOCK && hitResult.getBlockPos().equals(pos);
    }

    private void triggerWhisperEvent(ServerPlayerEntity targetPlayer) {
        if (isAprilFools()) {
            this.world.playSound(
                    null,
                    this.pos,
                    ModSounds.APRIL_FOOLS_WHISPER,
                    SoundCategory.BLOCKS,
                    1.0f,
                    1.0f
            );
        } else {
            this.world.playSound(
                    null,
                    this.pos,
                    ModSounds.NO_ONE_WILL_BELIEVE_YOU,
                    SoundCategory.BLOCKS,
                    1.0f,
                    1.0f
            );
        }

        triggerRandomAnimation();
    }

    private void updateAnimations() {
        if (this.world == null || !this.world.isClient()) return;

        if (arloState == ArloState.INTERACTING) {
            if (!isInteractAnimationRunning) {
                this.interactAnimationState.start((int)this.world.getTime());
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
                this.randomAnimationState.start((int)this.world.getTime());
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
                this.idleAnimationState.startIfNotRunning((int)this.world.getTime());
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
        if (this.world == null || !this.world.isClient()) return;

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

            if (!this.world.isClient()) {
                this.markDirty();
                this.world.updateListeners(this.pos, this.getCachedState(), this.getCachedState(), 3);
            }
        }
    }

    public void triggerInteractAnimation() {
        if (this.world != null && !this.world.isClient()) {
            if (arloState == ArloState.IDLE) {
                setArloState(ArloState.INTERACTING);
                this.interactAnimationStateTimer = 1;
                this.markDirty();
                this.world.updateListeners(this.pos, this.getCachedState(), this.getCachedState(), 3);
            }
        }
    }

    public void triggerRandomAnimation() {
        if (this.world != null && !this.world.isClient()) {
            if (arloState == ArloState.IDLE) {
                setArloState(ArloState.RANDOM_WIGGLE);
                this.randomAnimationStateTimer = 1;
                this.markDirty();
                this.world.updateListeners(this.pos, this.getCachedState(), this.getCachedState(), 3);
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
        if (this.world != null && !this.world.isClient()) {
            BlockState state = this.getCachedState();
            boolean hasHat = !hatType.equals("none");

            if (state.get(MiniCactusBlock.HAS_HAT) != hasHat) {
                this.world.setBlockState(this.pos, state.with(MiniCactusBlock.HAS_HAT, hasHat), 3);
            }

            if (hasHat) {
                randomAnimationTimer = 0;
                nextRandomAnimationDelay = 3600 + this.world.random.nextInt(1201);
                whisperEventTimer = 0;
                nextWhisperEventDelay = MIN_WHISPER_DELAY + this.world.random.nextInt(MAX_WHISPER_DELAY - MIN_WHISPER_DELAY);
                hasAddedStareDelay = false;
            }

            this.markDirty();
            this.world.updateListeners(this.pos, this.getCachedState(), this.getCachedState(), 3);
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
    public void writeData(WriteView writeView) {
        super.writeData(writeView);
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
    public void readData(ReadView readView) {
        super.readData(readView);
        this.hatType = readView.getString("HatType", "none");
        String stateString = readView.getString("ArloState", "IDLE");
        try {
            this.arloState = ArloState.valueOf(stateString);
        } catch (IllegalArgumentException e) {
            this.arloState = ArloState.IDLE;
        }
        this.interactAnimationStateTimer = readView.getInt("InteractAnimationStateTimer", 0);
        this.randomAnimationStateTimer = readView.getInt("RandomAnimationStateTimer", 0);
        this.randomAnimationTimer = readView.getInt("RandomAnimTimer", 0);
        this.nextRandomAnimationDelay = readView.getInt("NextRandomDelay", 3600);
        this.whisperEventTimer = readView.getInt("WhisperEventTimer", 0);
        this.nextWhisperEventDelay = readView.getInt("NextWhisperDelay", MIN_WHISPER_DELAY);
        this.hasAddedStareDelay = readView.getBoolean("HasAddedStareDelay", false);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createComponentlessNbt(registries);
    }
}
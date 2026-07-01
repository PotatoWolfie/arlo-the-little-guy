package potatowolfie.arlo_the_little_guy.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import potatowolfie.arlo_the_little_guy.advancement.WayDeeperDownHandler;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class NoClipDecorBlock extends Block {

    private static final ResourceKey<Level> ARLROOMS_DIMENSION = ResourceKey.create(
            Registries.DIMENSION,
            Identifier.fromNamespaceAndPath("arlo-the-little-guy", "arlrooms")
    );

    private static final Map<UUID, Long> lastTouchTick = new ConcurrentHashMap<>();

    public NoClipDecorBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos,
                                Entity entity, InsideBlockEffectApplier effectApplier,
                                boolean isPrecise) {

        if (!level.isClientSide() && entity.level() instanceof ServerLevel serverLevel
                && entity instanceof ServerPlayer player) {

            long currentTick = serverLevel.getGameTime();
            UUID id = player.getUUID();

            Long lastTick = lastTouchTick.get(id);
            boolean isFreshContact = (lastTick == null || currentTick - lastTick > 1);

            lastTouchTick.put(id, currentTick);

            if (isFreshContact && serverLevel.dimension().equals(ARLROOMS_DIMENSION)) {
                WayDeeperDownHandler.grantWayDeeperDownAdvancement(player);
            }
        }

        super.entityInside(state, level, pos, entity, effectApplier, isPrecise);
    }
}
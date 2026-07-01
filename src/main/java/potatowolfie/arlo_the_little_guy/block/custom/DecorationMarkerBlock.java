package potatowolfie.arlo_the_little_guy.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.util.StringRepresentable;

/**
 * Marks a point inside a room template where a decoration piece is allowed
 * to spawn (see ArlroomsExpansionManager.spawnDecorations). Unlike
 * DoorMarkerBlock, this doesn't need its own orientation -- it's a single
 * anchor point, and the decoration placed here picks up whatever rotation
 * the parent room itself was placed with, same as every other block in the
 * room.
 *
 * TAG lets a placed marker restrict which decorations are eligible to spawn
 * at it. Matching is strict opt-in on both sides: a TAG.NONE anchor only
 * ever pulls from decorations that declare no tags of their own, and a
 * tagged anchor (e.g. HALLWAY) only pulls from decorations that explicitly
 * list that same tag. An untagged decoration never spawns at a tagged
 * anchor, and a tagged decoration never spawns at an untagged anchor.
 *
 * Right-clicking a placed marker with an empty hand cycles through the
 * available tags (there's no visual difference between tag states, so the
 * current tag is reported to the player via an action bar message).
 */
public class DecorationMarkerBlock extends Block {

    public static final EnumProperty<DecorationTag> TAG = EnumProperty.create("tag", DecorationTag.class);

    public DecorationMarkerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TAG, DecorationTag.NONE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TAG);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        DecorationTag current = state.getValue(TAG);
        DecorationTag[] values = DecorationTag.values();
        DecorationTag next = values[(current.ordinal() + 1) % values.length];

        if (!world.isClientSide()) {
            world.setBlock(pos, state.setValue(TAG, next), 3);
        }

        player.sendOverlayMessage(Component.literal("Decoration marker tag: " + next.getSerializedName()));

        return InteractionResult.SUCCESS;
    }

    public enum DecorationTag implements StringRepresentable {
        NONE("none"),
        HALLWAY("hallway"),
        POOLROOMS("poolrooms"),
        SECRET("secret");

        private final String name;

        DecorationTag(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
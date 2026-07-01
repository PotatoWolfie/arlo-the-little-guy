package potatowolfie.arlo_the_little_guy.structure.arlrooms;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record DoorLookPayload(BlockPos pos) implements CustomPacketPayload {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(
            "arlo-the-little-guy",
            "door_look"
    );

    public static final Type<DoorLookPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, DoorLookPayload> CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC,
                    DoorLookPayload::pos,
                    DoorLookPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
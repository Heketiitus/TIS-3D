package li.cil.tis3d.common.network.message;

import io.netty.buffer.ByteBuf;
import li.cil.tis3d.api.machine.Casing;
import li.cil.tis3d.common.network.Network;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class ServerCasingDataMessage extends AbstractCasingDataMessage {
    public static final Type<ServerCasingDataMessage> TYPE = Network.type("casing_data");
    public static final StreamCodec<RegistryFriendlyByteBuf, ServerCasingDataMessage> STREAM_CODEC = CustomPacketPayload.codec(
        ServerCasingDataMessage::toBytes,
        ServerCasingDataMessage::new
    );

    public ServerCasingDataMessage(final Casing casing, final ByteBuf data) {
        super(casing, data);
    }

    public ServerCasingDataMessage(final RegistryFriendlyByteBuf buffer) {
        super(buffer);
    }

    // --------------------------------------------------------------------- //
    // AbstractMessage

    @Override
    public void handleMessage(final IPayloadContext context) {
        final Level level = getClientLevel();
        if (level != null) {
            handleMessage(level);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

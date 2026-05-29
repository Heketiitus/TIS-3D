package li.cil.tis3d.common.network.message;

import li.cil.tis3d.client.gui.ReadOnlyMemoryModuleScreen;
import li.cil.tis3d.common.network.Network;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.InteractionHand;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class ServerReadOnlyMemoryModuleDataMessage extends AbstractReadOnlyMemoryModuleDataMessage {
    public static final CustomPacketPayload.Type<ServerReadOnlyMemoryModuleDataMessage> TYPE = Network.type("rom_data");
    public static final StreamCodec<RegistryFriendlyByteBuf, ServerReadOnlyMemoryModuleDataMessage> STREAM_CODEC = CustomPacketPayload.codec(
        ServerReadOnlyMemoryModuleDataMessage::toBytes,
        ServerReadOnlyMemoryModuleDataMessage::new
    );

    public ServerReadOnlyMemoryModuleDataMessage(final InteractionHand hand, final byte[] data) {
        super(hand, data);
    }

    public ServerReadOnlyMemoryModuleDataMessage(final RegistryFriendlyByteBuf buffer) {
        super(buffer);
    }

    // --------------------------------------------------------------------- //
    // AbstractMessage

    @Override
    public void handleMessage(final IPayloadContext context) {
        final Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof final ReadOnlyMemoryModuleScreen moduleScreen) {
            moduleScreen.setData(data);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

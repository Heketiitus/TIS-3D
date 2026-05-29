package li.cil.tis3d.common.network.message;

import li.cil.tis3d.common.block.entity.ControllerBlockEntity;
import li.cil.tis3d.common.network.Network;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class ControllerStateMessage extends AbstractMessageWithPosition {
    public static final CustomPacketPayload.Type<ControllerStateMessage> TYPE = Network.type("controller_state");
    public static final StreamCodec<RegistryFriendlyByteBuf, ControllerStateMessage> STREAM_CODEC = CustomPacketPayload.codec(
        ControllerStateMessage::toBytes,
        ControllerStateMessage::new
    );

    private ControllerBlockEntity.ControllerState state;

    public ControllerStateMessage(final ControllerBlockEntity controller, final ControllerBlockEntity.ControllerState state) {
        super(controller.getBlockPos());
        this.state = state;
    }

    public ControllerStateMessage(final RegistryFriendlyByteBuf buffer) {
        super(buffer);
    }

    // --------------------------------------------------------------------- //
    // AbstractMessage

    @Override
    public void handleMessage(final IPayloadContext context) {
        final var level = getClientLevel();
        if (level != null) {
            withBlockEntity(level, ControllerBlockEntity.class, controller ->
                controller.setStateClient(state));
        }
    }

    @Override
    public void fromBytes(final RegistryFriendlyByteBuf buffer) {
        super.fromBytes(buffer);

        state = buffer.readEnum(ControllerBlockEntity.ControllerState.class);
    }

    @Override
    public void toBytes(final RegistryFriendlyByteBuf buffer) {
        super.toBytes(buffer);

        buffer.writeEnum(state);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

package li.cil.tis3d.common.network.message;

import li.cil.tis3d.api.machine.Casing;
import li.cil.tis3d.common.block.entity.CasingBlockEntity;
import li.cil.tis3d.common.network.Network;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class CasingEnabledStateMessage extends AbstractMessageWithPosition {
    public static final Type<CasingEnabledStateMessage> TYPE = Network.type("casing_enabled_state");
    public static final StreamCodec<RegistryFriendlyByteBuf, CasingEnabledStateMessage> STREAM_CODEC = CustomPacketPayload.codec(
        CasingEnabledStateMessage::toBytes,
        CasingEnabledStateMessage::new
    );

    private boolean isEnabled;

    public CasingEnabledStateMessage(final Casing casing, final boolean isEnabled) {
        super(casing.getPosition());
        this.isEnabled = isEnabled;
    }

    public CasingEnabledStateMessage(final RegistryFriendlyByteBuf buffer) {
        super(buffer);
    }

    // --------------------------------------------------------------------- //
    // AbstractMessage

    @Override
    public void handleMessage(final IPayloadContext context) {
        final Level level = getClientLevel();
        if (level != null) {
            withBlockEntity(level, CasingBlockEntity.class, casing ->
                casing.setEnabledClient(isEnabled));
        }
    }

    @Override
    public void fromBytes(final RegistryFriendlyByteBuf buffer) {
        super.fromBytes(buffer);

        isEnabled = buffer.readBoolean();
    }

    @Override
    public void toBytes(final RegistryFriendlyByteBuf buffer) {
        super.toBytes(buffer);

        buffer.writeBoolean(isEnabled);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

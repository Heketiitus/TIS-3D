package li.cil.tis3d.common.network.message;

import li.cil.tis3d.api.machine.Casing;
import li.cil.tis3d.common.block.entity.CasingBlockEntity;
import li.cil.tis3d.common.network.Network;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class CasingLockedStateMessage extends AbstractMessageWithPosition {
    public static final Type<CasingLockedStateMessage> TYPE = Network.type("casing_locked_state");
    public static final StreamCodec<RegistryFriendlyByteBuf, CasingLockedStateMessage> STREAM_CODEC = CustomPacketPayload.codec(
        CasingLockedStateMessage::toBytes,
        CasingLockedStateMessage::new
    );
    private boolean isLocked;

    public CasingLockedStateMessage(final Casing casing, final boolean isLocked) {
        super(casing.getPosition());
        this.isLocked = isLocked;
    }

    public CasingLockedStateMessage(final RegistryFriendlyByteBuf buffer) {
        super(buffer);
    }

    // --------------------------------------------------------------------- //
    // AbstractMessage

    @Override
    public void handleMessage(final IPayloadContext context) {
        final Level level = getClientLevel();
        if (level != null) {
            withBlockEntity(level, CasingBlockEntity.class, casing ->
                casing.setCasingLockedClient(isLocked));
        }
    }

    @Override
    public void fromBytes(final RegistryFriendlyByteBuf buffer) {
        super.fromBytes(buffer);

        isLocked = buffer.readBoolean();
    }

    @Override
    public void toBytes(final RegistryFriendlyByteBuf buffer) {
        super.toBytes(buffer);

        buffer.writeBoolean(isLocked);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

package li.cil.tis3d.common.network.message;

import li.cil.tis3d.common.block.entity.ControllerBlockEntity;
import li.cil.tis3d.common.network.Network;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class HaltAndCatchFireMessage extends AbstractMessageWithPosition {
    public static final CustomPacketPayload.Type<HaltAndCatchFireMessage> TYPE = Network.type("halt_and_catch_fire");
    public static final StreamCodec<RegistryFriendlyByteBuf, HaltAndCatchFireMessage> STREAM_CODEC = CustomPacketPayload.codec(
        HaltAndCatchFireMessage::toBytes,
        HaltAndCatchFireMessage::new
    );

    public HaltAndCatchFireMessage(final BlockPos position) {
        super(position);
    }

    public HaltAndCatchFireMessage(final RegistryFriendlyByteBuf buffer) {
        super(buffer);
    }

    // --------------------------------------------------------------------- //
    // AbstractMessage


    @Override
    public void handleMessage(IPayloadContext context) {
        final Level level = getClientLevel();
        if (level != null) {
            withBlockEntity(level, ControllerBlockEntity.class, ControllerBlockEntity::haltAndCatchFire);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

package li.cil.tis3d.common.network.message;

import li.cil.tis3d.common.item.Items;
import li.cil.tis3d.common.item.ReadOnlyMemoryModuleItem;
import li.cil.tis3d.common.network.Network;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class ClientReadOnlyMemoryModuleDataMessage extends AbstractReadOnlyMemoryModuleDataMessage {
    public static final CustomPacketPayload.Type<ClientReadOnlyMemoryModuleDataMessage> TYPE = Network.type("ro_module_data");
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientReadOnlyMemoryModuleDataMessage> STREAM_CODEC = CustomPacketPayload.codec(
        ClientReadOnlyMemoryModuleDataMessage::toBytes,
        ClientReadOnlyMemoryModuleDataMessage::new
    );

    public ClientReadOnlyMemoryModuleDataMessage(final InteractionHand hand, final byte[] data) {
        super(hand, data);
    }

    public ClientReadOnlyMemoryModuleDataMessage(final RegistryFriendlyByteBuf buffer) {
        super(buffer);
    }

    // --------------------------------------------------------------------- //
    // AbstractMessage

    @Override
    public void handleMessage(final IPayloadContext context) {
        final var player = context.player();
        if (player != null) {
            final ItemStack stack = player.getItemInHand(hand);
            if (Items.is(stack, Items.READ_ONLY_MEMORY_MODULE)) {
                ReadOnlyMemoryModuleItem.saveToStack(stack, data);
            }
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

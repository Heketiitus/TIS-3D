package li.cil.tis3d.common.network.message;

import li.cil.tis3d.common.item.CodeBookItem;
import li.cil.tis3d.common.item.Items;
import li.cil.tis3d.common.network.Network;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class CodeBookDataMessage extends AbstractMessage {
    public static final CustomPacketPayload.Type<CodeBookDataMessage> TYPE = Network.type("codebook_data");
    public static final StreamCodec<RegistryFriendlyByteBuf, CodeBookDataMessage> STREAM_CODEC = CustomPacketPayload.codec(
        CodeBookDataMessage::toBytes,
        CodeBookDataMessage::new
    );

    private InteractionHand hand;
    private CodeBookItem.Data data;

    public CodeBookDataMessage(final InteractionHand hand, final CodeBookItem.Data data) {
        this.hand = hand;
        this.data = data;
    }

    public CodeBookDataMessage(final RegistryFriendlyByteBuf buffer) {
        super(buffer);
    }

    // --------------------------------------------------------------------- //
    // AbstractMessage

    @Override
    public void handleMessage(final IPayloadContext context) {
        final var player = context.player();
        if (player == null) {
            return;
        }

        final ItemStack stack = player.getItemInHand(hand);
        if (Items.is(stack, Items.BOOK_CODE)) {
            CodeBookItem.Data.setToStack(stack, data);
        }
    }

    @Override
    public void fromBytes(final RegistryFriendlyByteBuf buffer) {
        hand = buffer.readEnum(InteractionHand.class);
        data = CodeBookItem.Data.STREAM_CODEC.decode(buffer);
    }

    @Override
    public void toBytes(final RegistryFriendlyByteBuf buffer) {
        buffer.writeEnum(hand);
        CodeBookItem.Data.STREAM_CODEC.encode(buffer, data);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

package li.cil.tis3d.common.network.message;

import li.cil.tis3d.api.machine.Casing;
import li.cil.tis3d.common.block.entity.CasingBlockEntity;
import li.cil.tis3d.common.network.Network;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.FriendlyByteBufUtil;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.annotation.Nullable;

public final class CasingInventoryMessage extends AbstractMessageWithPosition {
    public static final Type<CasingInventoryMessage> TYPE = Network.type("casing_inventory");
    public static final StreamCodec<RegistryFriendlyByteBuf, CasingInventoryMessage> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC, CasingInventoryMessage::getPosition,
        ByteBufCodecs.INT, CasingInventoryMessage::getSlot,
        ItemStack.OPTIONAL_STREAM_CODEC, CasingInventoryMessage::getStack,
        ByteBufCodecs.COMPOUND_TAG, CasingInventoryMessage::getModuleData,
        CasingInventoryMessage::new
    );
    private int slot;
    private ItemStack stack;
    private CompoundTag moduleData;

    public CasingInventoryMessage(final Casing casing, final int slot, final ItemStack stack, @Nullable final CompoundTag moduleData) {
        this(casing.getPosition(), slot, stack, moduleData);
    }

    public CasingInventoryMessage(final BlockPos casing, final int slot, final ItemStack stack, @Nullable final CompoundTag moduleData) {
        super(casing);
        this.slot = slot;
        this.stack = stack;
        this.moduleData = moduleData == null ? new CompoundTag() : moduleData;
    }

    // --------------------------------------------------------------------- //
    // AbstractMessage

    @Override
    public void handleMessage(final IPayloadContext context) {
        final Level level = getClientLevel();
        if (level != null) {
            withBlockEntity(level, CasingBlockEntity.class, casing ->
                casing.setStackAndModuleClient(slot, stack, moduleData));
        }
    }

    @Override
    public void fromBytes(final RegistryFriendlyByteBuf buffer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void toBytes(final RegistryFriendlyByteBuf buffer) {
        throw new UnsupportedOperationException();
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getStack() {
        return stack;
    }

    public CompoundTag getModuleData() {
        return moduleData;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

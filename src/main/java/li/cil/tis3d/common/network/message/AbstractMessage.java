package li.cil.tis3d.common.network.message;

import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

public abstract class AbstractMessage implements CustomPacketPayload {
    protected static final Logger LOGGER = LogManager.getLogger();

    protected AbstractMessage() {
    }

    protected AbstractMessage(final RegistryFriendlyByteBuf buffer) {
        fromBytes(buffer);
    }

    // --------------------------------------------------------------------- //

    public abstract void handleMessage(final IPayloadContext context);

    public abstract void fromBytes(final RegistryFriendlyByteBuf buffer);

    public abstract void toBytes(final RegistryFriendlyByteBuf buffer);

    @Nullable
    protected Level getServerLevel(final IPayloadContext context) {
        final var sender = context.player();
        return sender != null ? sender.level() : null;
    }

    @OnlyIn(Dist.CLIENT)
    @Nullable
    protected Level getClientLevel() {
        return Minecraft.getInstance().level;
    }
}

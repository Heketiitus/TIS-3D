package li.cil.tis3d.common.item;

import com.mojang.serialization.Codec;
import li.cil.tis3d.util.RegistryUtils;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.nio.ByteBuffer;
import java.util.UUID;

public class DataComponentTypes {
    private static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = RegistryUtils.getDeferred(Registries.DATA_COMPONENT_TYPE);

    // --------------------------------------------------------------------- //

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<UUID>> KEY_COMPONENT =
        DATA_COMPONENT_TYPES.register("key", () -> DataComponentType.<UUID>builder()
            .persistent(UUIDUtil.CODEC)
            .build()
        );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<CodeBookItem.Data>> CODEBOOK_COMPONENT =
        DATA_COMPONENT_TYPES.register("codebook", () -> DataComponentType.<CodeBookItem.Data>builder()
            .persistent(CodeBookItem.Data.CODEC)
            .networkSynchronized(CodeBookItem.Data.STREAM_CODEC)
            .build()
        );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ByteBuffer>> ROM_DATA_COMPONENT =
        DATA_COMPONENT_TYPES.register("rom", () -> DataComponentType.<ByteBuffer>builder()
            .persistent(Codec.BYTE_BUFFER)
            .build()
        );

    // --------------------------------------------------------------------- //

    public static void initialize(IEventBus bus) {
        DATA_COMPONENT_TYPES.register(bus);
    }
}

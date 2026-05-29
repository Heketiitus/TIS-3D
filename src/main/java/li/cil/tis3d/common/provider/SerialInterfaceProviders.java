package li.cil.tis3d.common.provider;

import li.cil.tis3d.api.serial.SerialInterfaceProvider;
import li.cil.tis3d.common.provider.serial.SerialInterfaceProviderFurnace;
import li.cil.tis3d.util.RegistryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;

public final class SerialInterfaceProviders {
    private static final DeferredRegister<SerialInterfaceProvider> MODULE_PROVIDERS = RegistryUtils.getDeferred(SerialInterfaceProvider.REGISTRY);

    // --------------------------------------------------------------------- //

    public static void initialize(IEventBus bus) {
        RegistryUtils.builder(SerialInterfaceProvider.REGISTRY);

        MODULE_PROVIDERS.register("furnace", SerialInterfaceProviderFurnace::new);

        MODULE_PROVIDERS.register(bus);
    }

    public static Optional<SerialInterfaceProvider> getProviderFor(final Level level, final BlockPos position, final Direction face) {
        for (final SerialInterfaceProvider provider : RegistryUtils.get(SerialInterfaceProvider.REGISTRY)) {
            if (provider.matches(level, position, face)) {
                return Optional.of(provider);
            }
        }
        return Optional.empty();
    }
}

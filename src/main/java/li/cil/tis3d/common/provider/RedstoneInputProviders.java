package li.cil.tis3d.common.provider;

import li.cil.tis3d.api.machine.Face;
import li.cil.tis3d.api.module.Module;
import li.cil.tis3d.api.module.RedstoneInputProvider;
import li.cil.tis3d.common.provider.redstone.MinecraftRedstoneInputProvider;
import li.cil.tis3d.util.RegistryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class RedstoneInputProviders {
    private static final DeferredRegister<RedstoneInputProvider> REDSTONE_INPUT_PROVIDERS = RegistryUtils.getDeferred(RedstoneInputProvider.REGISTRY);

    // --------------------------------------------------------------------- //


    public static void initialize(IEventBus bus) {
        RegistryUtils.builder(RedstoneInputProvider.REGISTRY);

        REDSTONE_INPUT_PROVIDERS.register("minecraft", MinecraftRedstoneInputProvider::new);

        REDSTONE_INPUT_PROVIDERS.register(bus);
    }

    public static int getRedstoneInput(final Module module) {
        int maxSignal = 0;
        final Level level = module.getCasing().getCasingLevel();
        final BlockPos position = module.getCasing().getPosition();
        for (final RedstoneInputProvider provider : RegistryUtils.get(RedstoneInputProvider.REGISTRY)) {
            final int signal = provider.getInput(level, position, Face.toDirection(module.getFace()));
            if (signal > maxSignal) {
                maxSignal = signal;
            }
        }
        return maxSignal;
    }
}

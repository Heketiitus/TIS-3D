package li.cil.tis3d.util;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public abstract class RegistryUtils {
    private static final List<net.neoforged.neoforge.registries.DeferredRegister<?>> ENTRIES = new ArrayList();
    private static final List<RegistryBuilder<?>> BUILDERS = new ArrayList();
    private static RegistryUtils.Phase phase;
    private static String modId;

    public static <T> RegistryBuilder<T> builder(ResourceKey<Registry<T>> registryKey) {
        if (phase != RegistryUtils.Phase.INIT) {
            throw new IllegalStateException();
        } else {
            RegistryBuilder<T> builder = new RegistryBuilder<>(registryKey);
            BUILDERS.add(builder);
            return builder;
        }
    }

    public static <T> net.neoforged.neoforge.registries.DeferredRegister<T> getDeferred(ResourceKey<Registry<T>> registryKey) {
        if (phase != RegistryUtils.Phase.INIT) {
            throw new IllegalStateException();
        } else {
            net.neoforged.neoforge.registries.DeferredRegister<T> entry = net.neoforged.neoforge.registries.DeferredRegister.create(registryKey, modId);
            ENTRIES.add(entry);
            return entry;
        }
    }

    public static <T> Registry<T> get(ResourceKey<Registry<T>> registryKey) {
        return (Registry) BuiltInRegistries.REGISTRY.get(registryKey.location());
    }

    public static void begin(String modId) {
        RegistryUtils.modId = modId;
        if (phase != Phase.PRE_INIT) {
            throw new IllegalStateException();
        } else {
            phase = Phase.INIT;
        }
    }

    public static void finish(IEventBus bus) {
        if (phase != Phase.INIT) throw new IllegalStateException();
        phase = Phase.POST_INIT;

        bus.addListener((NewRegistryEvent e) -> BUILDERS.forEach(e::create));
        BUILDERS.clear();
    }

    private RegistryUtils() {
    }

    static {
        phase = Phase.PRE_INIT;
    }

    private enum Phase {
        PRE_INIT,
        INIT,
        POST_INIT;

        private Phase() {
        }
    }
}

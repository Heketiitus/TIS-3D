package li.cil.tis3d.client.renderer;

import li.cil.tis3d.api.module.Module;
import li.cil.tis3d.api.module.ModuleProvider;
import li.cil.tis3d.api.module.ModuleRenderer;
import li.cil.tis3d.client.renderer.module.DisplayModuleRenderer;
import li.cil.tis3d.client.renderer.module.ExecutionModuleRenderer;
import li.cil.tis3d.client.renderer.module.FacadeModuleRenderer;
import li.cil.tis3d.client.renderer.module.RandomAccessMemoryModuleRenderer;
import li.cil.tis3d.client.renderer.module.TextureModuleRenderer;
import li.cil.tis3d.common.item.Items;
import li.cil.tis3d.common.module.AudioModule;
import li.cil.tis3d.common.module.DisplayModule;
import li.cil.tis3d.common.module.ExecutionModule;
import li.cil.tis3d.common.module.FacadeModule;
import li.cil.tis3d.common.module.InfraredModule;
import li.cil.tis3d.common.module.KeypadModule;
import li.cil.tis3d.common.module.QueueModule;
import li.cil.tis3d.common.module.RandomAccessMemoryModule;
import li.cil.tis3d.common.module.RandomModule;
import li.cil.tis3d.common.module.ReadOnlyMemoryModule;
import li.cil.tis3d.common.module.RedstoneModule;
import li.cil.tis3d.common.module.SequencerModule;
import li.cil.tis3d.common.module.SerialPortModule;
import li.cil.tis3d.common.module.StackModule;
import li.cil.tis3d.common.module.TerminalModule;
import li.cil.tis3d.common.module.TimerModule;
import li.cil.tis3d.util.RegistryUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModuleRenderers {
    private static final DeferredRegister<ModuleRenderer<?>> MODULE_RENDERERS = RegistryUtils.getDeferred(ModuleRenderer.REGISTRY);
    // --------------------------------------------------------------------- //

    public static void initialize(IEventBus eventBus) {
        RegistryUtils.builder(ModuleRenderer.REGISTRY);

        MODULE_RENDERERS.register("display", DisplayModuleRenderer::new);
        MODULE_RENDERERS.register("execution", ExecutionModuleRenderer::new);
        MODULE_RENDERERS.register("random_access_memory", RandomAccessMemoryModuleRenderer::new);
        MODULE_RENDERERS.register("facade", FacadeModuleRenderer::new);

        registerTexture("audio", Textures.LOCATION_OVERLAY_MODULE_AUDIO, AudioModule.class);
        registerTexture("infrared", Textures.LOCATION_OVERLAY_MODULE_INFRARED, InfraredModule.class);
        registerTexture("keypad", Textures.LOCATION_OVERLAY_MODULE_KEYPAD, KeypadModule.class);
        registerTexture("queue", Textures.LOCATION_OVERLAY_MODULE_QUEUE, QueueModule.class);
        registerTexture("random", Textures.LOCATION_OVERLAY_MODULE_RANDOM, RandomModule.class);
        registerTexture("redstone", Textures.LOCATION_OVERLAY_MODULE_REDSTONE, RedstoneModule.class);
        registerTexture("sequencer", Textures.LOCATION_OVERLAY_MODULE_SEQUENCER, SequencerModule.class);
        registerTexture("serial_port", Textures.LOCATION_OVERLAY_MODULE_SERIAL_PORT, SerialPortModule.class);
        registerTexture("stack", Textures.LOCATION_OVERLAY_MODULE_STACK, StackModule.class);
        registerTexture("terminal", Textures.LOCATION_OVERLAY_MODULE_SERIAL_PORT, TerminalModule.class);
        registerTexture("timer", Textures.LOCATION_OVERLAY_MODULE_STACK, TimerModule.class);

        MODULE_RENDERERS.register(eventBus);
    }

    private static void registerTexture(String name, ResourceLocation texture, Class<? extends Module> moduleClass) {
        MODULE_RENDERERS.register(name, () -> new TextureModuleRenderer<>(texture, moduleClass));
    }
}

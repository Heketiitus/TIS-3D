package li.cil.tis3d.common.neoforge;

import li.cil.tis3d.api.API;
import li.cil.tis3d.client.ClientBootstrap;
import li.cil.tis3d.client.ClientSetup;
import li.cil.tis3d.client.manual.Manuals;
import li.cil.tis3d.common.CommonBootstrap;
import li.cil.tis3d.common.CommonSetup;
import li.cil.tis3d.util.RegistryUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(API.MOD_ID)
public class BootstrapNeoForge {
    public BootstrapNeoForge(final ModContainer modContainer, final IEventBus modEventBus) {
        RegistryUtils.begin(API.MOD_ID);
        CommonBootstrap.setup(modContainer, modEventBus);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            Manuals.initialize(modEventBus);
            ClientBootstrap.setup(modEventBus);
        }

        modEventBus.addListener(ClientSetup::setup);
        modEventBus.addListener(CommonSetup::setup);
        RegistryUtils.finish(modEventBus);
    }
}

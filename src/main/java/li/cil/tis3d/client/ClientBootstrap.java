package li.cil.tis3d.client;

import li.cil.tis3d.client.renderer.color.CasingBlockColor;
import li.cil.tis3d.client.renderer.entity.NullEntityRenderer;
import li.cil.tis3d.common.block.Blocks;
import li.cil.tis3d.common.entity.Entities;
import li.cil.tis3d.util.ClientSided;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;


@ClientSided
public final class ClientBootstrap {
    public static void setup(IEventBus bus) {
        bus.addListener((RegisterColorHandlersEvent.Block e) ->
            e.register(new CasingBlockColor(), Blocks.CASING.get()));

        bus.addListener((EntityRenderersEvent.RegisterRenderers e) ->
            e.registerEntityRenderer(Entities.INFRARED_PACKET.get(), NullEntityRenderer::new));
    }
}

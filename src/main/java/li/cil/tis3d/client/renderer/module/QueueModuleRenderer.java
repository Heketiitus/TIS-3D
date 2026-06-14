package li.cil.tis3d.client.renderer.module;

import li.cil.tis3d.api.module.Module;
import li.cil.tis3d.api.util.RenderContext;
import li.cil.tis3d.common.module.DisplayModule;

public class QueueModuleRenderer extends AbstractModuleWithRotationRenderer<DisplayModule> {

    @Override
    public boolean matches(Module module) {
        return module instanceof DisplayModule;
    }

    @Override
    public void render(final DisplayModule module, final RenderContext context) {
            /*
        final PoseStack matrixStack = context.getMatrixStack();
        matrixStack.pushPose();
        rotateForRendering(matrixStack);

        context.drawAtlasQuadUnlit(Textures.LOCATION_OVERLAY_MODULE_QUEUE);

        // Render detailed state when player is close.
        if (!isEmpty() && context.closeEnoughForDetails(getCasing().getPosition())) {
            drawState(context);
        }

        matrixStack.popPose();
            */
    }
}

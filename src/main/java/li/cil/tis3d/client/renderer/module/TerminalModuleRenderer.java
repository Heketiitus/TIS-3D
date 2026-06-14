package li.cil.tis3d.client.renderer.module;

import com.mojang.blaze3d.vertex.PoseStack;
import li.cil.tis3d.api.module.Module;
import li.cil.tis3d.api.util.RenderContext;
import li.cil.tis3d.client.renderer.Textures;
import li.cil.tis3d.common.module.DisplayModule;

public class TerminalModuleRenderer extends AbstractModuleWithRotationRenderer<DisplayModule> {

    @Override
    public boolean matches(Module module) {
        return module instanceof DisplayModule;
    }

    @Override
    public void render(final DisplayModule module, final RenderContext context) {
        /*
        if (!getCasing().isEnabled() || !isVisible()) {
            return;
        }

        final PoseStack matrixStack = context.getMatrixStack();
        matrixStack.pushPose();
        rotateForRendering(matrixStack);

        if (context.closeEnoughForDetails(getCasing().getPosition())) {
            // Player is close, render actual terminal text.
            renderText(context);
        } else {
            // Player too far away for details, draw static overlay.
            context.drawAtlasQuadUnlit(Textures.LOCATION_OVERLAY_MODULE_TERMINAL);
        }

        matrixStack.popPose();
         */
    }
}

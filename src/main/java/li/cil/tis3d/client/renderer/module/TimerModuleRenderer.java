package li.cil.tis3d.client.renderer.module;

import li.cil.tis3d.api.module.Module;
import li.cil.tis3d.api.util.RenderContext;
import li.cil.tis3d.common.module.DisplayModule;

public class TimerModuleRenderer extends AbstractModuleWithRotationRenderer<DisplayModule> {

    @Override
    public boolean matches(Module module) {
        return module instanceof DisplayModule;
    }

    @Override
    public void render(final DisplayModule module, final RenderContext context) {
        /*
if (!getCasing().isEnabled()) {
            return;
        }

        final PoseStack matrixStack = context.getMatrixStack();
        matrixStack.pushPose();
        rotateForRendering(matrixStack);

        context.drawAtlasQuadUnlit(Textures.LOCATION_OVERLAY_MODULE_TIMER);

        // Render detailed state when player is close.
        if (!hasElapsed && context.closeEnoughForDetails(getCasing().getPosition())) {
            final long gameTime = context.getDispatcher().level.getGameTime();
            final float remaining = (float) (timer - gameTime) - context.getPartialTicks();
            if (remaining <= 0) {
                hasElapsed = true;
            } else {
                drawState(context, remaining);
            }
        }

        matrixStack.popPose();
         */
    }
}

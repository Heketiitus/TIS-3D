package li.cil.tis3d.client.renderer.module;

import li.cil.tis3d.api.module.Module;
import li.cil.tis3d.api.util.RenderContext;
import li.cil.tis3d.common.module.DisplayModule;

public class KeypadRenderer extends AbstractModuleWithRotationRenderer<DisplayModule> {

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

        // Draw base texture. Draw half transparent while writing current value,
        // i.e. while no input is possible.
        context.drawAtlasQuadUnlit(Textures.LOCATION_OVERLAY_MODULE_KEYPAD, Color.withAlpha(Color.WHITE, value.isPresent() ? 0.5f : 1f));

        // Draw overlay for hovered button if we can currently input a value.
        if (value.isEmpty()) {
            final Vec3 hitPos = getLocalHitPosition(context.getDispatcher().cameraHitResult);
            if (hitPos != null) {
                final Vec3 uv = hitToUV(hitPos);
                final int button = uvToButton((float) uv.x, (float) uv.y);
                if (button >= 0) {
                    drawButtonOverlay(context, button);
                }
            }
        }

        matrixStack.popPose();
         */
    }
}

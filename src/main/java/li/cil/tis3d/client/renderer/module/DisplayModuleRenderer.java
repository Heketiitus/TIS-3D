package li.cil.tis3d.client.renderer.module;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import li.cil.tis3d.api.API;
import li.cil.tis3d.api.module.Module;
import li.cil.tis3d.api.util.RenderContext;
import li.cil.tis3d.client.renderer.ModRenderType;
import li.cil.tis3d.common.module.DisplayModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;

import java.util.HashMap;
import java.util.Map;

public class DisplayModuleRenderer extends AbstractModuleWithRotationRenderer<DisplayModule> {

    // Don't allow displaying stuff on the edge of the casing. I mean we could,
    // technically, but that'd usually look pretty weird. Also it's more
    // intuitive that the usable area start in the inner, black part.
    private static final int MARGIN = 4;

    private static int nextTextureId = 0;
    private static final Map<DisplayModule, RenderData> RENDER_DATA = new HashMap<>();

    private RenderData getRenderData(DisplayModule module) {
        return RENDER_DATA.computeIfAbsent(module, m -> {
            final TextureManager textureManager = Minecraft.getInstance().getTextureManager();
            final DynamicTexture texture = new DynamicTexture(DisplayModule.RESOLUTION, DisplayModule.RESOLUTION, false);
            final ResourceLocation textureId = API.resource("dynamic/display_module_" + (++nextTextureId));
            textureManager.register(textureId, texture);
            return new RenderData(texture, textureId, ModRenderType.unlitTexture(textureId));
        });
    }

    @Override
    public boolean matches(Module module) {
        return module instanceof DisplayModule;
    }

    @Override
    public void render(final DisplayModule module, final RenderContext context) {
        if (!module.getCasing().isEnabled()) {
            return;
        }

        final PoseStack matrixStack = context.getMatrixStack();
        matrixStack.pushPose();
        rotateForRendering(module, matrixStack);
        final RenderData renderData = getRenderData(module);

        if (module.resetImageDirty())
            renderData.write(module.getImage());

        final VertexConsumer builder = context.getBuffer().getBuffer(renderData.renderType());
        context.drawQuad(builder, MARGIN / 32f, MARGIN / 32f, DisplayModule.RESOLUTION / 32f, DisplayModule.RESOLUTION / 32f);

        matrixStack.popPose();
    }

    /**
     * Deletes our texture from the GPU, if we have one.
     */
    public static void deleteTexture(DisplayModule module) {
        final RenderData data = RENDER_DATA.get(module);
        if (data != null) {
            data.delete();
        }
    }

    private record RenderData(DynamicTexture texture, ResourceLocation textureId, RenderType renderType) {
        private void write(int[] image) {
            final NativeImage nativeImage = texture.getPixels();
            if (nativeImage == null) {
                return;
            }

            int ip = 0;
            for (int iy = 0; iy < DisplayModule.RESOLUTION; iy++) {
                for (int ix = 0; ix < DisplayModule.RESOLUTION; ix++, ip++) {
                    final int argb = image[ip];
                    final int a = FastColor.ARGB32.alpha(argb);
                    final int b = FastColor.ARGB32.blue(argb);
                    final int g = FastColor.ARGB32.green(argb);
                    final int r = FastColor.ARGB32.red(argb);
                    nativeImage.setPixelRGBA(ix, iy, FastColor.ABGR32.color(a, b, g, r));
                }
            }

            texture.upload();
        }

        private void delete() {
            if (textureId != null) {
                Minecraft.getInstance().doRunTask(() ->
                    Minecraft.getInstance().getTextureManager().release(textureId));
            }

            if (texture != null) {
                Minecraft.getInstance().doRunTask(texture::close);
            }
        }
    }
}

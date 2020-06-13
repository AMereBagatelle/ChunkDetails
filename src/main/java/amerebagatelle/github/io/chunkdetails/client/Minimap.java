package amerebagatelle.github.io.chunkdetails.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class Minimap {
    private MinecraftClient client = MinecraftClient.getInstance();

    public Minimap() {
    }

    public void draw(int x, int y, int width, int height) {
        if(client.currentScreen == null) {
            this.drawBox(x, y, width, height, 0.5f, 0.5f, 0.5f);
            this.renderChunkGrid(x, y, 16, 16);
        }
    }

    public void renderChunkGrid(int drawX, int drawY, int width, int height) {

    }

    public void drawBox(int x, int y, int width, int height, float red, float blue, float green) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        GlStateManager.blendFuncSeparate(GlStateManager.SrcFactor.SRC_COLOR.value, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA.value, GlStateManager.SrcFactor.ONE.value, GlStateManager.DstFactor.ZERO.value);
        RenderSystem.color3f(red, blue, green);

        builder.begin(7, VertexFormats.POSITION);
        builder.vertex(x, y, 0.0).next();
        builder.vertex(x, y + height, 0.0).next();
        builder.vertex(x + width, y + height, 0.0).next();
        builder.vertex(x + width, y, 0.0).next();

        tessellator.draw();

        GlStateManager.enableTexture();
        GlStateManager.disableBlend();
    }
}

package amerebagatelle.github.io.chunkdetails.client;

import amerebagatelle.github.io.chunkdetails.utils.RenderHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class Minimap {
    public static final Minimap INSTANCE = new Minimap();
    private final MinecraftClient client = MinecraftClient.getInstance();
    public boolean isActive = false;


    public Minimap() {
    }

    public void draw(int x, int y, int width, int height) {
        if(client.currentScreen == null) {
            RenderHelper.drawBox(x, y, width, height, 0.5f, 0.5f, 0.5f);
            this.renderChunkGrid(x, y, 16, 16);
        }
    }

    public void renderChunkGrid(int drawX, int drawY, int width, int height) {

    }
}

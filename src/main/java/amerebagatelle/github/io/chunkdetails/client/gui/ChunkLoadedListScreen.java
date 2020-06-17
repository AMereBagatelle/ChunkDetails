package amerebagatelle.github.io.chunkdetails.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.ChunkPos;

import java.util.LinkedHashMap;

public class ChunkLoadedListScreen extends Screen {
    // NOTE:  This is how the system should work:
    // NOTE:  The screen should update every tick, and get the current list of chunks
    // NOTE:  The screen should also be sure to never try and get a list of chunks that doesn't exist
    // NOTE:  Implement server/client handshake alongside this screen
    public static LinkedHashMap<String, ChunkPos> loadedChunks = new LinkedHashMap<>();

    public ChunkLoadedListScreen() {
        super(new LiteralText("ActiveChunkTicketScreen"));
    }

    @Override
    public void init(MinecraftClient client, int width, int height) {
        super.init(client, width, height);
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        super.render(mouseX, mouseY, delta);
    }
}

package amerebagatelle.github.io.chunkdetails.client.gui;

import amerebagatelle.github.io.chunkdetails.utils.ChunkTicketList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class ChunkLoadedListScreen extends Screen {
    // NOTE:  This is how the system should work:
    // NOTE:  The screen should update every tick, and get the current list of chunks
    // NOTE:  The screen should also be sure to never try and get a list of chunks that doesn't exist
    // NOTE:  Implement server/client handshake alongside this screen
    public static ChunkTicketList loadedChunks = new ChunkTicketList();
    private ChunkTicketList lastLoadedChunks = new ChunkTicketList();
    public ChunkLoadedListWidget loadedListWidget;

    public ChunkLoadedListScreen() {
        super(new LiteralText("ActiveChunkTicketScreen"));
    }

    @Override
    public void init(MinecraftClient client, int width, int height) {
        super.init(client, width, height);
        // ? Come back to these values, tweak them
        loadedListWidget = new ChunkLoadedListWidget(this.client, this.width, this.height, 40, this.height - 50, 15);
        this.children.add(loadedListWidget);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices, 0);
        if(loadedChunks != lastLoadedChunks) {
            loadedListWidget.updateEntries(loadedChunks);
        }
        lastLoadedChunks = loadedChunks;
        drawCenteredString(matrices, client.textRenderer, "Active Chunk Tickets", width/2, 10, 16777215);
        loadedListWidget.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

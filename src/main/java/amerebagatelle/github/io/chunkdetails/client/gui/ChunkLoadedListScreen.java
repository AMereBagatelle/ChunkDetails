package amerebagatelle.github.io.chunkdetails.client.gui;

import amerebagatelle.github.io.chunkdetails.client.Minimap;
import amerebagatelle.github.io.chunkdetails.utils.ChunkTicketList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class ChunkLoadedListScreen extends Screen {
    public static ChunkTicketList loadedChunks = new ChunkTicketList();
    private ChunkTicketList lastLoadedChunks = new ChunkTicketList();
    public ChunkLoadedListWidget loadedListWidget;

    private ButtonWidget minimapButton;

    public ChunkLoadedListScreen() {
        super(new LiteralText("ActiveChunkTicketScreen"));
    }

    @Override
    public void init(MinecraftClient client, int width, int height) {
        super.init(client, width, height);
        minimapButton = this.addButton(new ButtonWidget(10, height-30, 150, 20, new LiteralText("EXPERIMENTAL Minimap " + Minimap.INSTANCE.isActive), (onPress) -> {
            Minimap.INSTANCE.isActive = true;
        }));
        loadedListWidget = new ChunkLoadedListWidget(this.client, this.width, this.height, 40, this.height - 50, 15);
        this.children.add(loadedListWidget);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if(loadedChunks != lastLoadedChunks) {
            loadedListWidget.updateEntries(loadedChunks);
        }
        lastLoadedChunks = loadedChunks;
        minimapButton.setMessage(new LiteralText("EXPERIMENTAL Minimap " + Minimap.INSTANCE.isActive));
        this.renderBackground(matrices, 0);
        drawCenteredString(matrices, client.textRenderer, "Active Chunk Tickets", width/2, 10, 16777215);
        loadedListWidget.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

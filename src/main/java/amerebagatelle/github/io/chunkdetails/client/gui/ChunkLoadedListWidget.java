package amerebagatelle.github.io.chunkdetails.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.util.math.ChunkPos;

import java.util.LinkedHashMap;

public class ChunkLoadedListWidget extends EntryListWidget<ChunkLoadedListWidget.Entry> {

    public ChunkLoadedListWidget(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
        super(client, width, height, top, bottom, itemHeight);
    }

    public void updateEntries(LinkedHashMap<String, ChunkPos> list) {
        this.clearEntries();
        list.forEach((name, position) -> {
            this.addEntry(new LoadedChunkEntry(name, position));
        });
    }

    public class LoadedChunkEntry extends ChunkLoadedListWidget.Entry {
        public final String name;
        public final ChunkPos position;

        public LoadedChunkEntry(String name, ChunkPos position) {
            this.name = name;
            this.position = position;
        }

        @Override
        public void render(int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovering, float delta) {
            TextRenderer renderer = ChunkLoadedListWidget.this.minecraft.textRenderer;
            // ? Another set of values that need fixing
            ChunkLoadedListWidget.this.drawString(renderer, name, x+5, y+width/2, 16777215);
        }
    }

    @Environment(EnvType.CLIENT)
    public abstract static class Entry extends AlwaysSelectedEntryListWidget.Entry<ChunkLoadedListWidget.Entry> {
    }
}

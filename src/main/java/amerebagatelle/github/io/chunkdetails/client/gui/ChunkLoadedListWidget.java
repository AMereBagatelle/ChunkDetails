package amerebagatelle.github.io.chunkdetails.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ChunkPos;

import java.util.*;

public class ChunkLoadedListWidget extends AlwaysSelectedEntryListWidget<ChunkLoadedListWidget.Entry> {

    public ChunkLoadedListWidget(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
        super(client, width, height, top, bottom, itemHeight);
    }

    public List<Entry> getEntries() {
        return this.children();
    }

    public void updateEntries(HashMap<ChunkPos, String> list) {
        this.clearEntries();
        // Clear duplicates before updating
        final Iterator<Map.Entry<ChunkPos, String>> iter = list.entrySet().iterator();
        final HashSet<ChunkPos> valueSet = new HashSet<>();
        while (iter.hasNext()) {
            final Map.Entry<ChunkPos, String> next = iter.next();
            if (!valueSet.add(next.getKey())) {
                iter.remove();
            }
        }
        list.forEach((position, name) -> {
            if(position != null && name != null) this.addEntry(new LoadedChunkEntry(name, position));
        });
    }

    public void setSelected(LoadedChunkEntry entry) {
        super.setSelected(entry);
        this.ensureVisible(entry);
    }

    public class LoadedChunkEntry extends ChunkLoadedListWidget.Entry {
        public final String name;
        public final ChunkPos position;

        public LoadedChunkEntry(String name, ChunkPos position) {
            this.name = name;
            this.position = position;
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovering, float delta) {
            TextRenderer renderer = ChunkLoadedListWidget.this.client.textRenderer;
            // ? Another set of values that need fixing
            int drawY = y+height/2-4;
            ChunkLoadedListWidget.this.drawStringWithShadow(matrices, renderer, name, x+5, drawY, 16777215);
            ChunkLoadedListWidget.this.drawStringWithShadow(matrices, renderer, Integer.toString(position.x), x+55, drawY, 16777215);
            ChunkLoadedListWidget.this.drawStringWithShadow(matrices, renderer, Integer.toString(position.z), x+75, drawY, 16777215);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            ChunkLoadedListWidget.this.setSelected(this);
            return false;
        }

        @Override
        public boolean mouseReleased(double mouseX, double mouseY, int button) {
            return false;
        }
    }

    @Environment(EnvType.CLIENT)
    public abstract static class Entry extends AlwaysSelectedEntryListWidget.Entry<ChunkLoadedListWidget.Entry> {
    }
}

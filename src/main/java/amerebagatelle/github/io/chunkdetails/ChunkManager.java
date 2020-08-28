package amerebagatelle.github.io.chunkdetails;

import amerebagatelle.github.io.chunkdetails.logger.LogManager;
import amerebagatelle.github.io.chunkdetails.logger.LoggerType;
import amerebagatelle.github.io.chunkdetails.mixin.fake.ChunkTicketFake;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.HashMap;

public class ChunkManager {
    public static ChunkManager INSTANCE = new ChunkManager();

    private final HashMap<Long, ChunkTicketFake<?>> loadedChunks = new HashMap<>();

    public void addTicket(ChunkTicketFake<?> ticket) {
        if(ticket.getArgument() instanceof ChunkPos) {
            loadedChunks.putIfAbsent(((ChunkPos) ticket.getArgument()).toLong(), ticket);
            LogManager.INSTANCE.logMessage(chunkTicketToString(ticket), LoggerType.CHUNK_LOADED);
        }
    }

    public void removeTicket(long pos, ChunkTicketFake<?> ticket) {
        loadedChunks.remove(pos);
        if(ticket.getArgument() instanceof ChunkPos) {
            LogManager.INSTANCE.logMessage(chunkTicketToString(ticket), LoggerType.CHUNK_UNLOADED);
        }
    }

    public boolean hasTicketAtPos(long pos) {
        return loadedChunks.containsKey(pos);
    }

    private Text chunkTicketToString(ChunkTicketFake<?> ticket) {
        BlockPos pos = ((ChunkPos) ticket.getArgument()).getCenterBlockPos();
        Text argument = new LiteralText(ticket.getArgument().toString()).styled(style -> {
            return style.withColor(Formatting.GREEN).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText("Block: " + pos.getX() + " " + pos.getZ())));
        });
        return new TranslatableText("Type: %s Chunk %s Priority: %s", ticket.getType(), argument, ticket.getLevel());
    }
}

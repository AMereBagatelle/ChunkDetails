package amerebagatelle.github.io.chunkdetails;

import amerebagatelle.github.io.chunkdetails.logger.LogManager;
import amerebagatelle.github.io.chunkdetails.logger.LoggerType;
import amerebagatelle.github.io.chunkdetails.mixin.fake.ChunkTicketFake;
import net.minecraft.util.math.ChunkPos;

import java.util.HashMap;

public class ChunkManager {
    public static ChunkManager INSTANCE = new ChunkManager();

    private final HashMap<Long, ChunkTicketFake<?>> loadedChunks = new HashMap<>();

    public void addTicket(ChunkTicketFake<?> ticket) {
        if(ticket.getArgument() instanceof ChunkPos) {
            loadedChunks.putIfAbsent(((ChunkPos) ticket.getArgument()).toLong(), ticket);
            LogManager.INSTANCE.logMessage(ticket.toString(), LoggerType.CHUNK_LOADED);
        }
    }

    public void removeTicket(long pos, ChunkTicketFake<?> ticket) {
        loadedChunks.remove(pos);
        LogManager.INSTANCE.logMessage(ticket.toString(), LoggerType.CHUNK_UNLOADED);
    }

    public boolean hasTicketAtPos(long pos) {
        return loadedChunks.containsKey(pos);
    }
}

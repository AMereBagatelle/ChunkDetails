package amerebagatelle.github.io.chunkdetails.utils;

import net.minecraft.util.math.ChunkPos;

import java.util.LinkedHashMap;

public class ChunkTicketList extends LinkedHashMap<ChunkPos, String> {
    public void addTicket(long pos, String ticketType) {
        ChunkPos chunkPos = new ChunkPos(pos);
        if(!this.containsKey(chunkPos)) {
            this.put(chunkPos, ticketType);
        }
    }

    public void removeTicket(long pos) {
        this.remove(new ChunkPos(pos));
    }

    public boolean hasTicket(long pos) {
        return this.containsKey(new ChunkPos(pos));
    }
}

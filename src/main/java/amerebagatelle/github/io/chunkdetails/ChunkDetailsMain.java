package amerebagatelle.github.io.chunkdetails;

import amerebagatelle.github.io.chunkdetails.utils.ChunkTicketList;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.MinecraftServer;

public class ChunkDetailsMain implements ModInitializer {

    public static ChunkTicketList currentlyLoadedChunks = new ChunkTicketList();

    public static MinecraftServer server;

    @Override
    public void onInitialize() {
    }
}
package amerebagatelle.github.io.chunkdetails;

import net.fabricmc.api.ModInitializer;
import net.minecraft.server.MinecraftServer;

import java.util.HashSet;

public class ChunkDetailsMain implements ModInitializer {

    public static HashSet<Long> currentlyLoadedChunks = new HashSet<>();

    public static MinecraftServer server;

    @Override
    public void onInitialize() {
    }
}
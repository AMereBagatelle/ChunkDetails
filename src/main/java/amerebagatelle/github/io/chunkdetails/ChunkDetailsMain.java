package amerebagatelle.github.io.chunkdetails;

import net.fabricmc.api.ModInitializer;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;

public class ChunkDetailsMain implements ModInitializer {

    public static ArrayList<String> currentlyLoadedChunks = new ArrayList<>();

    public static MinecraftServer server;

    @Override
    public void onInitialize() {
    }
}
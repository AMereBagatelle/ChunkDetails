package amerebagatelle.github.io.chunkdetails;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class ChunkDetailsMain implements ModInitializer {
    public static Identifier CHUNK_STATUS_RECIEVED_PACKET = new Identifier("chunkdetails", "chunkstatus");

    public static ArrayList<String> currentlyLoadedChunks = new ArrayList<String>();

    @Override
    public void onInitialize() {
    }
}

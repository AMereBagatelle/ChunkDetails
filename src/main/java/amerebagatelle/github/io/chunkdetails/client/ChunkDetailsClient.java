package amerebagatelle.github.io.chunkdetails.client;

import amerebagatelle.github.io.chunkdetails.ChunkDetailsMain;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class ChunkDetailsClient implements ClientModInitializer {
    public static Minimap minimap;

    @Override
    public void onInitializeClient() {
        minimap = new Minimap();
    }
}

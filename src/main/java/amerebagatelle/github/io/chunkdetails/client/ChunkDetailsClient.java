package amerebagatelle.github.io.chunkdetails.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ChunkDetailsClient implements ClientModInitializer {
    public static Minimap minimap;

    @Override
    public void onInitializeClient() {
        minimap = new Minimap();
    }
}

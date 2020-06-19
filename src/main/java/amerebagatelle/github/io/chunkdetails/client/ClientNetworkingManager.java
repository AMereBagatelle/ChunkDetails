package amerebagatelle.github.io.chunkdetails.client;

import amerebagatelle.github.io.chunkdetails.client.gui.ChunkLoadedListScreen;
import amerebagatelle.github.io.chunkdetails.utils.Packets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

@Environment(EnvType.CLIENT)
public class ClientNetworkingManager {
    public static ClientNetworkingManager INSTANCE = new ClientNetworkingManager();
    public static final MinecraftClient client = MinecraftClient.getInstance();

    public ClientNetworkingManager() {

    }

    public void sendCustomPayload(Identifier channel, PacketByteBuf buf) {
        client.getNetworkHandler().sendPacket(new CustomPayloadC2SPacket(channel, buf));
    }

    public void processCustomPayload(CustomPayloadS2CPacket packet) {
        Identifier channel = packet.getChannel();
        if(channel == Packets.CHUNK_TICKET_ADD_PACKET) {
            onChunkTicketAdd(packet.getData());
        } else if(channel == Packets.CHUNK_TICKET_REMOVE_PACKET) {
            onChunkTicketRemove(packet.getData());
        }
    }

    public void onChunkTicketAdd(PacketByteBuf buf) {
        String chunkTicketType = buf.readString();
        long chunkPosPacked = buf.readLong();
        client.execute(() -> ChunkLoadedListScreen.loadedChunks.addTicket(chunkPosPacked, chunkTicketType));
    }

    public void onChunkTicketRemove(PacketByteBuf buf) {
        long chunkPosPacked = buf.readLong();
        client.execute(() -> {
            ChunkLoadedListScreen.loadedChunks.removeTicket(chunkPosPacked);
        });
    }
}

package amerebagatelle.github.io.chunkdetails.utils;

import amerebagatelle.github.io.chunkdetails.mixin.CustomPayloadC2SPacketFake;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

import java.util.ArrayList;

public class ServerNetworkingManager {
    public static ServerNetworkingManager INSTANCE = new ServerNetworkingManager();
    public MinecraftServer server;
    public ArrayList<ServerPlayerEntity> connectedClients = new ArrayList<>();

    public void sendCustomPayload(Identifier packetType, PacketByteBuf buf) {
        for (ServerPlayerEntity client : connectedClients) {
            client.networkHandler.connection.send(new CustomPayloadS2CPacket(packetType, buf));
        }
    }

    public void processCustomPayload(CustomPayloadC2SPacketFake packet) {
        Identifier channel = packet.getChannel();
        PacketByteBuf buf = packet.getData();
        if(channel == Packets.CHUNK_DETAILS_HELLO) {
            onHello(buf);
        }
    }

    public void onHello(PacketByteBuf buf) {

    }

    public void setServer(MinecraftServer server) {
        this.server = server;
    }
}
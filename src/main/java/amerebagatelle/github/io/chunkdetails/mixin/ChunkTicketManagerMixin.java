package amerebagatelle.github.io.chunkdetails.mixin;

import amerebagatelle.github.io.chunkdetails.ChunkDetailsMain;
import amerebagatelle.github.io.chunkdetails.command.LogChunkDetails;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.*;
import net.minecraft.text.LiteralText;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.ChunkPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkTicketManager.class)
public class ChunkTicketManagerMixin {

    @Inject(method = "Lnet/minecraft/server/world/ChunkTicketManager;addTicket(JLnet/minecraft/server/world/ChunkTicket;)V", at=@At("HEAD"))
    public <T> void addTicket(long position, ChunkTicket<?> chunkTicket, CallbackInfo ci) {
        if(!ChunkDetailsMain.currentlyLoadedChunks.contains(chunkTicket.toString()) && ChunkDetailsMain.server != null) {
            for (String serverPlayerEntity : LogChunkDetails.loggingPlayerList) { // TODO: Find a better way to get to the server
                ChunkDetailsMain.server.getPlayerManager().getPlayer(serverPlayerEntity).sendChatMessage(new LiteralText(chunkTicket.toString()), MessageType.SYSTEM);
            }
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeString(chunkTicket.getType().toString());
            buf.writeLong(((ChunkPos)((ChunkTicketMixin<?>)(Object)chunkTicket).getArgument()).toLong());
            ChunkDetailsMain.server.getPlayerManager().sendToAll(new CustomPayloadS2CPacket(ChunkDetailsMain.CHUNK_STATUS_RECIEVED_PACKET, buf));
            ChunkDetailsMain.currentlyLoadedChunks.add(chunkTicket.toString());
        }
    }
}
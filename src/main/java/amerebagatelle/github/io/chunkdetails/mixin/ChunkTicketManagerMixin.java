package amerebagatelle.github.io.chunkdetails.mixin;

import amerebagatelle.github.io.chunkdetails.ChunkDetailsMain;
import amerebagatelle.github.io.chunkdetails.command.LogChunkDetails;
import amerebagatelle.github.io.chunkdetails.utils.Packets;
import io.netty.buffer.Unpooled;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.server.world.ChunkTicket;
import net.minecraft.server.world.ChunkTicketManager;
import net.minecraft.text.LiteralText;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkTicketManager.class)
public class ChunkTicketManagerMixin {

    @Inject(method = "Lnet/minecraft/server/world/ChunkTicketManager;addTicket(JLnet/minecraft/server/world/ChunkTicket;)V", at=@At("HEAD"))
    public <T> void addTicket(long position, ChunkTicket<?> chunkTicket, CallbackInfo ci) {
        if(!ChunkDetailsMain.currentlyLoadedChunks.contains(chunkTicket.toString()) && ChunkDetailsMain.server != null) {
            for (String serverPlayerEntity : LogChunkDetails.loggingPlayerList) {
                ChunkDetailsMain.server.getPlayerManager().getPlayer(serverPlayerEntity).sendChatMessage(new LiteralText(chunkTicket.toString()), MessageType.SYSTEM);
            }
            if(((ChunkTicketMixin<?>)(Object)chunkTicket).getArgument() instanceof ChunkPos) {
                PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                buf.writeString(chunkTicket.getType().toString());
                buf.writeLong(((ChunkPos) ((ChunkTicketMixin<?>) (Object) chunkTicket).getArgument()).toLong());
                ChunkDetailsMain.server.getPlayerManager().sendToAll(new CustomPayloadS2CPacket(Packets.CHUNK_STATUS_RECIEVED_PACKET, buf));
            }
            ChunkDetailsMain.currentlyLoadedChunks.add(chunkTicket.toString());
        }
    }
}
package amerebagatelle.github.io.chunkdetails.mixin;

import amerebagatelle.github.io.chunkdetails.ChunkDetailsMain;
import amerebagatelle.github.io.chunkdetails.utils.Packets;
import amerebagatelle.github.io.chunkdetails.utils.ServerNetworkingManager;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ChunkTicket;
import net.minecraft.server.world.ChunkTicketManager;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkTicketManager.class)
public class ChunkTicketManagerMixin {

    /**
     * Sends notification of the recieved ticket to the proper clients
     */
    @Inject(method = "addTicket(JLnet/minecraft/server/world/ChunkTicket;)V", at=@At("HEAD"))
    public <T> void onAddTicket(long position, ChunkTicket<?> chunkTicket, CallbackInfo ci) {
        ChunkTicketFake<?> chunkTicketFake = (ChunkTicketFake<?>)(Object)chunkTicket;
        if(!ChunkDetailsMain.currentlyLoadedChunks.hasTicket(position) && ChunkDetailsMain.server != null) {
            ServerNetworkingManager.INSTANCE.sendChunkLogMessage(new LiteralText(chunkTicketFake.toString()), chunkTicketFake.getType().toString());
            if(chunkTicketFake.getArgument() instanceof ChunkPos) {
                PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                buf.writeString(chunkTicket.getType().toString());
                buf.writeLong(((ChunkPos) chunkTicketFake.getArgument()).toLong());
                ServerNetworkingManager.INSTANCE.sendCustomPayload(Packets.CHUNK_TICKET_ADD_PACKET, buf);
            }
            ChunkDetailsMain.currentlyLoadedChunks.addTicket(position, chunkTicket.getType().toString());
        }
    }

    /**
     * Sends notification of the removed tickets to the proper clients
     */
    @Inject(method = "removeTicket(JLnet/minecraft/server/world/ChunkTicket;)V", at = @At("TAIL"))
    public void onRemoveTicket(long pos, ChunkTicket<?> ticket, CallbackInfo ci) {
        ServerNetworkingManager.INSTANCE.sendChunkLogMessage(new LiteralText(ticket.toString()), ((ChunkTicketFake<?>)(Object)ticket).getType().toString());
        ChunkDetailsMain.currentlyLoadedChunks.removeTicket(pos);
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeLong(pos);
        ServerNetworkingManager.INSTANCE.sendCustomPayload(Packets.CHUNK_TICKET_REMOVE_PACKET, buf);
    }
}
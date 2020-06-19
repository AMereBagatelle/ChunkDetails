package amerebagatelle.github.io.chunkdetails.mixin;

import amerebagatelle.github.io.chunkdetails.ChunkDetailsMain;
import amerebagatelle.github.io.chunkdetails.command.LogChunkDetails;
import amerebagatelle.github.io.chunkdetails.utils.Packets;
import amerebagatelle.github.io.chunkdetails.utils.ServerNetworkingManager;
import io.netty.buffer.Unpooled;
import net.minecraft.network.MessageType;
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
    public <T> void onAddTicket(long position, ChunkTicket<?> chunkTicket, CallbackInfo ci) {
        if(!ChunkDetailsMain.currentlyLoadedChunks.hasTicket(position) && ChunkDetailsMain.server != null) {
            for (String serverPlayerEntity : LogChunkDetails.loggingPlayerList) {
                ChunkDetailsMain.server.getPlayerManager().getPlayer(serverPlayerEntity).sendChatMessage(new LiteralText("Added: " + chunkTicket.toString()), MessageType.SYSTEM);
            }
            if(((ChunkTicketFake<?>)(Object)chunkTicket).getArgument() instanceof ChunkPos) {
                PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                buf.writeString(chunkTicket.getType().toString());
                buf.writeLong(((ChunkPos) ((ChunkTicketFake<?>) (Object) chunkTicket).getArgument()).toLong());
                ServerNetworkingManager.INSTANCE.sendCustomPayload(Packets.CHUNK_TICKET_ADD_PACKET, buf);
            }
            ChunkDetailsMain.currentlyLoadedChunks.addTicket(position, chunkTicket.getType().toString());
        }
    }

    @Inject(method = "removeTicket(JLnet/minecraft/server/world/ChunkTicket;)V", at = @At("TAIL"))
    public void onRemoveTicket(long pos, ChunkTicket<?> ticket, CallbackInfo ci) {
        if(ChunkDetailsMain.currentlyLoadedChunks.hasTicket(pos)) {
            for (String serverPlayerEntity : LogChunkDetails.loggingPlayerList) {
                ChunkDetailsMain.server.getPlayerManager().getPlayer(serverPlayerEntity).sendChatMessage(new LiteralText("Removed: " + ticket.toString()), MessageType.SYSTEM);
            }
        }
        ChunkDetailsMain.currentlyLoadedChunks.removeTicketForLocation(pos);
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeLong(pos);
        ServerNetworkingManager.INSTANCE.sendCustomPayload(Packets.CHUNK_TICKET_REMOVE_PACKET, buf);
    }
}
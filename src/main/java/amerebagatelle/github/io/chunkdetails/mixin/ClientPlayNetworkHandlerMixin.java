package amerebagatelle.github.io.chunkdetails.mixin;

import amerebagatelle.github.io.chunkdetails.client.gui.ChunkLoadedListScreen;
import amerebagatelle.github.io.chunkdetails.utils.ServerNetworkingManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Inject(method = "onCustomPayload", at = @At("HEAD"), cancellable = true)
    public void onCustomPayload(CustomPayloadS2CPacket packet, CallbackInfo ci) {
            if(packet.getChannel() == ServerNetworkingManager.CHUNK_STATUS_PACKET) {
                PacketByteBuf buf = packet.getData();
                String chunkTicketType = buf.readString();
                long chunkPosPacked = buf.readLong();
                ChunkLoadedListScreen.loadedChunks.put(chunkTicketType, new ChunkPos(chunkPosPacked));
                ci.cancel();
            }
    }
}
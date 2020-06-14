package amerebagatelle.github.io.chunkdetails.mixin;

import amerebagatelle.github.io.chunkdetails.ChunkDetailsMain;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Inject(method = "onCustomPayload", at = @At("RETURN"))
    public void onCustomPayload(CustomPayloadS2CPacket packet, CallbackInfo ci) {
            if(packet.getChannel() == ChunkDetailsMain.CHUNK_STATUS_RECIEVED_PACKET) {
                PacketByteBuf buf = packet.getData();
                String chunkTicketType = buf.readString();
                long chunkPosPacked = buf.readLong();
                MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("Ticket of type " + chunkTicketType + " at chunk " + Integer.toString(ChunkPos.getPackedX(chunkPosPacked)) + " " + Integer.toString(ChunkPos.getPackedZ(chunkPosPacked))));
            }
    }
}
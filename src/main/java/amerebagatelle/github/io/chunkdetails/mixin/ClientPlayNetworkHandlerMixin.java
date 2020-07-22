package amerebagatelle.github.io.chunkdetails.mixin;

import amerebagatelle.github.io.chunkdetails.client.ClientNetworkingManager;
import amerebagatelle.github.io.chunkdetails.utils.Packets;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Shadow private MinecraftClient client;

    /**
     * Takes ChunkDetails packets and passes them to ClientNetworkingManager
     */
    @Inject(method = "onCustomPayload", at = @At("HEAD"), cancellable = true)
    public void onCustomPayload(CustomPayloadS2CPacket packet, CallbackInfo ci) {
        if(packet.getChannel().getNamespace() == "chunkdetails") {
            ClientNetworkingManager.INSTANCE.processCustomPayload(packet);
            ci.cancel();
        }
    }

    /**
     * Sends a connection request to the server to see if it has ChunkDetails
     */
    @Inject(method = "onGameJoin", at = @At("TAIL"))
    public void onConnected(GameJoinS2CPacket packet, CallbackInfo ci) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeUuid(client.player.getUuid());
        ClientNetworkingManager.INSTANCE.sendCustomPayload(Packets.CHUNK_DETAILS_HELLO, buf);
    }
}
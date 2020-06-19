package amerebagatelle.github.io.chunkdetails.mixin;

import amerebagatelle.github.io.chunkdetails.utils.ServerNetworkingManager;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Inject(method = "onCustomPayload", at = @At("HEAD"))
    public void onCustomPayload(CustomPayloadC2SPacket packet, CallbackInfo ci) {
        CustomPayloadC2SPacketFake packetFake = (CustomPayloadC2SPacketFake)packet;
        if(packetFake.getChannel().getNamespace() == "chunkdetails") {
            // TODO: Call custom payload in ServerNetworkingManager
            ServerNetworkingManager.INSTANCE.processCustomPayload(packetFake);
        }
    }
}
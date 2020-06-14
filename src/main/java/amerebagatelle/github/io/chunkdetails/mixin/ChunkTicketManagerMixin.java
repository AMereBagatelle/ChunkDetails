package amerebagatelle.github.io.chunkdetails.mixin;

import amerebagatelle.github.io.chunkdetails.ChunkDetailsMain;
import amerebagatelle.github.io.chunkdetails.command.LogChunkDetails;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.MessageType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.*;
import net.minecraft.text.LiteralText;
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
        if(!ChunkDetailsMain.currentlyLoadedChunks.contains(chunkTicket.toString())) {
            for (String serverPlayerEntity : LogChunkDetails.loggingPlayerList) { // TODO: Find a better way to get to the server
                ((MinecraftServer)FabricLoader.getInstance().getGameInstance()).getPlayerManager().getPlayer(serverPlayerEntity).sendChatMessage(new LiteralText(chunkTicket.toString()), MessageType.SYSTEM);
            }
            ChunkDetailsMain.currentlyLoadedChunks.add(chunkTicket.toString());
        }
    }
}
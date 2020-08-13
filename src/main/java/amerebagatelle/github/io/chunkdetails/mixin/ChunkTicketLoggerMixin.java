package amerebagatelle.github.io.chunkdetails.mixin;

import amerebagatelle.github.io.chunkdetails.ChunkManager;
import amerebagatelle.github.io.chunkdetails.mixin.fake.ChunkTicketFake;
import net.minecraft.server.world.ChunkTicket;
import net.minecraft.server.world.ChunkTicketManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkTicketManager.class)
public class ChunkTicketLoggerMixin {


    @Inject(method = "addTicket(JLnet/minecraft/server/world/ChunkTicket;)V", at = @At("HEAD"))
    public <T> void onAddTicket(long position, ChunkTicket<?> chunkTicket, CallbackInfo ci) {
        if(!ChunkManager.INSTANCE.hasTicketAtPos(position)) {
            ChunkTicketFake<?> chunkTicketFake = (ChunkTicketFake<?>) (Object) chunkTicket;
            ChunkManager.INSTANCE.addTicket(chunkTicketFake);
        }
    }

    @Inject(method = "removeTicket(JLnet/minecraft/server/world/ChunkTicket;)V", at = @At("HEAD"))
    public void removeTicket(long pos, ChunkTicket<?> ticket, CallbackInfo ci) {
        ChunkManager.INSTANCE.removeTicket(pos, (ChunkTicketFake<?>)(Object)ticket);
    }
}

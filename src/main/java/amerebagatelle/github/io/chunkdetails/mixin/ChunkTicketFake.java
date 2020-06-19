package amerebagatelle.github.io.chunkdetails.mixin;

import net.minecraft.server.world.ChunkTicket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChunkTicket.class)
public interface ChunkTicketFake<T> {
    @Accessor
    T getArgument();
}
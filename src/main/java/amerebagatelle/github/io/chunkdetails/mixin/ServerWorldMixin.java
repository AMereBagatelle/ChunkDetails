package amerebagatelle.github.io.chunkdetails.mixin;

import amerebagatelle.github.io.chunkdetails.ChunkDetailsMain;
import amerebagatelle.github.io.chunkdetails.utils.ServerNetworkingManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @Shadow @Final private MinecraftServer server;

    /**
     * I have no good excuse for this code... will be fixed at some point
     */
    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        ServerNetworkingManager.INSTANCE.setServer(server);
        ChunkDetailsMain.server = server; // TODO fix this monstrosity
    }
}
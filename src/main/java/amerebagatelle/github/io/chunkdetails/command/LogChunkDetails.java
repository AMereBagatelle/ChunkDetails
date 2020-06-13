package amerebagatelle.github.io.chunkdetails.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;

import static net.minecraft.server.command.CommandManager.literal;

public class LogChunkDetails {
    public static ArrayList<ServerPlayerEntity> loggingPlayerList = new ArrayList<>();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> logChunk = literal("logChunk")
                .executes(ctx -> {
                    if(loggingPlayerList.contains(ctx.getSource().getPlayer())) {
                        loggingPlayerList.remove(ctx.getSource().getPlayer());
                    } else {
                        loggingPlayerList.add(ctx.getSource().getPlayer());
                    }
                    return 1;
                });

        dispatcher.register(logChunk);
    }
}

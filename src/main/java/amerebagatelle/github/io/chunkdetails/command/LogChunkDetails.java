package amerebagatelle.github.io.chunkdetails.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;

import static net.minecraft.server.command.CommandManager.literal;

public class LogChunkDetails {
    public static ArrayList<String> loggingPlayerList = new ArrayList<>();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> logChunk = literal("logChunk")
                .executes(ctx -> {
                    if(loggingPlayerList.contains(ctx.getSource().getPlayer().getName().asFormattedString())) {
                        loggingPlayerList.remove(ctx.getSource().getPlayer().getName().asFormattedString());
                    } else {
                        loggingPlayerList.add(ctx.getSource().getPlayer().getName().asFormattedString());
                    }
                    return 1;
                });

        dispatcher.register(logChunk);
    }
}

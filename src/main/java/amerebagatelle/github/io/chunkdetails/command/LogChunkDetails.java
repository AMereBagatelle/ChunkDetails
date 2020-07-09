package amerebagatelle.github.io.chunkdetails.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import java.util.HashMap;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class LogChunkDetails {
    public static HashMap<String, String> loggingPlayerList = new HashMap<>();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> logChunk = literal("logChunk")
                .then(argument("ticketType", StringArgumentType.string())
                        .executes((ctx) -> {
                            loggingPlayerList.put(ctx.getSource().getPlayer().getName().asString(), StringArgumentType.getString(ctx, "ticketType"));
                            ctx.getSource().sendFeedback(new LiteralText("Added for ticket type: " + StringArgumentType.getString(ctx, "ticketType")), true);
                            return 1;
                        }))
                .executes(ctx -> {
                    if(loggingPlayerList.containsKey(ctx.getSource().getPlayer().getName().asString())) {
                        loggingPlayerList.remove(ctx.getSource().getPlayer().getName().asString());
                        ctx.getSource().sendFeedback(new LiteralText("Removed from ChunkLog List"), true);
                    } else {
                        loggingPlayerList.put(ctx.getSource().getPlayer().getName().asString(), "all");
                        ctx.getSource().sendFeedback(new LiteralText("Added for ticket type: all"), true);
                    }
                    return 1;
                });

        dispatcher.register(logChunk);
    }
}

package amerebagatelle.github.io.chunkdetails.command;

import amerebagatelle.github.io.chunkdetails.logger.LogManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ChunkDetailsCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> chunkdetails = literal("chunkDetails")
                .then(argument("logtype", StringArgumentType.word())
                        .suggests((c, b) -> CommandSource.suggestMatching(LogManager.INSTANCE.getLoggerTypes(), b))
                        .executes( (ctx) -> subscribe(ctx.getSource(), StringArgumentType.getString(ctx, "logtype"))));

        dispatcher.register(chunkdetails);
    }

    public static int subscribe(ServerCommandSource source, String loggerType) throws CommandSyntaxException {
        LogManager logManager = LogManager.INSTANCE;
        if(!logManager.getLoggerTypes().contains(loggerType)) {
            return 0;
        }
        if(!logManager.isPlayerSubscribed(logManager.getLoggerType(loggerType), source.getPlayer())) {
            logManager.subscribePlayer(logManager.getLoggerType(loggerType), source.getPlayer());
            source.sendFeedback(new LiteralText(String.format("%s subscribed to \"%s\" logger.", source.getPlayer().getName().getString(), loggerType)), true);
        } else {
            logManager.unsubscribePlayer(logManager.getLoggerType(loggerType), source.getPlayer());
            source.sendFeedback(new LiteralText(String.format("%s unsubscribed from \"%s\" logger.", source.getPlayer().getName().getString(), loggerType)), true);
        }
        return 1;
    }
}

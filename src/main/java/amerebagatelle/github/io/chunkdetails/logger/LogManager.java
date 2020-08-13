package amerebagatelle.github.io.chunkdetails.logger;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.util.HashMap;
import java.util.UUID;

public class LogManager {
    public static LogManager INSTANCE = new LogManager();

    private static HashMap<LoggerType, ServerPlayerEntity> subscribedPlayers;

    public static void logMessage(String message, LoggerType loggerType) {
        subscribedPlayers.forEach((logger, player) -> {
            if(logger == loggerType) {
                player.sendSystemMessage(new LiteralText(message), UUID.randomUUID());
            }
        });
    }

    public static void subscribePlayer(LoggerType loggerType, ServerPlayerEntity player) {
        subscribedPlayers.put(loggerType, player);
    }

    public static void unsubscribePlayer(LoggerType loggerType, ServerPlayerEntity player) {
        subscribedPlayers.remove(loggerType, player);
    }
}

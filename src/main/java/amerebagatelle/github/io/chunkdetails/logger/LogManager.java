package amerebagatelle.github.io.chunkdetails.logger;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class LogManager {
    public static LogManager INSTANCE = new LogManager();

    private final HashMap<LoggerType, ServerPlayerEntity> subscribedPlayers = new HashMap<>();

    public void logMessage(Text message, LoggerType loggerType) {
        Text finalMessage = message.copy().append(" Logger: " + loggerType.getName());
        subscribedPlayers.forEach((logger, player) -> {
            if(logger == loggerType) {
                player.sendSystemMessage(finalMessage, UUID.randomUUID());
            }
        });
    }

    public void subscribePlayer(LoggerType loggerType, ServerPlayerEntity player) {
        subscribedPlayers.put(loggerType, player);
    }

    public void unsubscribePlayer(LoggerType loggerType, ServerPlayerEntity player) {
        subscribedPlayers.remove(loggerType, player);
    }

    public boolean isPlayerSubscribed(LoggerType loggerType, ServerPlayerEntity playerEntity) {
        AtomicBoolean value = new AtomicBoolean(false);
        subscribedPlayers.forEach((type, player) -> {
            if(type == loggerType && player == playerEntity) {
                value.set(true);
            }
        });
        return value.get();
    }

    public Set<String> getLoggerTypes() {
        Set<String> types = new HashSet<>();
        for (LoggerType enumConstant : LoggerType.class.getEnumConstants()) {
            types.add(enumConstant.getName());
        }
        return types;
    }

    public LoggerType getLoggerType(String name) {
        for (LoggerType loggerType : LoggerType.class.getEnumConstants()) {
            if(loggerType.getName().equals(name)) return loggerType;
        }
        return null;
    }
}

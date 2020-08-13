package amerebagatelle.github.io.chunkdetails.logger;

public enum LoggerType {
    CHUNK_LOADED("loaded"),
    CHUNK_UNLOADED("unloaded"),
    CHUNK_CACHE_ADD("cache_add"),
    CHUNK_CACHE_REMOVE("cache_remove");

    private final String name;

    LoggerType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

package amerebagatelle.github.io.chunkdetails.utils;

import net.minecraft.util.Identifier;

public class Packets {
    /**
     * Packet that the client sends to the server upon connection
     */
    public static final Identifier CHUNK_DETAILS_HELLO = new Identifier("chunkdetails", "hello");

    /**
     * Notifies the client of a chunk ticket being added
     */
    public static final Identifier CHUNK_TICKET_ADD_PACKET = new Identifier("chunkdetails", "chunkadd");

    /**
     * Notifies the client of a chunk ticket being removed
     */
    public static final Identifier CHUNK_TICKET_REMOVE_PACKET = new Identifier("chunkdetails", "chunkremove");
}
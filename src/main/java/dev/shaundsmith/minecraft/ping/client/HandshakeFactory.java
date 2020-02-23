package dev.shaundsmith.minecraft.ping.client;

import lombok.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Factory for creating handshake packages for a Minecraft "ping" server interaction.
 *
 * @author Shaun Smith
 * @since 1.0.0
 */
public final class HandshakeFactory {

    /**
     * Creates a handshake packet for interacting with a Minecraft server.
     *
     * <p>The handshake packet is composed of a series of fields:
     *
     * <ol>
     *     <li>A packet ID (byte): 0x00</li>
     *     <li>A protocol version (varint): The version of the protocol used to connect to the server. -1 if unknown.</li>
     *     <li>The server address (string): The address used to connect to the server, prefixed by the string's length.</li>
     *     <li>The server port (unsigned short): The port used to connect to the server. Default 25565.</li>
     *     <li>The next state (varint): 1 for status.</li>
     * </ol>
     *
     * @param serverAddress address of the server to create the handshake for
     * @param protocolVersion protocol version of the target server. -1 if not known.
     *
     * @return a handshake packet for the provided server address
     *
     * @throws IOException if an error occurs while creating the handshake packet
     *
     * @see <a href="https://wiki.vg/Server_List_Ping">https://wiki.vg/Server_List_Ping</a>
     */
    byte[] create(@NonNull InetSocketAddress serverAddress, @NonNull VarInt protocolVersion) throws IOException {
        try (ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
             MinecraftOutputStream outputStream = new MinecraftOutputStream(byteArrayStream)) {

            outputStream.write(0x00);
            outputStream.writeVarInt(protocolVersion);
            outputStream.writeString(serverAddress.getHostString());
            outputStream.writeShort(serverAddress.getPort());
            outputStream.writeVarInt(VarInt.of(1));

            return byteArrayStream.toByteArray();
        }
    }

}

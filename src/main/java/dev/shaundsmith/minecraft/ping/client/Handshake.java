package dev.shaundsmith.minecraft.ping.client;

import lombok.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * A handshake for interacting with a Minecraft server.
 */
class Handshake {

    private final int packetId;
    private final VarInt protocolVersion;
    private final InetSocketAddress serverAddress;

    /**
     * Creates a new {@code Handshake} using the default protocol version (-1).
     *
     * @param packetId the ID of the packet byte
     * @param serverAddress the address of the server that the handshake is for
     */
    Handshake(int packetId, @NonNull InetSocketAddress serverAddress) {
        this(packetId, VarInt.of(-1), serverAddress);
    }

    /**
     * Creates a new {@code Handshake}.
     *
     * @param packetId the ID of the packet byte
     * @param protocolVersion protocol version of the target server
     * @param serverAddress the address of the server that the handshake is for
     */
    Handshake(int packetId, @NonNull VarInt protocolVersion, @NonNull InetSocketAddress serverAddress) {
        this.packetId = packetId;
        this.protocolVersion = protocolVersion;
        this.serverAddress = serverAddress;
    }

    /**
     * Creates a handshake packet.
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
     * @return a handshake packet for the provided server address
     *
     * @throws IOException if an error occurs while creating the handshake packet
     *
     * @see <a href="https://wiki.vg/Server_List_Ping">https://wiki.vg/Server_List_Ping</a>
     */
    byte[] toHandshakePacket() throws IOException {

        try (ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
             MinecraftOutputStream outputStream = new MinecraftOutputStream(byteArrayStream)) {

            outputStream.write(packetId);
            outputStream.writeVarInt(protocolVersion);
            outputStream.writeString(serverAddress.getHostString());
            outputStream.writeShort(serverAddress.getPort());
            outputStream.writeVarInt(VarInt.of(1));

            return byteArrayStream.toByteArray();
        }
    }

}

package dev.shaundsmith.minecraft.ping.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.shaundsmith.minecraft.ping.exception.MinecraftClientException;
import dev.shaundsmith.minecraft.ping.response.MinecraftStatus;
import lombok.NonNull;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Client for interacting with a Minecraft server using the "ping" protocol.
 *
 * @author Shaun Smith
 * @version 1.0.0
 */
public class MinecraftPingClient {

    private static final byte[] REQUEST_PACKET = new byte[]{0x00};

    private final SocketFactory socketFactory;
    private final ObjectMapper objectMapper;

    /**
     * Constructs a new Minecraft ping client.
     *
     * @param socketFactory factory for creating sockets for a given socket address
     * @param objectMapper  object mapper for deserializing JSON payloads from the Minecraft server
     */
    MinecraftPingClient(@NonNull SocketFactory socketFactory,
                        @NonNull ObjectMapper objectMapper) {
        this.socketFactory = socketFactory;
        this.objectMapper = objectMapper;
    }

    /**
     * Retrieves the status of a Minecraft server from a ping request.
     *
     * @param serverAddress the address of the minecraft server
     *
     * @return the status of the Minecraft server
     *
     * @throws MinecraftClientException if an error occurs whilst connecting to the server
     * @throws MinecraftClientException if the server response is incorrectly formatted
     */
    public MinecraftStatus getStatus(@NonNull InetSocketAddress serverAddress) throws MinecraftClientException {
        String jsonResponse;

        /*
           Makes two requests and then expects a response to be returned from the running Minecraft instance.
             1. Sends a 'handshake' package to the server
             2. Sends an empty request packet, containing no fields and an ID of 0x00
             3. Retrieves the response packet containing a JSON string with the Minecraft server status.

           Based on the client-server protocol described in https://wiki.vg/Server_List_Ping.
         */
        try (MinecraftSocket socket = socketFactory.createSocket(serverAddress);
             MinecraftOutputStream outputStream = socket.getOutputStream();
             MinecraftInputStream inputStream = socket.getInputStream()) {

            // 1. Send the handshake package to the server
            final VarInt protocolVersion = VarInt.of(-1);
            Handshake handshake = new Handshake(0x00, protocolVersion, serverAddress);
            outputStream.writePacket(handshake.toHandshakePacket());

            // 2. Sends the empty request packet to the server
            outputStream.writePacket(REQUEST_PACKET);

            // 3. Retrieves the response packet
            // Discard the packet size
            inputStream.readVarInt();
            // Read the packet ID - this should be 0x00
            VarInt packetId = inputStream.readVarInt();
            if (packetId.getValueAsInt() != 0x00) {
                throw new MinecraftClientException("Response package should have an ID of 0x00. Received packet with ID " + packetId.getValueAsInt());
            }

            // Read the JSON response string
            jsonResponse = inputStream.readString();
        } catch (IOException e) {
            throw new MinecraftClientException("An error occurred whilst making the request to the Minecraft server at " + serverAddress, e);
        }
        try {
            return objectMapper.readValue(jsonResponse, MinecraftStatus.class);
        } catch (IOException e) {
            throw new MinecraftClientException("Invalid response from server. Cannot parse response body: " + jsonResponse, e);
        }
    }

}

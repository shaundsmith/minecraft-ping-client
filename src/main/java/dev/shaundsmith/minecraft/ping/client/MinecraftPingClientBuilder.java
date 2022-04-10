package dev.shaundsmith.minecraft.ping.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;

import java.net.InetSocketAddress;

/**
 * Builder for creating {@link MinecraftPingClient}s.
 *
 * @author Shaun Smith
 * @version 1.0.0
 */
@SuppressWarnings("unused")
public class MinecraftPingClientBuilder {

    private final InetSocketAddress serverAddress;
    private Integer timeout = 30000;
    private ObjectMapper objectMapper;

    /**
     * Constructs a new {@code MinecraftPingClientBuilder}.
     *
     * @param serverHost the host name of the server
     * @param serverPort the port that the Minecraft server is running on
     */
    public MinecraftPingClientBuilder(@NonNull String serverHost, int serverPort) {
        this.serverAddress = new InetSocketAddress(serverHost, serverPort);
    }

    /**
     * Sets a timeout period for the {@code MinecraftPingClient} requests.
     *
     * <p>A timeout period of '0' is the equivalent of no timeout period
     *
     * @param timeout the timeout period (in milliseconds)
     *
     * @return this builder
     */
    public MinecraftPingClientBuilder withTimeout(int timeout) {

        if (timeout < 0) {
            throw new IllegalArgumentException("Timeout cannot be negative. Value: " + timeout);
        }

        this.timeout = timeout;
        return this;
    }

    /**
     * Sets the custom object mapper for parsing the JSON responses from the Minecraft server.
     *
     * @param objectMapper the object mapper
     *
     * @return this builder
     */
    public MinecraftPingClientBuilder withObjectMapper(@NonNull ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;
        return this;
    }

    /**
     * Builds the {@code MinecraftPingClient} based on the builder configuration.
     *
     * @return the {@code MinecraftPingClient}
     */
    public MinecraftPingClient build() {

        SocketFactory socketFactory = timeout == null ?
                        MinecraftSocket::new :
                        socketAddress -> new MinecraftSocket(socketAddress, timeout);

        return new MinecraftPingClient(serverAddress, socketFactory, getObjectMapper());
    }

    private ObjectMapper getObjectMapper() {

        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        return objectMapper;
    }


}

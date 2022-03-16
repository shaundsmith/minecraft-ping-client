package dev.shaundsmith.minecraft.ping.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;

/**
 * Builder for creating {@link MinecraftPingClient}s.
 *
 * @author Shaun Smith
 * @version 1.0.0
 */
public class MinecraftPingClientBuilder {

    private Integer timeout;
    private ObjectMapper objectMapper;

    /**
     * Sets a timeout period for the {@code MinecraftPingClient} requests.
     *
     * <p>A timeout period of '0' is the equivalent of no timeout
     *
     * @param timeout the timeout period (in milliseconds)
     *
     * @return this builder
     */
    public MinecraftPingClientBuilder withTimeout(int timeout) {

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

        return new MinecraftPingClient(socketFactory, getObjectMapper());
    }

    private ObjectMapper getObjectMapper() {

        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        return objectMapper;
    }


}

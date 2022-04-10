package dev.shaundsmith.minecraft.ping.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.shaundsmith.minecraft.ping.client.deserializer.MinecraftServerResponseDeserializer;
import lombok.Builder;
import lombok.Value;

/**
 * The response from a Minecraft server ping request.
 *
 * @author Shaun Smith
 * @since 1.0.0
 */
@Value
@Builder
@JsonDeserialize(using = MinecraftServerResponseDeserializer.class)
public class MinecraftServerResponse {

    /** The description of the server. */
    String serverDescription;

    /** The version of Minecraft that the server is running. */
    String serverVersion;

    /** The information about the players that are currently active on the server. */
    Players players;

    /** The mod loader that is currently running on the server. */
    ModLoader modLoader;

    /** The server icon as a data URL. */
    String serverIcon;

}

package dev.shaundsmith.minecraft.ping.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * Details about a single mod installed on a Minecraft server.
 *
 * <p>Only available to forge-based servers.
 *
 * @author Shaun Smith
 * @since 1.0.0
 */
@Value
public class Mod {

    /** Name of the mod. */
    @JsonProperty("modid") private final String name;

    /** Version number of the mod. */
    private final String version;

}

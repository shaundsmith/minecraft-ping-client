package dev.shaundsmith.minecraft.ping.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor(onConstructor_ = @JsonCreator)
public class Mod {

    /** Name of the mod. */
    @JsonProperty("modid") String name;

    /** Version number of the mod. */
    @JsonProperty("version") String version;

}

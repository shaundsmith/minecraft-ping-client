package dev.shaundsmith.minecraft.ping.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * Details about the version of Minecraft running on a server.
 *
 * @author Shaun Smith
 * @since 1.0.0
 */
@Value
public class VersionInfo {

    /** Version number of Minecraft. */
    @JsonProperty("name") private final String version;

}

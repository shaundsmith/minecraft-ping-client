package dev.shaundsmith.minecraft.ping.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

/**
 * Details about all mods available to the Minecraft server.
 *
 * @author Shaun Smith
 * @since 1.0.0
 */
@Value
public class ModInfo {

    /** Type of mod loader. */
    @JsonProperty("type") private final String modLoader;

    /** The list of mods available to the Minecraft server. */
    private final List<Mod> modList;

}

package dev.shaundsmith.minecraft.ping.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

/**
 * Details about all mods available to the Minecraft server.
 *
 * @author Shaun Smith
 * @since 1.0.0
 */
@Value
@AllArgsConstructor(onConstructor_ = @JsonCreator)
public class ModInfo {

    /** Type of mod loader. */
    @JsonProperty("type") String modLoader;

    /** The list of mods available to the Minecraft server. */
    @JsonProperty("modList") List<Mod> modList;

}

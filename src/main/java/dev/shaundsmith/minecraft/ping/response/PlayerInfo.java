package dev.shaundsmith.minecraft.ping.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

/**
 * Details about an online player currently playing on the Minecraft server.
 *
 * @author Shaun Smith
 * @since 1.0.0
 */
@Value
@AllArgsConstructor(onConstructor_ = @JsonCreator)
public class PlayerInfo {

    /** The display name of an online player. */
    @JsonProperty("name")
    @NonNull String name;

}

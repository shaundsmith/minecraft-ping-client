package dev.shaundsmith.minecraft.ping.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * Represents a Minecraft chat packet body.
 *
 * @author Shaun Smith
 * @since 1.0.0
 * @see <a href="https://wiki.vg/Chat">https://wiki.vg/Chat</a>
 */
@Value
@AllArgsConstructor(onConstructor_ = @JsonCreator)
public class Chat {

    /** Text body of the chat message. */
    @JsonProperty("text") String text;

}

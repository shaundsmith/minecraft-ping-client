package dev.shaundsmith.minecraft.ping.response;

import lombok.Value;

/**
 * Represents a Minecraft chat packet body.
 *
 * @author Shaun Smith
 * @since 1.0.0
 * @see <a href="https://wiki.vg/Chat">https://wiki.vg/Chat</a>
 */
@Value
public class Chat {

    /** Text body of the chat message. */
    private final String text;

}

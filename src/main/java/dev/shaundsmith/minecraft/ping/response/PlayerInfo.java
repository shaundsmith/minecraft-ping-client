package dev.shaundsmith.minecraft.ping.response;

import lombok.NonNull;
import lombok.Value;

/**
 * Details about an online player currently playing on the Minecraft server.
 *
 * @author Shaun Smith
 * @since 1.0.0
 */
@Value
public class PlayerInfo {

    /** The display name of an online player. */
    @NonNull private final String name;

}

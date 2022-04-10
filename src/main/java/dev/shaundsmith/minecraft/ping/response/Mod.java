package dev.shaundsmith.minecraft.ping.response;

import lombok.Value;

/**
 * Details about a single mod installed on a Minecraft server.
 *
 * @author Shaun Smith
 * @since 1.0.0
 */
@Value
public class Mod {

    /** The name of the mod. */
    String name;

    /** The version of the mod. */
    String version;

}

package dev.shaundsmith.minecraft.ping.response;

import lombok.Value;

import java.util.List;

/**
 * Details about the mod loader that is currently installed on the server.
 *
 * <p>Only available to forge-based servers.
 *
 * @author Shaun Smith
 * @since 1.0.0
 */
@Value
public class ModLoader {

    /** The name of the mod loader. */
    String name;

    /** The mods that have been loaded by the mod loader. */
    List<Mod> loadedMods;

}

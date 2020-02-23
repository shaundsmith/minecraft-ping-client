package dev.shaundsmith.minecraft.ping.response;

import lombok.Value;

/**
 * Representation of a Minecraft server's status as returned by a ping response.

 * @author Shaun Smith
 * @since 1.0.0
 * @see <a href="https://wiki.vg/Server_List_Ping#Response">https://wiki.vg/Server_List_Ping#Response</a>
 */
@Value
public class MinecraftStatus {

    /** The description (motd) of the server. */
    private final Chat description;

    /** Details about the players on the server. */
    private final PlayersInfo players;

    /** The version of the Minecraft server */
    private final VersionInfo version;

    /** The icon for the server as a Base64-encoded PNG string. */
    private final String favicon;

    /** Information about the mods on the server. Only available to forge-based servers. */
    private final ModInfo modinfo;

}

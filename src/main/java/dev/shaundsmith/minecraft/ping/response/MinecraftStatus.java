package dev.shaundsmith.minecraft.ping.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.Collections;

/**
 * Representation of a Minecraft server's status as returned by a ping response.

 * @author Shaun Smith
 * @since 1.0.0
 * @see <a href="https://wiki.vg/Server_List_Ping#Response">https://wiki.vg/Server_List_Ping#Response</a>
 */
@Value
public class MinecraftStatus {

    /** The description (motd) of the server. */
    Chat description;

    /** Details about the players on the server. */
    PlayersInfo players;

    /** The version of the Minecraft server */
    VersionInfo version;

    /** The icon for the server as a Base64-encoded PNG string. */
    String favicon;

    /** Information about the mods on the server. Only available to forge-based servers. */
    ModInfo modInfo;

    @JsonCreator
    public MinecraftStatus(@JsonProperty("description") Chat description, @JsonProperty("players") PlayersInfo players,
                           @JsonProperty("version") VersionInfo version, @JsonProperty("favicon") String favicon,
                           @JsonProperty("modinfo") ModInfo modInfo) {
        this.description = description == null ? new Chat(null) : description;
        this.players = players == null ? new PlayersInfo(0, 0, Collections.emptyList()) : players;
        this.version = version == null ? new VersionInfo(null) : version;
        this.favicon = favicon;
        this.modInfo = modInfo == null ? new ModInfo("vanilla", Collections.emptyList()) : modInfo;
    }

}

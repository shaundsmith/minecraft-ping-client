package dev.shaundsmith.minecraft.ping.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

/**
 * Details about all players currently playing on a Minecraft server.
 *
 * @author Shaun Smith
 * @since 1.0.0
 */
@Value
public class PlayersInfo {

    /** Maximum number of players supported by the server. */
    @JsonProperty("max")
    @NonNull private final int maxPlayers;

    /** Current number of online players on the server. */
    @NonNull private int online;

    /** List of all online players on the server. */
    @JsonProperty("sample") private List<PlayerInfo> playersOnline;


}

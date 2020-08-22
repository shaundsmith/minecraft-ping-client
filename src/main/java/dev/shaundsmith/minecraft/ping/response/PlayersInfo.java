package dev.shaundsmith.minecraft.ping.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Collections;
import java.util.List;

/**
 * Details about all players currently playing on a Minecraft server.
 *
 * @author Shaun Smith
 * @since 1.0.0
 */
@Value
@AllArgsConstructor(onConstructor_ = @JsonCreator)
public class PlayersInfo {

    /** Maximum number of players supported by the server. */
    @JsonProperty("max") int maxPlayers;

    /** Current number of online players on the server. */
    @JsonProperty("online") int online;

    /** Subset of online players on the server. */
    @JsonProperty("sample") List<PlayerInfo> playersOnline;

    /**
     * Returns a subset of the online players on the server.
     *
     * @return a subset of the online players on the server, or an empty list if no players are online
     */
    public List<PlayerInfo> getPlayersOnline() {
        return playersOnline == null ? Collections.emptyList() : playersOnline;
    }

}

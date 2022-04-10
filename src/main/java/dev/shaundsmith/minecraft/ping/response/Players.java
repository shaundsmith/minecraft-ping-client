package dev.shaundsmith.minecraft.ping.response;

import lombok.Value;

import java.util.List;

/**
 * Details about the players that are currently using a Minecraft Server.
 *
 * @author Shaun Smith
 * @since 1.0.0
 */
@Value
public class Players {

    /** The maximum number of players allowed on the server. */
    int maximumNumberOfPlayers;

    /** The number of players currently online on the server. */
    int onlineNumberOfPlayers;

    /**
     * The names of the players that are on the server.
     *
     * <p>This may not include the complete list of players on larger servers.
     */
    List<String> playerNames;

}

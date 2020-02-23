package dev.shaundsmith.minecraft.ping.response;

import java.util.Collections;
import java.util.List;

import static dev.shaundsmith.minecraft.ping.response.PlayerInfoBuilder.aPlayerInfo;

public final class PlayersInfoBuilder {

    public static final int DEFAULT_MAX_PLAYERS = 20;
    public static final int DEFAULT_ONLINE = 4;
    public static final List<PlayerInfo> DEFAULT_PLAYERS_ONLINE = Collections.singletonList(aPlayerInfo().build());

    private int maxPlayers = DEFAULT_MAX_PLAYERS;
    private int online = DEFAULT_ONLINE;
    private List<PlayerInfo> playersOnline = DEFAULT_PLAYERS_ONLINE;

    private PlayersInfoBuilder() {
    }

    public static PlayersInfoBuilder aPlayersInfo() {
        return new PlayersInfoBuilder();
    }

    public PlayersInfoBuilder withMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
        return this;
    }

    public PlayersInfoBuilder withOnline(int online) {
        this.online = online;
        return this;
    }

    public PlayersInfoBuilder withPlayersOnline(List<PlayerInfo> playersOnline) {
        this.playersOnline = playersOnline;
        return this;
    }

    public PlayersInfo build() {
        return new PlayersInfo(maxPlayers, online, playersOnline);
    }
}

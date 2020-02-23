package dev.shaundsmith.minecraft.ping.response;

import static dev.shaundsmith.minecraft.ping.response.ChatBuilder.aChat;
import static dev.shaundsmith.minecraft.ping.response.ModInfoBuilder.aModInfo;
import static dev.shaundsmith.minecraft.ping.response.PlayersInfoBuilder.aPlayersInfo;
import static dev.shaundsmith.minecraft.ping.response.VersionInfoBuilder.aVersionInfo;

public final class MinecraftStatusBuilder {

    public static final Chat DEFAULT_CHAT = aChat().build();
    public static final PlayersInfo DEFAULT_PLAYERS = aPlayersInfo().build();
    public static final VersionInfo DEFAULT_VERSION = aVersionInfo().build();
    public static final String DEFAULT_FAVICON = "data/png;78-r8r8-f9sdfsd8f9";
    public static final ModInfo DEFAULT_MOD_INFO = aModInfo().build();

    private Chat description = DEFAULT_CHAT;
    private PlayersInfo players = DEFAULT_PLAYERS;
    private VersionInfo version = DEFAULT_VERSION;
    private String favicon = DEFAULT_FAVICON;
    private ModInfo modinfo = DEFAULT_MOD_INFO;

    private MinecraftStatusBuilder() {
    }

    public static MinecraftStatusBuilder aMinecraftStatus() {
        return new MinecraftStatusBuilder();
    }

    public MinecraftStatusBuilder withDescription(Chat description) {
        this.description = description;
        return this;
    }

    public MinecraftStatusBuilder withPlayers(PlayersInfo players) {
        this.players = players;
        return this;
    }

    public MinecraftStatusBuilder withVersion(VersionInfo version) {
        this.version = version;
        return this;
    }

    public MinecraftStatusBuilder withFavicon(String favicon) {
        this.favicon = favicon;
        return this;
    }

    public MinecraftStatusBuilder withModinfo(ModInfo modinfo) {
        this.modinfo = modinfo;
        return this;
    }

    public MinecraftStatus build() {
        return new MinecraftStatus(description, players, version, favicon, modinfo);
    }
}

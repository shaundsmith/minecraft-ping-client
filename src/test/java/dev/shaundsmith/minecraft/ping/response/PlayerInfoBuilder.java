package dev.shaundsmith.minecraft.ping.response;

public final class PlayerInfoBuilder {

    public static final String DEFAULT_NAME = "Creeper";

    private String name = DEFAULT_NAME;

    private PlayerInfoBuilder() {
    }

    public static PlayerInfoBuilder aPlayerInfo() {
        return new PlayerInfoBuilder();
    }

    public PlayerInfoBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public PlayerInfo build() {
        return new PlayerInfo(name);
    }
}

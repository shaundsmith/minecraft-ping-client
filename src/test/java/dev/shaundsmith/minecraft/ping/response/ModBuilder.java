package dev.shaundsmith.minecraft.ping.response;

public final class ModBuilder {

    public static final String DEFAULT_NAME = "the.best-mod";
    public static final String DEFAULT_VERSION = "0.0.1a";

    private String name = DEFAULT_NAME;
    private String version = DEFAULT_VERSION;

    private ModBuilder() {
    }

    public static ModBuilder aMod() {
        return new ModBuilder();
    }

    public ModBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ModBuilder withVersion(String version) {
        this.version = version;
        return this;
    }

    public Mod build() {
        return new Mod(name, version);
    }
}

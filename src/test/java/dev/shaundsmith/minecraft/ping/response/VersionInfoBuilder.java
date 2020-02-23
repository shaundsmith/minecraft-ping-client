package dev.shaundsmith.minecraft.ping.response;

public final class VersionInfoBuilder {

    public static final String DEFAULT_VERSION = "1.7.10";

    private String version = DEFAULT_VERSION;

    private VersionInfoBuilder() {
    }

    public static VersionInfoBuilder aVersionInfo() {
        return new VersionInfoBuilder();
    }

    public VersionInfoBuilder withVersion(String version) {
        this.version = version;
        return this;
    }

    public VersionInfo build() {
        return new VersionInfo(version);
    }
}

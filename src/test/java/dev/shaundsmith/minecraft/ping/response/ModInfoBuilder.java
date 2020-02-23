package dev.shaundsmith.minecraft.ping.response;

import java.util.Collections;
import java.util.List;

import static dev.shaundsmith.minecraft.ping.response.ModBuilder.aMod;

public final class ModInfoBuilder {

    public static final String DEFAULT_MOD_LOADER = "mods";
    public static final List<Mod> DEFAULT_MOD_LIST = Collections.singletonList(aMod().build());

    private String modLoader = DEFAULT_MOD_LOADER;
    private List<Mod> modList = DEFAULT_MOD_LIST;

    private ModInfoBuilder() {
    }

    public static ModInfoBuilder aModInfo() {
        return new ModInfoBuilder();
    }

    public ModInfoBuilder withModLoader(String modLoader) {
        this.modLoader = modLoader;
        return this;
    }

    public ModInfoBuilder withModList(List<Mod> modList) {
        this.modList = modList;
        return this;
    }

    public ModInfo build() {
        return new ModInfo(modLoader, modList);
    }
}

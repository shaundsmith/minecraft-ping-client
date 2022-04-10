package dev.shaundsmith.minecraft.ping.client.deserializer;

import com.fasterxml.jackson.databind.JsonNode;
import dev.shaundsmith.minecraft.ping.response.Mod;
import dev.shaundsmith.minecraft.ping.response.ModLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * JSON reader which reads a mod loader for a Minecraft Forge mod loader.
 *
 * @author Shaun Smith
 * @since 1.0.0
 */
class ForgeModLoaderJsonReader implements JsonReader<ModLoader> {

    private static final String FORGE_MOD_LOADER_TYPE = "forge";

    @Override
    public boolean supports(JsonNode rootNode) {

        JsonNode modLoaderTypeNode = JsonNestedNodeReader.getByPath(rootNode, "modinfo", "type");

        return modLoaderTypeNode != null && FORGE_MOD_LOADER_TYPE.equals(modLoaderTypeNode.textValue());
    }

    @Override
    public ModLoader read(JsonNode rootNode) {

        List<Mod> mods = new ArrayList<>();
        JsonNode modInfoNode = JsonNestedNodeReader.getByPath(rootNode, "modinfo", "modList");

        if (modInfoNode != null && modInfoNode.isArray()) {
            for (JsonNode modInfoElement : modInfoNode) {
                mods.add(new Mod(
                        JsonNestedNodeReader.getByPath(modInfoElement, "modid").textValue(),
                        JsonNestedNodeReader.getByPath(modInfoElement, "version").textValue()));
            }
        }

        return new ModLoader(FORGE_MOD_LOADER_TYPE, mods);
    }

}

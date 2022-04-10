package dev.shaundsmith.minecraft.ping.client.deserializer;

import com.fasterxml.jackson.databind.JsonNode;
import dev.shaundsmith.minecraft.ping.response.ModLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JSON reader that reads the mod loader details from the Minecraft server response.
 *
 * @author Shaun Smith
 * @since 1.0.0
 */
class AggregateModLoaderJsonReader implements JsonReader<ModLoader> {

    private final List<JsonReader<ModLoader>> modLoaderJsonReaders = new ArrayList<>();

    AggregateModLoaderJsonReader() {

        modLoaderJsonReaders.add(new ForgeModLoaderJsonReader());
    }

    @Override
    public boolean supports(JsonNode jsonNode) {

        return modLoaderJsonReaders.stream().anyMatch(jsonReader -> jsonReader.supports(jsonNode));
    }

    @Override
    public ModLoader read(JsonNode jsonNode) {

        Optional<JsonReader<ModLoader>> supportedJsonReader = modLoaderJsonReaders.stream()
                .filter(modLoaderJsonReader -> modLoaderJsonReader.supports(jsonNode))
                .findFirst();

        return supportedJsonReader
                .map(modLoaderJsonReader -> modLoaderJsonReader.read(jsonNode))
                .orElse(null);
    }

}

package dev.shaundsmith.minecraft.ping.client.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import dev.shaundsmith.minecraft.ping.response.MinecraftServerResponse;
import dev.shaundsmith.minecraft.ping.response.ModLoader;
import dev.shaundsmith.minecraft.ping.response.Players;

import java.io.IOException;
import java.util.Collections;

/**
 * Deserializer for deserializing a JSON object into a Minecraft server response.
 *
 * @author Shaun Smith
 * @since 1.0.0
 */
public class MinecraftServerResponseDeserializer extends JsonDeserializer<MinecraftServerResponse> {

    private final JsonReader<Players> playersJsonReader = new PlayersJsonReader();
    private final JsonReader<ModLoader> modLoaderJsonReader = new AggregateModLoaderJsonReader();

    @Override
    public MinecraftServerResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode rootNode = jsonParser.readValueAsTree();

        Players players = playersJsonReader.supports(rootNode)
                ? playersJsonReader.read(rootNode)
                : new Players(0, 0, Collections.emptyList());

        ModLoader modLoader = modLoaderJsonReader.supports(rootNode)
                ? modLoaderJsonReader.read(rootNode)
                : null;

        return MinecraftServerResponse.builder()
                .serverDescription(deserializeServerDescription(rootNode))
                .serverVersion(deserializeServerVersion(rootNode))
                .serverIcon(deserializeServerIcon(rootNode))
                .players(players)
                .modLoader(modLoader)
                .build();
    }

    private String deserializeServerDescription(JsonNode rootNode) {

        return JsonNestedNodeReader.getByPath(rootNode, "description", "text").textValue();
    }

    private String deserializeServerVersion(JsonNode rootNode) {

        return JsonNestedNodeReader.getByPath(rootNode, "version", "name").textValue();
    }

    private String deserializeServerIcon(JsonNode rootNode) {

        return JsonNestedNodeReader.getByPath(rootNode, "favicon").textValue();
    }

}

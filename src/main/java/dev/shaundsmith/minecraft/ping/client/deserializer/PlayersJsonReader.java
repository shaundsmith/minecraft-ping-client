package dev.shaundsmith.minecraft.ping.client.deserializer;

import com.fasterxml.jackson.databind.JsonNode;
import dev.shaundsmith.minecraft.ping.response.Players;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * JSON reader for reading the player information from the Minecraft server response.
 *
 * @author Shaun Smith
 * @since 1.0.0
 */
class PlayersJsonReader implements JsonReader<Players> {

    private static final String PLAYERS_NODE_NAME = "players";

    @Override
    public boolean supports(JsonNode jsonNode) {

        return JsonNestedNodeReader.getByPath(jsonNode, PLAYERS_NODE_NAME) != null;
    }

    @Override
    public Players read(JsonNode jsonNode) {

        if (!jsonNode.has(PLAYERS_NODE_NAME)) {
            return new Players(0, 0, Collections.emptyList());
        }

        JsonNode playersNode = jsonNode.get(PLAYERS_NODE_NAME);
        int maxPlayerCount = JsonNestedNodeReader.getByPath(playersNode,"max").asInt();
        int onlinePlayerCount = JsonNestedNodeReader.getByPath(playersNode, "online").asInt();

        return new Players(maxPlayerCount, onlinePlayerCount, readPlayerNames(playersNode));
    }

    private List<String> readPlayerNames(JsonNode playerNode) {

        List<String> playerNames = new ArrayList<>();
        JsonNode playersSampleNode = JsonNestedNodeReader.getByPath(playerNode, "sample");
        if (playersSampleNode != null && playersSampleNode.isArray()) {
            for (JsonNode jsonNode : playersSampleNode) {
                playerNames.add(JsonNestedNodeReader.getByPath(jsonNode, "name").textValue());
            }
        }

        return playerNames;
    }
}

package dev.shaundsmith.minecraft.ping.client.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.shaundsmith.minecraft.ping.response.MinecraftServerResponse;
import dev.shaundsmith.minecraft.ping.response.Mod;
import dev.shaundsmith.minecraft.ping.response.ModLoader;
import dev.shaundsmith.minecraft.ping.response.Players;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class MinecraftServerResponseDeserializerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    void deserializes_a_minecraft_server_response() throws Exception {

        //language=json
        String json = "{" +
                "\"description\": { \"text\": \"My minecraft server\" }," +
                "\"version\": { \"name\": \"1.12.2\" }," +
                "\"players\": {" +
                "    \"max\": 10," +
                "    \"online\": 1," +
                "    \"sample\": [{ \"name\": \"Shaun\" }]" +
                "}," +
                "\"favicon\": \"data:jpg:lots+of+data\"" +
                "}";

        MinecraftServerResponse response = OBJECT_MAPPER.readValue(json, MinecraftServerResponse.class);

        assertThat(response).isEqualTo(MinecraftServerResponse.builder()
                .serverDescription("My minecraft server")
                .serverIcon("data:jpg:lots+of+data")
                .serverVersion("1.12.2")
                .players(new Players(10, 1, Collections.singletonList("Shaun")))
                .modLoader(null)
                .build());
    }

    @Test
    void deserializes_a_minecraft_server_response_with_a_forge_mod_loader() throws Exception {
        //language=json
        String json = "{" +
                "\"description\": { \"text\": \"My minecraft server\" }," +
                "\"version\": { \"name\": \"1.12.2\" }," +
                "\"players\": {" +
                "    \"max\": 10," +
                "    \"online\": 1," +
                "    \"sample\": [{ \"name\": \"Shaun\" }]" +
                "}," +
                "\"modinfo\": {" +
                "    \"type\": \"forge\"," +
                "    \"modList\": [{ \"modid\": \"my-mod\", \"version\": \"1.1a\" }]" +
                "}," +
                "\"favicon\": \"data:jpg:lots+of+data\"" +
                "}";

        MinecraftServerResponse response = OBJECT_MAPPER.readValue(json, MinecraftServerResponse.class);

        assertThat(response).isEqualTo(MinecraftServerResponse.builder()
                .serverDescription("My minecraft server")
                .serverIcon("data:jpg:lots+of+data")
                .serverVersion("1.12.2")
                .players(new Players(10, 1, Collections.singletonList("Shaun")))
                .modLoader(new ModLoader("forge", Collections.singletonList(new Mod("my-mod", "1.1a"))))
                .build());
    }

    @Test
    void deserializes_a_minecraft_server_response_without_player_information() throws Exception {
        //language=json
        String json = "{" +
                "\"description\": { \"text\": \"My minecraft server\" }," +
                "\"version\": { \"name\": \"1.12.2\" }," +
                "\"favicon\": \"data:jpg:lots+of+data\"" +
                "}";

        MinecraftServerResponse response = OBJECT_MAPPER.readValue(json, MinecraftServerResponse.class);

        assertThat(response).isEqualTo(MinecraftServerResponse.builder()
                .serverDescription("My minecraft server")
                .serverIcon("data:jpg:lots+of+data")
                .serverVersion("1.12.2")
                .players(new Players(0, 0, Collections.emptyList()))
                .modLoader(null)
                .build());
    }

    @Test
    void deserializes_an_empty_minecraft_server_response() throws Exception {
        //language=json
        String json = "{}";

        MinecraftServerResponse response = OBJECT_MAPPER.readValue(json, MinecraftServerResponse.class);

        assertThat(response).isEqualTo(MinecraftServerResponse.builder()
                .serverDescription(null)
                .serverIcon(null)
                .serverVersion(null)
                .players(new Players(0, 0, Collections.emptyList()))
                .modLoader(null)
                .build());
    }

}
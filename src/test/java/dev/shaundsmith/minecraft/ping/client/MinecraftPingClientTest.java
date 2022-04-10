package dev.shaundsmith.minecraft.ping.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.shaundsmith.minecraft.ping.exception.MinecraftClientException;
import dev.shaundsmith.minecraft.ping.response.MinecraftServerResponse;
import dev.shaundsmith.minecraft.ping.response.Mod;
import dev.shaundsmith.minecraft.ping.response.ModLoader;
import dev.shaundsmith.minecraft.ping.response.Players;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class MinecraftPingClientTest {

    private static final InetSocketAddress ANY_SERVER_ADDRESS = new InetSocketAddress("http://example.com", 2389);

    @Mock
    private MinecraftOutputStream mockedOutputStream;
    @Mock
    private MinecraftInputStream mockedInputStream;
    @Mock
    private MinecraftSocket mockedSocket;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private MinecraftPingClient theClient;

    @BeforeEach
    void beforeEach() throws Exception {
        theClient = new MinecraftPingClient(ANY_SERVER_ADDRESS, address -> mockedSocket, objectMapper);
        given(mockedSocket.getInputStream()).willReturn(mockedInputStream);
        given(mockedSocket.getOutputStream()).willReturn(mockedOutputStream);
    }

    @Test
    void creates_a_socket_for_the_server_address() throws Exception {

        SocketFactory mockedSocketFactory = mock(SocketFactory.class);
        InetSocketAddress theServerAddress = ANY_SERVER_ADDRESS;
        givenTheResponsePacketIsReturned();
        givenAResponseBodyIsReturned();
        given(mockedSocketFactory.createSocket(any())).willReturn(mockedSocket);
        theClient = new MinecraftPingClient(theServerAddress, mockedSocketFactory, objectMapper);

        theClient.ping();

        then(mockedSocketFactory).should().createSocket(theServerAddress);
    }

    @Test
    void performs_a_handshake_with_the_server() throws Exception {

        givenTheResponsePacketIsReturned();
        givenAResponseBodyIsReturned();

        theClient.ping();

        then(mockedOutputStream).should()
                .writePacket(new Handshake(0x00, ANY_SERVER_ADDRESS).toHandshakePacket());
    }

    @Test
    void sends_the_0x00_request_packet() throws Exception {

        givenTheResponsePacketIsReturned();
        givenAResponseBodyIsReturned();

        theClient.ping();

        then(mockedOutputStream).should().writePacket(new byte[]{0x00});
    }

    @Test
    void parses_the_json_response_returned_from_the_server() throws Exception {

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
        givenTheResponsePacketIsReturned();
        given(mockedInputStream.readString()).willReturn(json);

        MinecraftServerResponse response = theClient.ping();

        assertThat(response).isEqualTo(MinecraftServerResponse.builder()
                .serverDescription("My minecraft server")
                .serverIcon("data:jpg:lots+of+data")
                .serverVersion("1.12.2")
                .players(new Players(10, 1, Collections.singletonList("Shaun")))
                .modLoader(new ModLoader("forge", Collections.singletonList(new Mod("my-mod", "1.1a"))))
                .build());
    }

    @Test
    void require_the_response_packet_id_to_be_0x00() throws Exception {

        given(mockedInputStream.readVarInt()).willReturn(VarInt.of(0x01));

        Throwable theException = catchThrowable(() -> theClient.ping());

        assertThat(theException)
                .isInstanceOf(MinecraftClientException.class)
                .hasMessageContaining("should have an ID of 0x00.");
    }

    @Test
    void request_the_response_data_to_be_valid_json() throws Exception {

        String json = "{some-json: 38928392}";
        givenTheResponsePacketIsReturned();
        given(mockedInputStream.readString()).willReturn(json);

        Throwable theException = catchThrowable(() -> theClient.ping());

        assertThat(theException)
                .isInstanceOf(MinecraftClientException.class)
                .hasMessageContaining("Invalid response");

    }

    @Test
    void requires_the_request_to_be_successful() throws Exception {

        given(mockedInputStream.readVarInt()).willThrow(new IOException(""));

        Throwable theException = catchThrowable(() -> theClient.ping());

        assertThat(theException)
                .isInstanceOf(MinecraftClientException.class)
                .hasMessageContaining("An error occurred");

    }

    private void givenTheResponsePacketIsReturned() throws Exception {

        given(mockedInputStream.readVarInt()).willReturn(VarInt.of(0x00));
    }

    private void givenAResponseBodyIsReturned() throws Exception {

        given(mockedInputStream.readString()).willReturn("{}");
    }

}
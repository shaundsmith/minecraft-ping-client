package dev.shaundsmith.minecraft.ping.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.shaundsmith.minecraft.ping.exception.MinecraftClientException;
import dev.shaundsmith.minecraft.ping.response.MinecraftStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.InetSocketAddress;

import static dev.shaundsmith.minecraft.ping.response.MinecraftStatusBuilder.aMinecraftStatus;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class MinecraftPingClientTest {

    private static final InetSocketAddress ANY_SERVER_ADDRESS = new InetSocketAddress("http://example.com", 2389);
    private static final byte[] ANY_HANDSHAKE = new byte[]{12, -4, 80, 127, 38};

    @Mock private MinecraftOutputStream mockedOutputStream;
    @Mock private MinecraftInputStream mockedInputStream;
    @Mock private MinecraftSocket mockedSocket;
    @Mock private HandshakeFactory mockedHandshakeFactory;
    private ObjectMapper objectMapper = new ObjectMapper();

    private MinecraftPingClient theClient;

    @BeforeEach
    void beforeEach() throws Exception {
        theClient = new MinecraftPingClient(ANY_SERVER_ADDRESS, address -> mockedSocket, mockedHandshakeFactory, objectMapper);
        given(mockedSocket.getInputStream()).willReturn(mockedInputStream);
        given(mockedSocket.getOutputStream()).willReturn(mockedOutputStream);
    }

    @Test
    void shouldCreateASocketForTheServerAddress_WhenRetrievingTheStatus() throws Exception {
        SocketFactory mockedSocketFactory = mock(SocketFactory.class);
        InetSocketAddress theServerAddress = ANY_SERVER_ADDRESS;
        givenTheResponsePacketIsReturned();
        givenAResponseBodyIsReturned();
        given(mockedSocketFactory.createSocket(any())).willReturn(mockedSocket);
        theClient = new MinecraftPingClient(theServerAddress, mockedSocketFactory, mockedHandshakeFactory, objectMapper);

        theClient.getStatus();

        then(mockedSocketFactory).should().createSocket(theServerAddress);
    }

    @Test
    void shouldPerformAHandshake_WhenRetrievingTheStatus() throws Exception {
        byte[] theHandshake = ANY_HANDSHAKE;
        givenTheResponsePacketIsReturned();
        givenAResponseBodyIsReturned();
        given(mockedHandshakeFactory.create(ANY_SERVER_ADDRESS, VarInt.of(-1))).willReturn(theHandshake);

        theClient.getStatus();

        then(mockedOutputStream).should().writePacket(theHandshake);
    }

    @Test
    void shouldSendThe0x00RequestPacket_WhenRetrievingTheStatus() throws Exception {
        givenTheResponsePacketIsReturned();
        givenAResponseBodyIsReturned();

        theClient.getStatus();

        then(mockedOutputStream).should().writePacket(new byte[]{0x00});
    }

    @Test
    void shouldParseTheJsonResponse_WhenRetrievingTheStatus() throws Exception {
        MinecraftStatus expectedStatus = aMinecraftStatus().build();
        String json = objectMapper.writeValueAsString(expectedStatus);
        givenTheResponsePacketIsReturned();
        given(mockedInputStream.readString()).willReturn(json);

        MinecraftStatus returnedStatus = theClient.getStatus();

        assertThat(returnedStatus).isEqualTo(expectedStatus);
    }

    @Test
    void shouldThrowAnException_WhenTheResponsePacketIdIsNot0x00() throws Exception {
        given(mockedInputStream.readVarInt()).willReturn(VarInt.of(0x01));

        Throwable theException = catchThrowable(() -> theClient.getStatus());

        assertThat(theException)
                .isInstanceOf(MinecraftClientException.class)
                .hasMessageContaining("should have an ID of 0x00.");
    }

    @Test
    void shouldThrowAnException_WhenTheResponseJsonIsInvalid() throws Exception {
        String json = "{some-json: 38928392}";
        givenTheResponsePacketIsReturned();
        given(mockedInputStream.readString()).willReturn(json);

        Throwable theException = catchThrowable(() -> theClient.getStatus());

        assertThat(theException)
                .isInstanceOf(MinecraftClientException.class)
                .hasMessageContaining("Invalid response");

    }

    @Test
    void shouldThrowAnException_WhenAnErrorOccursWhilstMakingTheRequest() throws Exception {
        given(mockedInputStream.readVarInt()).willThrow(new IOException(""));

        Throwable theException = catchThrowable(() -> theClient.getStatus());

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
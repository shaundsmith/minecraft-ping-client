package dev.shaundsmith.minecraft.ping.client;

import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;

import static dev.shaundsmith.minecraft.ping.client.PacketAssert.assertThat;

class HandshakeFactoryTest {

    private static final String ANY_HOST_NAME = "http://url.com";
    private static final InetSocketAddress ANY_SERVER_ADDRESS = new InetSocketAddress(ANY_HOST_NAME, 1234);
    private static final VarInt ANY_PROTOCOL_VERSION = VarInt.of(1);

    private static final byte[] PACKET_ID = new byte[]{0x00};

    private final HandshakeFactory theHandshakeFactory = new HandshakeFactory();

    @Test
    void shouldReturnAPacketIdOf0x00_WhenCreatingAHandshake() throws Exception {
        byte[] theHandshakePacket = theHandshakeFactory.create(ANY_SERVER_ADDRESS, ANY_PROTOCOL_VERSION);

        assertThat(theHandshakePacket)
                .containsSubArray(new byte[]{0x00}, 0);
    }

    @Test
    void shouldReturnAProtocolVersion_WhenCreatingAHandshake() throws Exception {
        VarInt protocolVersion = ANY_PROTOCOL_VERSION;
        byte[] protocolVersionBytes = ANY_PROTOCOL_VERSION.getValueAsBytes();

        byte[] theHandshakePacket = theHandshakeFactory.create(ANY_SERVER_ADDRESS, protocolVersion);

        assertThat(theHandshakePacket)
                .containsSubArray(protocolVersionBytes,
                        after(PACKET_ID));
    }

    @Test
    void shouldReturnTheServerAddress_PrefixedWithTheServerAddressLength_WhenCreatingAHandshake() throws Exception {
        String serverAddressString = "http://any-url.com";
        InetSocketAddress serverAddress = new InetSocketAddress(serverAddressString, 123);
        byte[] serverAddressLength = VarInt.of(serverAddressString.length()).getValueAsBytes();

        byte[] theHandshakePacket = theHandshakeFactory.create(serverAddress, ANY_PROTOCOL_VERSION);

        assertThat(theHandshakePacket)
                .containsSubArray(serverAddressLength,
                        after(PACKET_ID, ANY_PROTOCOL_VERSION))
                .containsSubArray(serverAddressString.getBytes(),
                        after(PACKET_ID, ANY_PROTOCOL_VERSION, serverAddressLength));
    }

    @Test
    void shouldReturnTheServerPortAsAnUnsignedShort_WhenCreatingAHandshake() throws Exception {
        int port = 100;
        InetSocketAddress serverAddress = new InetSocketAddress(ANY_HOST_NAME, port);

        byte[] theHandshakePacket = theHandshakeFactory.create(serverAddress, ANY_PROTOCOL_VERSION);

        assertThat(theHandshakePacket)
                .containsSubArray(asUnsignedShort(port),
                        after(PACKET_ID, ANY_PROTOCOL_VERSION, VarInt.of(ANY_HOST_NAME.length()), ANY_HOST_NAME));
    }

    @Test
    void shouldReturnANextStateOfOne_WhenCreatingAHandshake() throws Exception {
        byte[] theHandshakePacket = theHandshakeFactory.create(ANY_SERVER_ADDRESS, ANY_PROTOCOL_VERSION);

        assertThat(theHandshakePacket)
                .containsSubArray(VarInt.of(1).getValueAsBytes(),
                        after(PACKET_ID, ANY_PROTOCOL_VERSION, VarInt.of(ANY_HOST_NAME.length()), ANY_HOST_NAME, new byte[2]));
    }

    int after(Object... components) {
        int total = 0;
        for (Object component: components) {
            if (component instanceof VarInt) {
                total += ((VarInt) component).getValueAsBytes().length;
            } else if (component instanceof byte[]) {
                total += ((byte[]) component).length;
            } else if (component instanceof String) {
                total += ((String) component).getBytes().length;
            }
        }
        return total;
    }

    byte[] asUnsignedShort(int value) {
        return new byte[]{
                (byte) ((value >>> 8) & 0xFF),
                (byte) (value & 0xFF)
        };
    }

}
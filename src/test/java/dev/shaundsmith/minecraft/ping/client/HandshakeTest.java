package dev.shaundsmith.minecraft.ping.client;

import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;

import static dev.shaundsmith.minecraft.ping.client.PacketAssert.assertThat;

class HandshakeTest {

    @Test
    void returns_a_handshake_packet() throws Exception {

        Handshake sut = new Handshake(0x00, VarInt.of(5), new InetSocketAddress("http://minecraft.net", 1234));

        byte[] result = sut.toHandshakePacket();

        assertThat(result)
                .contains("Packet ID", new byte[]{0x00})
                .followedBy("Protocol Version", VarInt.of(5).getValueAsBytes())
                .followedBy("Host length", VarInt.of("http://minecraft.net".length()).getValueAsBytes())
                .followedBy("Host", "http://minecraft.net".getBytes())
                .followedBy("Host Port", asUnsignedShort(1234))
                .followedBy("Next State", VarInt.of(1).getValueAsBytes());
    }

    byte[] asUnsignedShort(int value) {
        return new byte[]{
                (byte) ((value >>> 8) & 0xFF),
                (byte) (value & 0xFF)
        };
    }

}
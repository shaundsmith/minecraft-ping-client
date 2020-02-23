package dev.shaundsmith.minecraft.ping.client;

import org.assertj.core.api.AbstractByteArrayAssert;
import org.assertj.core.api.Assertions;

// Custom assertj assertion for asserting packets (byte arrays) using natural language
class PacketAssert extends AbstractByteArrayAssert<PacketAssert> {

    PacketAssert(byte[] actual) {
        super(actual, PacketAssert.class);
    }

    static PacketAssert assertThat(byte[] actual) {
        return new PacketAssert(actual);
    }

    PacketAssert containsSubArray(byte[] bytes, int startPosition) {
        assertThat(bytes).isNotEmpty();
        for (int i = 0; i < bytes.length; i++) {
            Assertions.assertThat(actual[startPosition + i]).isEqualTo(bytes[i]);
        }

        return this;
    }

}

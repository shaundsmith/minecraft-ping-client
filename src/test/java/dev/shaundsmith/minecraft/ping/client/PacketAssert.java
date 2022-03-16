package dev.shaundsmith.minecraft.ping.client;

import org.assertj.core.api.AbstractIteratorAssert;
import org.assertj.core.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

// Custom assertj assertion for asserting packets (byte arrays) using natural language
class PacketAssert extends AbstractIteratorAssert<PacketAssert, Byte> {

    private PacketAssert(Iterator<Byte> actual) {
        super(actual, PacketAssert.class);
    }

    static PacketAssert assertThat(byte[] actual) {

        List<Byte> actualBytes = new ArrayList<>();
        for (byte b : actual) {
            actualBytes.add(b);
        }

        return new PacketAssert(actualBytes.listIterator());
    }

    PacketAssert contains(String label, byte[] bytes) {

        byte[] matchingBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            hasNext();
            matchingBytes[i] = actual.next();
        }

        Assertions.assertThat(matchingBytes)
                .withFailMessage("Expected %s to have value %s.\n Actual value %s", label,
                        Arrays.toString(matchingBytes),
                        Arrays.toString(bytes))
                .isEqualTo(bytes);

        return this;
    }

    PacketAssert followedBy(String label, byte[] bytes) {

        return contains(label, bytes);
    }

}

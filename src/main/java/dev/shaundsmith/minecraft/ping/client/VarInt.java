package dev.shaundsmith.minecraft.ping.client;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Variable-length integer value designed to use a fewer bytes for smaller values.
 *
 * <p>Based on the description provided at <a href="https://wiki.vg/Data_types#VarInt_and_VarLong">https://wiki.vg/Data_types#VarInt_and_VarLong</a>.
 *
 * @author Shaun Smith
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class VarInt {

    private final int value;

    /**
     * Constructs a varint from the provided integer.
     *
     * @param value integer value to create a varint for
     *
     * @return varint of the provided value
     */
    static VarInt of(int value) {
        return new VarInt(value);
    }

    /**
     * Returns the value of the varint as an array of bytes.
     *
     * @return value of the varint as an array of bytes
     */
    byte[] getValueAsBytes() {
        List<Byte> bytes = new ArrayList<>();
        int processedValue = value;
        /*
           See https://developers.google.com/protocol-buffers/docs/encoding#varints
           "Each byte in a varint, except the last byte, has the most significant bit (msb) set – this indicates that there are further bytes to come.
            The lower 7 bits of each byte are used to store the two's complement representation of the number in groups of 7 bits,
            least significant group first."
         */
        do {
            byte temporaryValue = (byte) (processedValue & 0b01111111);
            processedValue >>>= 7;
            if (processedValue != 0) {
                temporaryValue |= 0b10000000;
            }
            bytes.add(temporaryValue);
        } while (processedValue != 0);

        return toArray(bytes);
    }

    /**
     * Returns the value of the varint as an integer.
     *
     * @return value of the varint as an integer
     */
    int getValueAsInt() {
        return value;
    }

    private byte[] toArray(List<Byte> bytes) {
        byte[] bytesArray = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            bytesArray[i] = bytes.get(i);
        }
        return bytesArray;
    }

}

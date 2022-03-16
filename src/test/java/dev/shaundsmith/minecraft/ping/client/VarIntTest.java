package dev.shaundsmith.minecraft.ping.client;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class VarIntTest {

    private static final int ANY_LARGE_VALUE = 191954546;

    @ParameterizedTest
    @MethodSource("varIntBytes")
    void returns_a_byte_array(int value, byte[] expectedBytes) {

        VarInt varInt = VarInt.of(value);

        byte[] bytes = varInt.getValueAsBytes();

        assertThat(bytes).contains(expectedBytes);
    }

    static Object[] varIntBytes() {
        return new Object[]{
                // 0 = 0
                new Object[]{0, new byte[]{0b0}},
                // 127 = 127
                new Object[]{127, new byte[]{0b1111111}},
                // 128 = 127 (twos-compliment) + 1
                new Object[]{128, new byte[]{-0b10000000, 0b1}},
                // 256 = 127 + 127 + 2
                new Object[]{256, new byte[]{-0b10000000, -0b10000000, 0b10}},
                // 512 = 127 + 127 + 127 + 127 + 4
                new Object[]{512, new byte[]{-0b10000000, -0b10000000, -0b10000000, -0b10000000, 0b100}},
        };
    }

    @Test
    void all_bytes_except_the_last_should_be_prefixed_with_one() {

        VarInt varInt = VarInt.of(ANY_LARGE_VALUE);

        byte[] bytes = varInt.getValueAsBytes();

        assertThat(bytes).isNotEmpty();
        for (int i = 0; i < bytes.length - 1; i++) {
            assertThat(asBinary(bytes[i])).startsWith("1");
        }
    }

    @Test
    void the_last_byte_should_be_prefixed_with_zero() {

        VarInt varInt = VarInt.of(ANY_LARGE_VALUE);

        byte[] bytes = varInt.getValueAsBytes();

        byte theLastByte = bytes[bytes.length - 1];
        assertThat(asBinary(theLastByte)).startsWith("0");
    }

    @Test
    void returns_an_integer() {

        int intValue = 43874;
        VarInt varInt = VarInt.of(intValue);

        int returnedInt = varInt.getValueAsInt();

        assertThat(returnedInt).isEqualTo(intValue);
    }

    @Test
    void testEqualsAndHashCode() {
        EqualsVerifier.forClass(VarInt.class).verify();
    }
    
    private static String asBinary(byte b) {
        return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
    }


}
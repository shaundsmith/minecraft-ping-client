package dev.shaundsmith.minecraft.ping.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class MinecraftInputStreamTest {

    @ParameterizedTest
    @MethodSource("varInts")
    void reads_a_var_int_from_the_stream(VarInt varInt) throws Exception {

        MinecraftInputStream inputStream = new MinecraftInputStream(streamOf(varInt.getValueAsBytes()));

        VarInt readVarInt = inputStream.readVarInt();

        assertThat(readVarInt).isEqualTo(varInt);
    }

    static Stream<VarInt> varInts() {

        return Stream.of(
                VarInt.of(120),
                VarInt.of(700),
                VarInt.of(1),
                VarInt.of(128)
        );
    }

    @Test
    void var_int_must_not_exceed_five_bytes() {

        MinecraftInputStream inputStream = new MinecraftInputStream(streamOf(new byte[]{
                -0b10000000, -0b10000000, -0b10000000, -0b10000000, -0b10000000, -0b10000000
        }));

        Throwable theException = catchThrowable(inputStream::readVarInt);

        assertThat(theException)
                .isInstanceOf(IOException.class)
                .hasMessageContaining("VarInt exceeds 5 bytes");
    }

    @Test
    void reads_a_string_from_the_stream() throws Exception {

        String theString = "a string of words";
        MinecraftInputStream inputStream = new MinecraftInputStream(
                streamOf(VarInt.of(theString.length()).getValueAsBytes(), theString.getBytes())
        );

        String readString = inputStream.readString();

        assertThat(readString).isEqualTo(theString);
    }

    @Test
    void closes_the_stream() throws Exception {

        InputStream mockedInputStream = mock(InputStream.class);
        MinecraftInputStream inputStream = new MinecraftInputStream(mockedInputStream);

        inputStream.close();

        then(mockedInputStream).should().close();
    }

    private static ByteArrayInputStream streamOf(byte[]... elements) {

        byte[] streamContents = merge(elements);
        return new ByteArrayInputStream(streamContents);
    }


    private static byte[] merge(byte[]... elements) {

        byte[] merged = new byte[getSizeTotalSize(elements)];
        int currentPosition = 0;
        for (byte[] element: elements) {
            for (byte elementByte: element) {
                merged[currentPosition] = elementByte;
                currentPosition++;
            }
        }
        return merged;
    }

    private static int getSizeTotalSize(byte[]... elements) {

        int totalSize = 0;
        for (byte[] element: elements) {
            totalSize += element.length;
        }
        return totalSize;
    }

}
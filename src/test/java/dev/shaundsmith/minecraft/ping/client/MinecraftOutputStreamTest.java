package dev.shaundsmith.minecraft.ping.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.DataOutputStream;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.inOrder;

@ExtendWith(MockitoExtension.class)
class MinecraftOutputStreamTest {

    @Mock private DataOutputStream mockedOutputStream;
    private MinecraftOutputStream outputStream;

    @BeforeEach
    void beforeEach() {
        outputStream = new MinecraftOutputStream(mockedOutputStream);
    }

    @Test
    void writes_a_packet_to_the_output_stream() throws Exception {

        byte[] packet = new byte[]{123, 64, 18, -15};

        outputStream.writePacket(packet);

        then(mockedOutputStream).should().write(packet);
    }

    @Test
    void prepends_a_packet_with_its_size_when_writing_to_the_output_stream() throws Exception {

        byte[] packet = new byte[]{123, 64, 18, -15};

        outputStream.writePacket(packet);

        InOrder theOrder = inOrder(mockedOutputStream);
        theOrder.verify(mockedOutputStream).write(VarInt.of(packet.length).getValueAsBytes());
        theOrder.verify(mockedOutputStream).write(packet);
    }

    @Test
    void writes_a_string_to_the_output_stream() throws Exception {

        String string = "a string";

        outputStream.writeString(string);

        then(mockedOutputStream).should().writeBytes(string);
    }

    @Test
    void prepends_a_string_with_its_length_when_writing_to_the_output_stream() throws Exception {

        String string = "a string";

        outputStream.writeString(string);

        InOrder theOrder = inOrder(mockedOutputStream);
        theOrder.verify(mockedOutputStream).write(VarInt.of(string.length()).getValueAsBytes());
        theOrder.verify(mockedOutputStream).writeBytes(string);
    }

    @Test
    void writes_a_short_to_the_output_stream() throws Exception {

        int aShort = 47378;

        outputStream.writeShort(aShort);

        then(mockedOutputStream).should().writeShort(aShort);
    }

    @Test
    void writes_a_byte_to_the_output_stream() throws Exception {

        int aSingleByte = 4546;

        outputStream.write(aSingleByte);

        then(mockedOutputStream).should().writeByte(aSingleByte);
    }

    @Test
    void writes_a_var_int_to_the_output_stream() throws Exception {

        VarInt varInt = VarInt.of(348);

        outputStream.writeVarInt(varInt);

        then(mockedOutputStream).should().write(varInt.getValueAsBytes());
    }

    @Test
    void closes_the_stream() throws Exception {

        outputStream.close();

        then(mockedOutputStream).should().close();
    }

}
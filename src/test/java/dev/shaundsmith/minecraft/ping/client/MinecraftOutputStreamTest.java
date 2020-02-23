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
    void shouldWriteAPacketToTheOutputStream() throws Exception {
        byte[] packet = new byte[]{123, 64, 18, -15};

        outputStream.writePacket(packet);

        then(mockedOutputStream).should().write(packet);
    }

    @Test
    void shouldPrependAPacketWithItsSize_WhenWritingAPacketToTheOutputStream() throws Exception {
        byte[] packet = new byte[]{123, 64, 18, -15};

        outputStream.writePacket(packet);

        InOrder theOrder = inOrder(mockedOutputStream);
        theOrder.verify(mockedOutputStream).write(VarInt.of(packet.length).getValueAsBytes());
        theOrder.verify(mockedOutputStream).write(packet);
    }

    @Test
    void shouldWriteAStringAsBytesToTheOutputStream() throws Exception {
        String string = "a string";

        outputStream.writeString(string);

        then(mockedOutputStream).should().writeBytes(string);
    }

    @Test
    void shouldPrependAStringWithItsLength_WhenWritingAStringToTheOutputStream() throws Exception {
        String string = "a string";

        outputStream.writeString(string);

        InOrder theOrder = inOrder(mockedOutputStream);
        theOrder.verify(mockedOutputStream).write(VarInt.of(string.length()).getValueAsBytes());
        theOrder.verify(mockedOutputStream).writeBytes(string);
    }

    @Test
    void shouldWriteAShortToTheOutputStream() throws Exception {
        int aShort = 47378;

        outputStream.writeShort(aShort);

        then(mockedOutputStream).should().writeShort(aShort);
    }

    @Test
    void shouldWriteAByteToTheOutputStream() throws Exception {
        int aSingleByte = 4546;

        outputStream.write(aSingleByte);

        then(mockedOutputStream).should().writeByte(aSingleByte);
    }

    @Test
    void shouldWriteAVarIntAsBytesToTheOutputStream() throws Exception {
        VarInt varInt = VarInt.of(348);

        outputStream.writeVarInt(varInt);

        then(mockedOutputStream).should().write(varInt.getValueAsBytes());
    }

    @Test
    void shouldCloseTheStream() throws Exception {
        outputStream.close();

        then(mockedOutputStream).should().close();
    }

}
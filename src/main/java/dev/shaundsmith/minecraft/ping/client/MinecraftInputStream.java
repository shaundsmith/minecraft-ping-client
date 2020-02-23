package dev.shaundsmith.minecraft.ping.client;

import lombok.NonNull;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Input stream wrapper for Minecraft-specific socket interactions.
 *
 * @author Shaun Smith
 * @since 1.0.0
 */
final class MinecraftInputStream implements Closeable {

    private final DataInputStream dataInputStream;

    /**
     * Constructs a new Minecraft input stream from the provided input stream.
     *
     * @param inputStream input stream to wrap as a Minecraft input stream
     */
    MinecraftInputStream(@NonNull InputStream inputStream) {
        this.dataInputStream = new DataInputStream(inputStream);
    }

    @Override
    public void close() throws IOException {
        dataInputStream.close();
    }

    /**
     * Read a varint from the input stream.
     *
     * @return the varint from the input stream
     *
     * @throws IOException if an error occurs reading the stream, or the varint is incorrectly formatted
     */
    VarInt readVarInt() throws IOException {
        int numberRead = 0;
        int result = 0;
        byte currentByte;
        do {
            currentByte = dataInputStream.readByte();
            int value = (currentByte & 0b01111111);
            result |= (value << (7 * numberRead));

            numberRead++;
            if (numberRead > 5) {
                throw new IOException("VarInt exceeds 5 bytes. VarInt cannot exceed 5 bytes.");
            }
        } while ((currentByte & 0b10000000) != 0);

        return VarInt.of(result);
    }

    /**
     * Reads a string from the input stream.
     *
     * <p>Expects the string to be preceded by a varint specifying the string length.
     *
     * @return the string from the input stream
     *
     * @throws IOException if an error occurs reading the stream
     */
    String readString() throws IOException {
        VarInt length = readVarInt();
        byte[] bytes = new byte[length.getValueAsInt()];
        dataInputStream.readFully(bytes);
        return new String(bytes);
    }

}

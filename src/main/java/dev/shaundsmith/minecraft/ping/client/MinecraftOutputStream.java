package dev.shaundsmith.minecraft.ping.client;

import lombok.NonNull;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Output stream wrapper for Minecraft-specific socket interactions.
 *
 * @author Shaun Smith
 * @since 1.0.0
 */
final class MinecraftOutputStream implements Closeable {

    private final DataOutputStream dataOutputStream;

    /**
     * Constructs a new Minecraft output stream from the provided output stream.
     *
     * @param outputStream output stream to wrap as a Minecraft output stream
     */
    MinecraftOutputStream(@NonNull OutputStream outputStream) {
        this(new DataOutputStream(outputStream));
    }

    /**
     * Constructs a new Minecraft output stream from the provided data output stream.
     *
     * @param outputStream output stream to wrap as a Minecraft output stream
     */
    MinecraftOutputStream(@NonNull DataOutputStream outputStream) {
        this.dataOutputStream = outputStream;
    }

    @Override
    public void close() throws IOException {
        dataOutputStream.close();
    }

    /**
     * Write a packet of data to the stream.
     *
     * <p>Prepends the packet with a varint specifying the packet size.
     *
     * @param data the packet of data to write to the stream
     *
     * @throws IOException if an error occurs writing to the stream
     */
    void writePacket(byte[] data) throws IOException {
        writeVarInt(VarInt.of(data.length));
        write(data);
    }

    /**
     * Writes a single integer to the stream.
     *
     * @param data integer to write to the stream
     *
     * @throws IOException if an error occurs writing to the stream
     */
    void write(int data) throws IOException {
        dataOutputStream.writeByte(data);
    }

    /**
     * Writes a string to the stream.
     *
     * <p>Prepends the string with a varint specifying the string length.
     *
     * @param data string to write to the stream
     *
     * @throws IOException if an error occurs writing to the stream
     */
    void writeString(String data) throws IOException {
        writeVarInt(VarInt.of(data.length()));
        dataOutputStream.writeBytes(data);
    }

    /**
     * Writes a short to the stream.
     *
     * @param data integer representing a short
     *
     * @throws IOException if an error occurs writing to the stream
     */
    void writeShort(int data) throws IOException {
        dataOutputStream.writeShort(data);
    }

    /**
     * Writes a varint to the stream.
     *
     * @param data varint to write to the stream
     *
     * @throws IOException if an error occurs writing to the stream
     */
    void writeVarInt(VarInt data) throws IOException {
        dataOutputStream.write(data.getValueAsBytes());
    }

    private void write(byte[] data) throws IOException {
        dataOutputStream.write(data);
    }

}

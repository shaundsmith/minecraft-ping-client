package dev.shaundsmith.minecraft.ping.client;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Socket wrapper for performing socket-level interactions with a Minecraft server.
 *
 * @author Shaun Smith
 * @version 1.0.0
 */
class MinecraftSocket implements Closeable {

    private final Socket socket;

    /**
     * Constructs a {@code MinecraftSocket} for the given address.
     *
     * @param serverAddress address of the Minecraft server
     *
     * @throws IOException if an error occurs whilst constructing the socket
     */
    MinecraftSocket(InetSocketAddress serverAddress) throws IOException {
        this.socket = new Socket(serverAddress.getHostString(), serverAddress.getPort());
    }

    /**
     * Constructs a {@code MinecraftSocket} for the given address and with the given ticket period.
     *
     * @param serverAddress address of the Minecraft server
     * @param timeout the timeout of the socket (in milliseconds)
     *
     * @throws IOException if an error occurs whilst constructing the socket
     *
     * @implNote a timeout of 0 is treated as an infinite timeout period
     */
    MinecraftSocket(InetSocketAddress serverAddress, int timeout) throws IOException {
        this(serverAddress);
        this.socket.setSoTimeout(timeout);
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }

    /**
     * Returns the output stream for the socket.
     *
     * @return output stream for the socket
     *
     * @throws IOException if an error occurs whilst retrieving the output stream
     */
    MinecraftOutputStream getOutputStream() throws IOException {
        return new MinecraftOutputStream(socket.getOutputStream());
    }

    /**
     * Returns the output stream for the socket.
     *
     * @return output stream for the socket
     *
     * @throws IOException if an error occurs whilst retrieving the output stream
     */
    MinecraftInputStream getInputStream() throws IOException {
        return new MinecraftInputStream(socket.getInputStream());
    }

}

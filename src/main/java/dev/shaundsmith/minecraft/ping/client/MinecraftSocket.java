package dev.shaundsmith.minecraft.ping.client;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * Socket wrapper for performing socket-level interactions with a Minecraft server.
 *
 * @author Shaun Smith
 * @version 1.0.0
 */
public class MinecraftSocket implements Closeable {

    private final Socket socket;

    /**
     * Constructs a Minecraft socket for the provided address.
     *
     * @param serverAddress address of the Minecraft server.
     *
     * @throws IOException if an error occurs whilst constructing the socket
     */
    public MinecraftSocket(InetSocketAddress serverAddress) throws IOException {
        this.socket = new Socket(serverAddress.getHostString(), serverAddress.getPort());
    }

    /**
     * Sets the timeout of the socket.
     *
     * <p>A timeout of 0 seconds is treated as an infinite timeout period.
     *
     * @param timeout timeout of the socket (in milliseconds)
     *
     * @throws SocketException if an error in the underlying protocol
     */
    public void setTimeout(int timeout) throws SocketException {
        socket.setSoTimeout(timeout);
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

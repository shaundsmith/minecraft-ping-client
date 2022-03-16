package dev.shaundsmith.minecraft.ping.client;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Factory for creating sockets for a specified address.
 *
 * @author Shaun Smith
 * @since 1.0.0
 */
@FunctionalInterface
interface SocketFactory {

    /**
     * Creates a socket for the specified address.
     *
     * @param socketAddress address to create the socket for
     *
     * @return the socket for the address
     *
     * @throws IOException if an error occurs whilst creating the socket
     */
    MinecraftSocket createSocket(InetSocketAddress socketAddress) throws IOException;

}

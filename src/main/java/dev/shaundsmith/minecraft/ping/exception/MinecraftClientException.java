package dev.shaundsmith.minecraft.ping.exception;

/**
 * Exception thrown when a Minecraft client encounters an error.
 *
 * @author Shaun Smith
 * @since 1.0.0
 */
public class MinecraftClientException extends Exception {

    /**
     * Constructs a new Minecraft client exception with the provided message.
     *
     * @param message message to display as part of the exception
     */
    public MinecraftClientException(String message) {
        super(message);
    }

    /**
     * Constructs a new Minecraft client exception with the provided message and cause.
     *
     * @param message message to display as part of the exception
     * @param cause the cause of the exception
     */
    public MinecraftClientException(String message, Exception cause) {
        super(message, cause);
    }

}

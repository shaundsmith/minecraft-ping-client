package dev.shaundsmith.minecraft.ping.client.deserializer;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Reader which reads a {@link JsonNode} into the given object type.
 *
 * @param <T> the type of object to read the {@code JsonNode} into
 *
 * @author Shaun Smith
 * @since 1.0.0
 */
interface JsonReader<T> {

    /**
     * Returns {@literal true} if this {@code JsonReader} supports reading from the given {@code JsonNode}.
     *
     * @param jsonNode the JsonNode
     *
     * @return {@literal true} if reading from the {@code JsonNode} is supported, {@literal false} otherwise.
     */
    boolean supports(JsonNode jsonNode);

    /**
     * Reads the given {@code JsonNode} into an object of type {@code T}.
     *
     * @param jsonNode the {@code JsonNode} to read from
     *
     * @return the object as represented by the {@code JsonNode}
     */
    T read(JsonNode jsonNode);

}

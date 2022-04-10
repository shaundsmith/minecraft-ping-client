package dev.shaundsmith.minecraft.ping.client.deserializer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import lombok.experimental.UtilityClass;

import java.util.Arrays;

@UtilityClass
class JsonNestedNodeReader {

    /**
     * Returns the {@code JsonNode} at the given path, relative to the given {@code JsonNode}.
     *
     * @param node the node to use as the base of the path
     * @param path the path of the node to return
     *
     * @return the node at the given path, or a {@link MissingNode} if no node is present
     */
    JsonNode getByPath(JsonNode node, String... path) {

        JsonNode value = null;
        if (path.length > 0 && node.has(path[0])) {
            if (path.length == 1) {
                value = node.get(path[0]);
            } else {
                value = getByPath(node.get(path[0]), Arrays.copyOfRange(path, 1, path.length));
            }
        }

        return value == null ? MissingNode.getInstance() : value;
    }

}

package dev.shaundsmith.minecraft.ping.response;

public final class ChatBuilder {

    public static final String DEFAULT_TEXT = "a simple test minecraft server";

    private String text = DEFAULT_TEXT;

    private ChatBuilder() {
    }

    public static ChatBuilder aChat() {
        return new ChatBuilder();
    }

    public ChatBuilder withText(String text) {
        this.text = text;
        return this;
    }

    public Chat build() {
        return new Chat(text);
    }
}

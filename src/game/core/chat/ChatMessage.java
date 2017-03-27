package game.core.chat;

import game.core.event.Event;
import game.core.event.chat.ChatMessageCreatedEvent;
import game.core.event.chat.ChatMessageReceivedEvent;
import game.core.player.Player;

/**
 * Created by samtebbs on 27/01/2017.
 */
public class ChatMessage {

    public final String message;
    public final String from, to;

    public ChatMessage(String message, String from, String to) {
        this.message = message;
        this.from = from;
        this.to = to;
    }

    public ChatMessage(String message, String from) {
        this(message, from, null);
    }

    /**
     * Checks if the message is private (i.e no specific recipient)
     * @return true if private, else false
     */
    public boolean isPrivateMessage() {
        return to != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatMessage that = (ChatMessage) o;

        if (!message.equals(that.message)) return false;
        if (!from.equals(that.from)) return false;
        return to != null ? to.equals(that.to) : that.to == null;
    }

    @Override
    public int hashCode() {
        int result = message.hashCode();
        result = 31 * result + from.hashCode();
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }

    /**
     * Creates a ChatMessageCreatedEvent from this chat message object
     * @return
     */
    public ChatMessageCreatedEvent toCreatedEvent() {
        return to != null ? new ChatMessageCreatedEvent(from, to, message) : new ChatMessageCreatedEvent(from, message);
    }

}

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

    public boolean isPrivateMessage() {
        return to != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatMessage)) return false;

        ChatMessage that = (ChatMessage) o;

        if (!message.equals(that.message)) return false;
        if (!from.equals(that.from)) return false;
        return to.equals(that.to);

    }

    @Override
    public int hashCode() {
        int result = message.hashCode();
        result = 31 * result + from.hashCode();
        result = 31 * result + to.hashCode();
        return result;
    }

    public ChatMessageCreatedEvent toCreatedEvent() {
        return to != null ? new ChatMessageCreatedEvent(Player.localPlayerName, to, message) : new ChatMessageCreatedEvent(Player.localPlayerName, message);
    }

}

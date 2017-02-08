package game.core.chat;

import game.core.player.Player;

/**
 * Created by samtebbs on 27/01/2017.
 */
public class ChatMessage {

    public final String message;
    public final Player from, to;

    public ChatMessage(String message, Player from, Player to) {
        this.message = message;
        this.from = from;
        this.to = to;
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
}

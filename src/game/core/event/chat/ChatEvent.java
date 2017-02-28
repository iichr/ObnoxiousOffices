package game.core.event.chat;

import game.core.event.player.PlayerEvent;

/**
 * Created by samtebbs on 25/02/2017.
 */
public abstract class ChatEvent extends PlayerEvent {
    public final String recipient;
    public final String message;

    public ChatEvent(String playerName, String recipient, String message) {
        super(playerName);
        this.recipient = recipient;
        this.message = message;
    }


    public ChatEvent(String playerName, String message) {
        this(playerName, null, message);
    }

    public boolean isPrivateMessage() {
        return recipient != null;
    }

}

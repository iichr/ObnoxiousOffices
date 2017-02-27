package game.core.event.chat;

import game.core.event.Event;
import game.core.event.player.PlayerEvent;

/**
 * Created by samtebbs on 24/02/2017.
 */
public class ChatMessageCreatedEvent extends ChatEvent {

    public ChatMessageCreatedEvent(String playerName, String recipient, String message) {
        super(playerName, recipient, message);
    }

    public <T extends Event> ChatMessageCreatedEvent(String playerName, String message) {
        super(playerName, message);
    }

    public ChatMessageReceivedEvent toChatReceivedEvent() {
        return new ChatMessageReceivedEvent(playerName, recipient, message);
    }
}

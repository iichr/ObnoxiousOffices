package game.core.event.chat;

import game.core.chat.ChatMessage;
import game.core.world.World;

/**
 * Created by samtebbs on 25/02/2017.
 */
public class ChatMessageReceivedEvent extends ChatEvent {

    public ChatMessageReceivedEvent(String playerName, String recipient, String message) {
        super(playerName, recipient, message);
    }

    public ChatMessageReceivedEvent(String playerName, String message) {
        super(playerName, message);
    }

    public ChatMessage toChatMessage() {
        return isPrivateMessage() ? new ChatMessage(message, playerName, recipient) : new ChatMessage(message, playerName);
    }
}

package game.core.chat;

import game.core.event.Event;
import game.core.event.Events;
import game.core.event.chat.ChatMessageCreatedEvent;
import game.core.event.chat.ChatMessageReceivedEvent;
import game.core.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by samtebbs on 27/01/2017.
 */
public class Chat {

    public static Chat chat = new Chat();
    private final List<ChatMessage> messages = new ArrayList<>();

    public Chat() {
        Events.on(ChatMessageReceivedEvent.class, this::onMessageReceived);
    }

    private void onMessageReceived(ChatMessageReceivedEvent event) {
        addMessage(event.toChatMessage());
    }

    /**
     * Get the latest chat messages
     * @param num Number to get
     * @return latest messages
     */
    public List<ChatMessage> getLatestMessages(int num) {
        int skip = Math.max(0, messages.size() - num);
        return messages.stream().skip(skip).collect(Collectors.toList());
    }

    /**
     * Get all messages
     * @return all messages
     */
    public List<ChatMessage> getMessages() {
        return messages.stream().collect(Collectors.toList());
    }

    /**
     * Add a message
     * @param message
     */
    public void addMessage(ChatMessage message) {
        messages.add(message);
    }

    /**
     * Trigger a new chat message event
     * @param message
     */
    public void sendMessage(ChatMessage message) {
        Events.trigger(message.toCreatedEvent());
    }

    /**
     * Remove a message
     * @param message
     */
    public void removeMessage(ChatMessage message) {
        messages.remove(message);
    }
}

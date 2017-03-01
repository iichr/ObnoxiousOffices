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

    public List<ChatMessage> getLatestMessages(int num) {
        return messages.stream().skip(messages.size() - num).collect(Collectors.toList());
    }

    public List<ChatMessage> getMessages() {
        return messages.stream().collect(Collectors.toList());
    }

    public void addMessage(ChatMessage message) {
        messages.add(message);
    }

    public void sendMessage(ChatMessage message) {
        Events.trigger(message.toCreatedEvent());
    }

}

package game.core.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by samtebbs on 27/01/2017.
 */
public class Chat {

    private final List<ChatMessage> messages = new ArrayList<>();

    public List<ChatMessage> getLatestMessages(int num) {
        return messages.stream().skip(messages.size() - num).collect(Collectors.toList());
    }

    public List<ChatMessage> getMessages() {
        return messages.stream().collect(Collectors.toList());
    }

    public void addMessage(ChatMessage message) {
        messages.add(message);
    }

}

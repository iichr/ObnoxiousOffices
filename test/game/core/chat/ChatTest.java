package game.core.chat;

import org.junit.jupiter.api.Test;

import static game.core.TestUtil.*;

/**
 * Created by samtebbs on 22/03/2017.
 */
class ChatTest {

    Chat chat = new Chat();

    @Test
    void getLatestMessages() {
        addMessage();
    }

    @Test
    void getMessages() {
        addMessage();
    }

    @Test
    void addMessage() {
        wrappedTest(new ChatMessage("hi", "me"), m -> chat.getMessages().size() > 0 && chat.getLatestMessages(1).get(0).equals(m), chat::addMessage, chat::removeMessage);
    }

}
package game.core.chat;

import game.core.event.chat.ChatMessageCreatedEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by samtebbs on 27/03/2017.
 */
class ChatMessageTest {

    ChatMessage privateMessage = new ChatMessage("hi", "me", "you");
    ChatMessage publicMessage = new ChatMessage("hi", "me");

    @Test
    void isPrivateMessage() {
        assertTrue(privateMessage.isPrivateMessage());
        assertFalse(publicMessage.isPrivateMessage());
    }

    @Test
    void equals() {
        assertNotEquals(privateMessage, publicMessage);
        assertEquals(privateMessage, privateMessage);
        assertEquals(publicMessage, publicMessage);
    }

    @Test
    void toCreatedEvent() {
        ChatMessageCreatedEvent event = privateMessage.toCreatedEvent();
        assertEquals(event.message, privateMessage.message);
        assertEquals(event.playerName, privateMessage.from);
        assertEquals(event.recipient, privateMessage.to);
    }

}
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

}

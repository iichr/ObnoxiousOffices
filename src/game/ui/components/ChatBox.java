package game.ui.components;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;

import game.core.chat.Chat;
import game.core.chat.ChatMessage;
import game.ui.interfaces.Vals;

public class ChatBox {
	protected Chat chat;
	private String message = "";
	private TextField typer, main;
	private ArrayList<Character> allowedKeys = new ArrayList<Character>();
	private long inputInterval = 100;
	private long lastInput = System.currentTimeMillis();
	private List<ChatMessage> cms = new ArrayList<>();
	private int msgSize = 10;

	public ChatBox(GameContainer gc, Chat chat) {
		this.chat = chat;

		typer = new TextField(gc, Vals.FONT_MAIN, 0, Vals.SCREEN_HEIGHT - Vals.FONT_MAIN.getLineHeight() * 2,
				Vals.TFIELD_WIDTH, Vals.FONT_MAIN.getLineHeight(), new ComponentListener() {
					public void componentActivated(AbstractComponent src) {
						typer.setFocus(true);
					}
				});
		typer.setBackgroundColor(Color.white);
		typer.setTextColor(Color.red);
		typer.setMaxLength(50);
		
	}

	public char getChatKey() {
		return Keyboard.getEventCharacter();
	}

	public void render(GameContainer gc, Graphics g) {
		if (!cms.isEmpty()) {
			if (chat.getMessages().size() < msgSize) {
				cms = chat.getLatestMessages(chat.getMessages().size());
			} else {
				cms = chat.getLatestMessages(10);
			}

			for (ChatMessage cm : cms) {
				g.drawString(cm.from + " : " + cm.message, 0, 0);
			}

		}
		
		typer.render(gc, g);
	}

	public void update(String localPlayerName) {

		if (!typer.getText().isEmpty()) {
			chat.addMessage(new ChatMessage(typer.getText(), localPlayerName));
			chat.sendMessage(new ChatMessage(typer.getText(), localPlayerName));
		}

	}

}

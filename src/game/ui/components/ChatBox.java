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
	private TextField typer;
	private List<ChatMessage> cms = new ArrayList<>();
	private int msgSize = 10;

	public ChatBox(GameContainer gc, Chat chat) {
		this.chat = chat;

		typer = new TextField(gc, Vals.FONT_MAIN, 0, Vals.SCREEN_HEIGHT - Vals.FONT_MAIN.getLineHeight(),
				Vals.TFIELD_WIDTH * 10, Vals.FONT_MAIN.getLineHeight(), new ComponentListener() {
					public void componentActivated(AbstractComponent src) {
						typer.setFocus(true);
					}
				});
		typer.setBorderColor(Color.white);
		typer.setBackgroundColor(Color.transparent);
		typer.setMaxLength(50);

	}

	public char getChatKey() {
		return Keyboard.getEventCharacter();
	}

	public void render(GameContainer gc, Graphics g) {
		g.setColor(Color.white);
		cms = chat.getMessages();
		if (!cms.isEmpty()) {
			if (chat.getMessages().size() < msgSize) {
				cms = chat.getLatestMessages(chat.getMessages().size());
			} else {
				cms = chat.getLatestMessages(10);
			}
			int y = typer.getY() - typer.getHeight() * 10;
			for (ChatMessage cm : cms) {
				g.drawString(cm.from + " : " + cm.message, 0, y);
				y += typer.getHeight();
			}

		}

		typer.render(gc, g);
	}

	public void update(GameContainer gc, String localPlayerName) {
		Input input = gc.getInput();
		if (input.isKeyPressed(input.KEY_ENTER)) {
			if (!typer.getText().isEmpty()) {
				chat.sendMessage(new ChatMessage(typer.getText(), localPlayerName));
				typer.setText(message);
			}
		}

	}

	public void AIchat(String AIName, String text) {
		chat.sendMessage(new ChatMessage(text, AIName));
	}
	
	public void toggleFocus(){
		if(typer.hasFocus()){
			typer.setFocus(false);
		}else{
			typer.setFocus(true);
		}
	}

}

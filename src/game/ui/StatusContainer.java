package game.ui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public class StatusContainer extends Rectangle {

	private static final long serialVersionUID = 7035477320220180349L;
	private String p1 = "Player 1";
	private String p2 = "Player 2";
	private String p3 = "Player 3";
	private String p4 = "Player 4";

	private Image i1, i2, i3, i4;
	private int pad = 10;

	public StatusContainer(float x, float y, float width, float height, Image i1, Image i2, Image i3, Image i4) {
		super(x, y, width, height);
		this.i1 = i1.getScaledCopy((int) width / 2, setImgHeight());
		this.i2 = i2.getScaledCopy((int) width / 2, setImgHeight());
		this.i3 = i3.getScaledCopy((int) width / 2, setImgHeight());
		this.i4 = i4.getScaledCopy((int) width / 2, setImgHeight());
	}

	// supply with player avatars
	// Player one (current player should be bigger)
	// supply with progress
	// supply with player names

	public void render(Graphics g) {
		// fill the shape
		// g.fill(this);

		// Player names
		g.drawString(p1, this.x + getImgWidth(i1) + pad, this.y + pad);
		g.drawString(p2, this.x + getImgWidth(i2) + pad, this.y + getImgHeight(i1) + pad);
		g.drawString(p3, this.x + getImgWidth(i3) + pad, this.y + getImgHeight(i1) + getImgHeight(i2) + pad);
		g.drawString(p4, this.x + getImgWidth(i4) + pad,
				this.y + getImgHeight(i1) + getImgHeight(i2) + getImgHeight(i3) + pad);

		// Player avatars
		g.drawImage(i1, this.x, y);
		g.drawImage(i2, this.x, y + i1.getHeight());
		g.drawImage(i3, this.x, y + 2 * i2.getHeight());
		g.drawImage(i4, this.x, y + 3 * i3.getHeight());
	}

	int setImgHeight() {
		return (int) (this.height / 4);
	}

	int getImgHeight(Image img) {
		return img.getHeight();
	}

	int getImgWidth(Image img) {
		return img.getWidth();
	}
}

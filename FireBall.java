package balloone_Shooting;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class FireBall {

	private int x, y;
	private Image image;
	boolean visible;
	private int width, height;
	private int dy;

	public FireBall(int x) {
		dy = 20;
		y = 605;
		ImageIcon ii = new ImageIcon(this.getClass()
				.getResource("fireball.png"));
		image = ii.getImage();
		visible = true;
		width = image.getWidth(null);
		height = image.getHeight(null);
		this.x = x;
	}

	public Image getImage() {
		return image;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public void move() {
		y = y - dy;

	}
}
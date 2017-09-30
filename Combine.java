package balloone_Shooting;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class Combine extends JPanel implements ActionListener {
	Image back_Ground;
	Timer timer;

	Paddle paddle;
	ArrayList<FireBall> fireballs_array;
	ArrayList<Cloudball> cloud_array;
	FireBall fireBall;
	private boolean fire;

	public boolean isFire() {
		return fire;
	}

	public void setFire(boolean fire) {
		this.fire = fire;
	}

	public Combine() {
		addKeyListener(new Action());
		ImageIcon ii = new ImageIcon(this.getClass().getResource(
				"skybackground.png"));
		fireballs_array = new ArrayList<FireBall>();
		cloud_array = new ArrayList<Cloudball>();

		paddle = new Paddle();
		setFocusable(true);
		back_Ground = ii.getImage();
		timer = new Timer(70, this);
		timer.start();
		Thread startCloud = new Thread(new Runnable() {
			public void run() {
				while (true)
					try {
						cloudBalls();
						Thread.sleep(1000 * 2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

			}
		});
		startCloud.start();
		Thread startFire = new Thread(new Runnable() {
			public void run() {
				while (true)
					try {
						//System.out.println("fire " + fire);
						if (fire == true) {
							fire();
						}
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

			}
		});
		startFire.start();
		
		Thread movePaddle = new Thread(new Runnable() {
			public void run() {
				while (true)
					try {
						if (paddle.getLeft() == true) {
							paddle.left();
						}
						if (paddle.getRight() == true) {
							paddle.right();
						}
						Thread.sleep(9);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

			}
		});
		movePaddle.start();
		
	}

	public void actionPerformed(ActionEvent arg0) {
		// 800,700
		
		
	
		for (int k = 0; k < fireballs_array.size(); k++) {
			fireballs_array.get(k).move();
			if (fireballs_array.get(k).getY() < 1) {
				fireballs_array.remove(k);
			}
		}
		for (int i = 0; i < cloud_array.size(); i++) {
			cloud_array.get(i).move();
			if (cloud_array.get(i).getY() > 600) {
				cloud_array.remove(i);
			}
		}
		Thread collide = new Thread(new Runnable() {

			public void run() {
				collision();
			}

		});
		collide.start();
		
		repaint();
	}

	public void paintComponent(Graphics g) {
		g.drawImage(back_Ground, 0, 0, null);
		g.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(), null);

		if (!cloud_array.isEmpty()) {
			for (int i = 0; i < cloud_array.size(); i++) {
				g.drawImage(cloud_array.get(i).getImage(), cloud_array.get(i)
						.getX(), cloud_array.get(i).getY(), null);
			}
		}
		if (!fireballs_array.isEmpty()) {
			for (int k = 0; k < fireballs_array.size(); k++) {
				g.drawImage(fireballs_array.get(k).getImage(), fireballs_array
						.get(k).getX(), fireballs_array.get(k).getY(), null);
			}
		}

	}

	public void cloudBalls() {
		Random rn = new Random();
		int ran = rn.nextInt(730);
		Cloudball ball = new Cloudball(ran);
		cloud_array.add(ball);

	}

	public void fire() {
		fireBall = new FireBall(paddle.getX() + 35);
		// fireBall.setY(20);
		fireballs_array.add(fireBall);
	}// ///////////////////////////////////////////////////////

	public void collision() {
		for (int k = 0; k < fireballs_array.size(); k++) {
			for (int j = 0; j < cloud_array.size(); j++) {
				Rectangle r1;
				if(!fireballs_array.isEmpty()){
					 r1 = fireballs_array.get(k).getBounds();
				
				
				Rectangle r2 = cloud_array.get(j).getBounds();
				if (r1.intersects(r2)) {
					fireballs_array.remove(k);
					cloud_array.remove(j);
				}
				}

			}
		}
	}

	private class Action extends KeyAdapter {
		public void keyPressed(KeyEvent e) {

			paddle.volcano_Pressed();
			int keyCode = e.getKeyCode();
			if (keyCode == KeyEvent.VK_SPACE) {
				fire = true;
			}

			if (keyCode == KeyEvent.VK_LEFT) {
				paddle.setLeft(true);
			}

			if (keyCode == KeyEvent.VK_RIGHT) {
				paddle.setRight(true);
			}

		}

		public void keyReleased(KeyEvent e) {

			paddle.volcano_Release();

			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				fire = false;
			}

			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				paddle.setLeft(false);
			}

			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				paddle.setRight(false);
			}
		}
	}

}

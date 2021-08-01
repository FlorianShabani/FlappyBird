package FlappyBird;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import matrixmath.Matrix;

public class PG {

	Bird[] birds;
	private float acc = 1f;
	private int bx;
	private boolean stop = false;
	private int width, height;
	private int speed = 2;
	private int bdist = 200;
	private int bc = 5;
	private LinkedList<Rectangle> barriers = new LinkedList<Rectangle>();
	private int bgap = 150;
	private int bwidth = 30;
	private int sub;
	private int[] fitness;
	private double mutations;
	private static int dist;
	private int gen = 0;
	int dead = 0;

	BufferedImage bimg;

	public PG(int sub, int layers, int[] nnodes, double mutations, int width,
			int height) {
		this.width = width;
		this.height = height;
		this.sub = sub;
		this.mutations = mutations;
		fitness = new int[sub];
		birds = new Bird[sub];
		bx = 20;
		for (int i = 0; i < sub; i++) {
			Bird b = new Bird(layers, nnodes);
			b.setX(bx);
			b.setY(height / 2);
			birds[i] = b;
		}

		try {
			bimg = ImageIO.read(getClass().getResource("/raw.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		nBarriers();
	}

	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		for (int i = 0; i < birds.length; i++) {
			Bird b = birds[i];
			if (b.dead) {
				continue;
			}
			b.draw(g, bimg);
		}
		g.setColor(new Color(200, 200, 0));
		for (int i = 0; i < barriers.size(); i++) {
			g.fillRect(barriers.get(i).x, barriers.get(i).y,
					barriers.get(i).width, barriers.get(i).height);
		}
		g.setColor(Color.BLACK);
		g.drawString("Score " + dist/200 + "  Gen " + gen, 20, 20);
	}

	public void tick() {
		if (!stop) {
			dist++;
			reset();
			checkDeadBirds();
			for (Bird b : birds) {
				if (b.dead) {
					continue;
				}
				if (dist % 5 == 0)
					b.askJump(barriers.get(0).height, barriers.get(0).x, bgap);
				b.setVy(b.getVy() + acc);
				b.tick();
			}
			for (int i = 0; i < barriers.size(); i += 2) {
				barriers.get(i).x -= speed;
				barriers.get(i + 1).x -= speed;
				if (barriers.get(i).x <= bx - bwidth) {
					barriers.removeFirst();
					barriers.removeFirst();
					int rand = (int) ((Math.random() * height) - bgap) / 2
							+ width / 3;
					barriers.addLast(
							new Rectangle(10 * bdist, 0, bwidth, rand));
					barriers.addLast(new Rectangle(10 * bdist, rand + bgap,
							bwidth, height));
				}
			}
		}
	}

	public void reset() {
		if (dead == sub) {
			Bird[] nbirds = new Bird[sub];
			for (int i = 0; i < sub; i++) {
				int i1 = getPool(fitness);
				int i2 = getPool(fitness);
				nbirds[i] = new Bird(birds[i1], birds[i2], mutations);
				nbirds[i].setX(bx);
				nbirds[i].setY(height / 2);
				nBarriers();
			}
			fitness = new int[sub];
			dist = 0;
			birds = nbirds;
			dead = 0;
			gen++;
		}
	}

	public void nBarriers() {
		barriers = new LinkedList<Rectangle>();
		for (int i = 0; i < bc * 2; i += 2) {
			int rand = (int) (Math.random() * (height - bgap));
			barriers.add(new Rectangle(i * bdist - 10, 0, bwidth, rand));
			barriers.add(
					new Rectangle(i * bdist - 10, rand + bgap, bwidth, height));
		}
	}

	public static int getPool(int[] fit) {
		double total = 0;
		double at = 0;
		double num = Math.random();
		for (int i : fit) {
			total += i;
		}
		for (int i = 0; i < fit.length; i++) {
			at += Matrix.map(fit[i], 0, total, 0, 1);
			if (at >= num) {
				return i;
			}
		}
		return 0;
	}

	public void checkDeadBirds() {
		for (int i = 0; i < birds.length; i++) {
			Bird x1 = birds[i];
			for (int j = 0; j < barriers.size(); j++) {
				Rectangle x2 = barriers.get(j);
				if (!x1.dead
						&& ((x1.x > x2.x && x1.x < x2.x + x2.width)
								|| (x1.x + x1.w > x2.x
										&& x1.x + x1.w < x2.x + x2.width))
						&& ((x1.y > x2.y && x1.y < x2.y + x2.height)
								|| (x1.y + x1.h > x2.y + x2.height
										&& x1.y + x1.h < x2.y + x2.height))) {

					x1.dead = true;
					fitness[i] = dist / 5;
					dead++;

				}
			}
			if (!x1.dead && (x1.getY() < 0 || x1.getY() > height)) {
				x1.dead = true;
				fitness[i] = dist / 5;
				dead++;
			}
		}
	}

	public void keyPressed(int k) {
		if (k == 106) {
			stop = true;
		}
	}
}

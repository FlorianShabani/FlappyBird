package FlappyBird;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import neuralnetwork.MNeuralNetwork;

public class Bird extends Rectangle {
	private static final long serialVersionUID = 1L;

	private MNeuralNetwork mnn;
	public float x, y, vx, vy;
	private static final int jumpF = 20;
	public int w = 40, h = 30;
	public boolean dead = false;

	public Bird(int layers, int[] nnodes) {
		mnn = new MNeuralNetwork(layers, nnodes);
	}

	public Bird(Bird b1, Bird b2, double mutations) {
		mnn = MNeuralNetwork.fuse(b1.getMnn(), b2.getMnn(), mutations);
	}

	public void askJump(int by, int dbx, int bgap) {
		float[] inputs = { by - y, by - y + bgap, vy, dbx - x};
		float[][] r = mnn.feedforward(inputs);
		if (r[0][0] > 0.5) {
			jump();
		}
	}

	public void draw(Graphics g, BufferedImage bimg) {
		g.drawImage(bimg, (int) x, (int) y, w, h, null);
	}

	public void tick() {
		x += vx;
		y += vy;
	}

	public void jump() {
		vy -= jumpF;
	}

	public double getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getVx() {
		return vx;
	}

	public void setVx(float vx) {
		this.vx = vx;
	}

	public float getVy() {
		return vy;
	}

	public void setVy(float vy) {
		this.vy = vy;
	}

	public MNeuralNetwork getMnn() {
		return mnn;
	}

	public void setMnn(MNeuralNetwork mnn) {
		this.mnn = mnn;
	}
}

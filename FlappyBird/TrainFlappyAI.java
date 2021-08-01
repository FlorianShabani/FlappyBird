package FlappyBird;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import setup.*;

public class TrainFlappyAI implements Manager{

	public static final int Width = 700, Height = 700;
	
	PG pg;
	
	public TrainFlappyAI(int sub, int layers, int[] nnodes) {
		pg = new PG(sub, layers, nnodes, 0.1,  Width, Height);
	}
	
	@Override
	public void draw(Graphics g) {
		pg.draw(g);
	}

	@Override
	public void tick() {
		pg.tick();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		
	}

	@Override
	public void keyPressed(int e) {
		pg.keyPressed(e);
		
	}

	@Override
	public void keyReleased(int e) {
		
		
	}

	@Override
	public void keyTyped(int e) {
		
		
	}
	public static void main(String[] args) {
		int sub = 50;
		int layers = 4;
		int[] nnodes = {4, 5, 3, 1};
		@SuppressWarnings("unused")
		Window wind = new Window(Width, Height, 60, 60, "Train AI", new TrainFlappyAI(sub, layers, nnodes));
	}
}

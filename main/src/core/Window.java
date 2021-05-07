package core;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import ui.Canvas;

public class Window {

	protected final Canvas canvas;

	private final JFrame frame;

	private final Game game;

	private int canvasWidth = 800;
	private int canvasHeight = 600;

	public Window(Game game) {

		this.game = game;

		this.canvas = new Canvas(game);

		frame = new JFrame();
		frame.setResizable(false);

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(canvas.getComponent(), BorderLayout.CENTER);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				game.requestQuit();
			}
		});
	}

	public void setCanvasSize(int width, int height) {

		assert width > 0;
		assert height > 0;

		this.canvasWidth = width;
		this.canvasHeight = height;

		resizeWindow();
	}

	public void setVisible() {
		resizeWindow();
		frame.setVisible(true);
	}

	private void resizeWindow() {
		canvas.setPreferredSize(canvasWidth, canvasHeight);
		frame.pack();
	}
}

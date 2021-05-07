package core;

import java.util.concurrent.atomic.AtomicBoolean;

import io.InputHandler;
import util.UpdateContext;

public abstract class Game {

	public final World world;

	public final Window window;

	private final AtomicBoolean shouldQuit = new AtomicBoolean(false);

	private Thread mainThread;

	private double targetFPS = 60.0;
	private double targetFrameTime = 1.0 / targetFPS;

	public Game() {

		world = new World();
		window = new Window(this);

		init();
		setupGame();
		initMainThread();
	}

	private void init() {

		InputHandler input = new InputHandler();
		world.setInputHandler(input);

		DebugConsole console = new DebugConsole();
		world.setConsole(console);
	}

	public void run() {
		mainThread.start();
		window.setVisible();
	}

	public abstract void setupGame();

	public void setTargetFPS(double targetFPS) {
		this.targetFPS = targetFPS;
		this.targetFrameTime = 1.0 / targetFPS;
	}

	public void requestQuit() {
		shouldQuit.set(true);
	}

	private void initMainThread() {

		mainThread = new Thread(() -> {

			long lastTime = getTime();

			while (!shouldQuit.get()) {

				long loopStartTime = getTime();
				long deltaTime = loopStartTime - lastTime;

				UpdateContext context = new UpdateContext(world, loopStartTime, lastTime, deltaTime);

				lastTime = loopStartTime;

				world.getInputHandler().update(context);

				update(context);

				window.canvas.draw();

				sync(loopStartTime);
			}

			return;
		});
	}

	private long getTime() {
		return System.currentTimeMillis();
	}

	private void sync(long loopStartTime) {
		
		long endTime = (long) (loopStartTime + targetFrameTime);
		
//		while (getTime() < endTime) {

		long diff = (endTime - getTime());
		
		if (diff > 0) {
			try {
				Thread.sleep(diff);
//				Thread.sleep(1L);
			} catch (InterruptedException ignored) {
			}
		}
//		}
	}

	private void update(UpdateContext context) {
		context.world.getEntities().forEach(e -> e.update(context));
	}
}

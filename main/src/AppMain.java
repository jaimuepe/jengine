import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;

import components.Renderer;
import components.Updatable;
import core.AmbientLight;
import core.Camera;
import core.DebugConsole;
import core.DirectionalLight;
import core.Vec3;
import core.World;
import io.InputHandler;
import shapes.Cube;
import ui.Canvas;
import util.UpdateContext;

public class AppMain {

	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;

	private static final double UPDATE_TARGET_FRAME_TIME = 1000.0 / 60.0;
	private static final double RENDER_TARGET_FRAME_TIME = 1000.0 / 60.0;

	public static void main(String[] args) {

		final AtomicBoolean shouldQuit = new AtomicBoolean(false);

		double aspectRatio = (double) WIDTH / HEIGHT;

		World world = new World();

		AmbientLight ambient = new AmbientLight(new Vec3(0.1, 0.1, 0.1));
		DirectionalLight dirLight = new DirectionalLight(new Vec3(1.0, 0.0, 0.0), new Vec3(0.5, 0.5, 0.5));

		DebugConsole console = new DebugConsole();

		Camera camera = new Camera(0.1, 100.0, aspectRatio, 90.0f);

		camera.addComponent(new Updatable("playerController") {

			double movementSpeed = 1.0f;

			@Override
			public void update(UpdateContext context) {

				if (context.world.getInputHandler().isKey(KeyEvent.VK_W)) {
					Vec3 fwd = owner.transform.front();
					owner.transform.translate(fwd.mul(movementSpeed * context.deltaTimeSeconds));
				}

				if (context.world.getInputHandler().isKey(KeyEvent.VK_S)) {
					Vec3 bwd = owner.transform.back();
					owner.transform.translate(bwd.mul(movementSpeed * context.deltaTimeSeconds));
				}

				if (context.world.getInputHandler().isKey(KeyEvent.VK_A)) {
					Vec3 left = owner.transform.left();
					owner.transform.translate(left.mul(movementSpeed * context.deltaTimeSeconds));
				}

				if (context.world.getInputHandler().isKey(KeyEvent.VK_D)) {
					Vec3 right = owner.transform.right();
					owner.transform.translate(right.mul(movementSpeed * context.deltaTimeSeconds));
				}
			}
		});

		console.addPrintable("Camera pos: ", () -> camera.transform.getPosition());
		console.addPrintable("Camera rot: ", () -> camera.transform.getRotation());

		InputHandler input = new InputHandler();

		world.setConsole(console);
		world.setMainCamera(camera);
		world.setInputHandler(input);
		world.setAmbientLight(ambient);
		world.setDirectionalLight(dirLight);

		Cube spinnyCube1 = new Cube();

		spinnyCube1.addComponent(new Updatable("spin") {
			@Override
			public void update(UpdateContext context) {
				owner.transform.rotate(new Vec3(0.0, context.deltaTimeSeconds, 0.0));
			}
		});

		Renderer r1 = spinnyCube1.getComponentUnsafe(Renderer.class);
		r1.setColor(new Vec3(1.0, 0.0, 0.0));

		spinnyCube1.transform.setPosition(new Vec3(0.0, 0.0, 3.0));

		world.register(spinnyCube1);

		Canvas canvas = new Canvas(world);
		canvas.setPreferredSize(WIDTH, HEIGHT);

		JFrame f = new JFrame();
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				shouldQuit.set(true);
				f.dispose();
			}
		});

		f.getContentPane().setLayout(new BorderLayout());
		f.getContentPane().add(canvas.getComponent(), BorderLayout.CENTER);

		final Thread mainThread = new Thread(() -> {

			double steps = 0.0;
			long lastTime = getTime();

			while (!shouldQuit.get()) {

				long loopStartTime = getTime();
				long deltaTime = loopStartTime - lastTime;

				UpdateContext context = new UpdateContext(world, loopStartTime, lastTime, deltaTime);

				lastTime = loopStartTime;
				steps += deltaTime;

//                 f.setTitle("FPS: " + (1000.0 / deltaTime));

				input.update(context);

//                while (steps >= UPDATE_TARGET_FRAME_TIME) {
				update(context);
//                    steps -= UPDATE_TARGET_FRAME_TIME;
//                }

				canvas.draw();

				sync(loopStartTime);
			}
		});

		mainThread.start();

		f.pack();
		f.setVisible(true);
	}

	private static long getTime() {
		return System.currentTimeMillis();
	}

	private static void sync(double loopStartTime) {
		double endTime = loopStartTime + RENDER_TARGET_FRAME_TIME;
		while (getTime() < endTime) {
			try {
				Thread.sleep(1L);
			} catch (InterruptedException ignored) {
			}
		}
	}

	private static void update(UpdateContext context) {
		context.world.getEntities().forEach(e -> e.update(context));
	}
}

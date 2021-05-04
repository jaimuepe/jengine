import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;

import core.Camera;
import core.Vec3;
import core.World;
import shapes.Cube;
import ui.Canvas;
import util.UpdateContext;

public class AppMain {

	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;

	private static final double UPDATE_TARGET_FRAME_TIME = 1000.0 / 60.0;
	private static final double RENDER_TARGET_FRAME_TIME = 1000.0 / 60.0;

	private static Thread mainThread;

	private static AtomicBoolean shouldQuit = new AtomicBoolean(false);

	public static void main(String[] args) throws InterruptedException {

		double aspectRatio = (double) WIDTH / HEIGHT;

		World world = new World();

		Camera camera = new Camera(0.1, aspectRatio, 100.0f, 90.0f);

		world.setMainCamera(camera);

		Cube spinnyCube1 = new Cube() {

			@Override
			public void update(UpdateContext context) {

				double t = context.time / 1000.0;

				rotation.y = t;
//				rotation.y = Math.sin(t);
			};
		};

		spinnyCube1.setPosition(new Vec3(0, 0, 10));
		spinnyCube1.setScale(new Vec3(5.0, 1.0, 1.0));
		spinnyCube1.setRotation(new Vec3(-0.3, 0.0, 0.0));

		Cube spinnyCube2 = new Cube() {

			@Override
			public void update(UpdateContext context) {

				double t = context.time / 1000.0;

				rotation.x = Math.cos(-0.5 + t);
				rotation.y = Math.sin(0.6 + t);
			};
		};

//		spinnyCube2.setPosition(new Vec3(500, 400, 0));
//		spinnyCube2.setScale(new Vec3(20, 20, 20));
//		spinnyCube2.setRotation(new Vec3(-2.0, 0.0, 0.0));

		world.register(spinnyCube1);
//		world.register(spinnyCube2);

		Canvas canvas = new Canvas(world);
		canvas.setPreferredSize(WIDTH, HEIGHT);

		JFrame f = new JFrame();
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				shouldQuit.set(true);
			}
		});

		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		f.getContentPane().setLayout(new BorderLayout());
		f.getContentPane().add(canvas.getComponent(), BorderLayout.CENTER);

		mainThread = new Thread(() -> {

			double steps = 0.0;
			double lastTime = getTime();

			while (!shouldQuit.get()) {

				double loopStartTime = getTime();
				double deltaTime = loopStartTime - lastTime;
				lastTime = loopStartTime;
				steps += deltaTime;

				f.setTitle("FPS: " + (1000.0 / deltaTime));

				while (steps >= UPDATE_TARGET_FRAME_TIME) {
					UpdateContext uc = new UpdateContext(world, loopStartTime, deltaTime);
					update(uc);
					steps -= UPDATE_TARGET_FRAME_TIME;
				}

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
			} catch (InterruptedException ie) {
			}
		}
	}

	private static void update(UpdateContext context) {
		context.world.getEntities().forEach(e -> e.update(context));
	}
}
import java.awt.event.KeyEvent;

import components.Renderer;
import components.Updatable;
import core.AmbientLight;
import core.Camera;
import core.DirectionalLight;
import core.Game;
import core.Vec3;
import shapes.Cube;
import util.UpdateContext;

public class AppMain {

	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;

	public static void main(String[] args) {

		double aspect = (double) WIDTH / HEIGHT;

		Camera camera = new Camera(0.1, 100.0, aspect, 90.0f);

		camera.addComponent(new Updatable("playerController") {

			private double movementSpeed = 2.0f;

			@Override
			public void update(UpdateContext context) {

				Vec3 dir = new Vec3();

				if (context.world.getInputHandler().isKey(KeyEvent.VK_W)) {
					dir.sumSelf(owner.transform.front());
				}

				if (context.world.getInputHandler().isKey(KeyEvent.VK_S)) {
					dir.sumSelf(owner.transform.back());
				}

				if (context.world.getInputHandler().isKey(KeyEvent.VK_A)) {
					dir.sumSelf(owner.transform.left());
				}

				if (context.world.getInputHandler().isKey(KeyEvent.VK_D)) {
					dir.sumSelf(owner.transform.right());
				}

				owner.transform.translate(dir.mul(movementSpeed * context.deltaTimeSeconds));
			}
		});

		Cube spinnyCube1 = new Cube();

		spinnyCube1.transform.setPosition(new Vec3(0.0, 0.0, 3.0));
//		spinnyCube1.transform.setScale(new Vec3(2.0, 2.0, 2.0));

		spinnyCube1.addComponent(new Updatable("spin") {
			@Override
			public void update(UpdateContext context) {
				owner.transform.rotate(new Vec3(context.deltaTimeSeconds, context.deltaTimeSeconds, 0.0));
			}
		});

		Game game = new Game() {

			@Override
			public void setupGame() {

				window.setCanvasSize(WIDTH, HEIGHT);

				AmbientLight ambient = new AmbientLight(new Vec3(0.1, 0.1, 0.1));
				DirectionalLight dirLight = new DirectionalLight(new Vec3(0.0, -0.8, 0.3).normalized(),
						new Vec3(0.5, 0.5, 0.5));

				world.setMainCamera(camera);

				world.setAmbientLight(ambient);
				world.setDirectionalLight(dirLight);

				world.register(spinnyCube1);
			}
		};

		game.run();
	}
}

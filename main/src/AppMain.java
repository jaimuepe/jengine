import java.awt.BorderLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;

import components.Updatable;
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

    public static void main(String[] args) {

        final AtomicBoolean shouldQuit = new AtomicBoolean(false);

        double aspectRatio = (double) WIDTH / HEIGHT;

        World world = new World();
        InputHandler input = new InputHandler();
        
        Camera camera = new Camera(0.1, aspectRatio, 100.0f, 90.0f);

        world.setMainCamera(camera);

        Cube spinnyCube1 = new Cube();

        spinnyCube1.transform.setPosition(new Vec3(0, 0, 10));
        spinnyCube1.transform.setScale(new Vec3(1.0, 2.0, 3.0));
        spinnyCube1.transform.setRotation(new Vec3(-0.3, 0.0, 0.0));
        
        spinnyCube1.addComponent(new Updatable("spinny") {
        	@Override
        	public void update(UpdateContext context) {
        		 owner.transform.rotate(new Vec3(0.0, context.deltaTimeSeconds, 0.0));
        	}
		});

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

                f.setTitle("FPS: " + (1000.0 / deltaTime));
                
                input.update(context);
                
                while (steps >= UPDATE_TARGET_FRAME_TIME) {
                    update(context);
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
            } catch (InterruptedException ignored) {
            }
        }
    }

    private static void update(UpdateContext context) {
        context.world.getEntities().forEach(e -> e.update(context));
    }
}

class InputHandler {
	
	class KeyState {
		
		private long lastPressed;
		private long lastReleased;
		
		private final int keyCode;
	
		public KeyState(int keyCode) {
			this.keyCode = keyCode;
		}
	}

	private long lastFrameTime;
	
	private final KeyState[] keyStates = new KeyState[0xFF];
	
	public InputHandler() {

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

			@Override
			public boolean dispatchKeyEvent(KeyEvent ke) {

				if (ke.getID() == KeyEvent.KEY_PRESSED || ke.getID() == KeyEvent.KEY_RELEASED) {

					long when = ke.getWhen();
					int keyCode = ke.getKeyCode();

					if (keyStates[keyCode] == null) {
						keyStates[keyCode] = new KeyState(keyCode);
					}

					if (ke.getID() == KeyEvent.KEY_PRESSED) {
						keyStates[keyCode].lastPressed = when;
					} else {
						keyStates[keyCode].lastReleased = when;
					}
				}

				return false;
			}
		});
	}
	
	public boolean isKeyReleased(int keyCode) {

		if (keyStates[keyCode] == null) {
			return false;
		}

		return keyStates[keyCode].lastReleased > keyStates[keyCode].lastPressed
				&& keyStates[keyCode].lastReleased > lastFrameTime;
	}
	
	public boolean isKeyPressed(int keyCode) {

		if (keyStates[keyCode] == null) {
			return false;
		}

		return keyStates[keyCode].lastPressed > keyStates[keyCode].lastReleased
				&& keyStates[keyCode].lastPressed > lastFrameTime;
	}
	
	public void update(UpdateContext context) {
		this.lastFrameTime = context.time;
	}
}
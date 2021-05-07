package core;

import io.InputHandler;

import java.util.ArrayList;
import java.util.List;

public class World {

	private final List<Entity> entities;

	private Camera mainCamera;
	private InputHandler inputHandler;
	private DebugConsole console;

	private AmbientLight ambientLight;
	private DirectionalLight dirLight;

	public World() {
		entities = new ArrayList<>();
	}

	public void register(Entity entity) {
		entities.add(entity);
	}

	public InputHandler getInputHandler() {
		return inputHandler;
	}

	protected void setInputHandler(InputHandler inputHandler) {
		this.inputHandler = inputHandler;
	}
	
	protected void setConsole(DebugConsole console) {
		this.console = console;
	}

	public DebugConsole getConsole() {
		return console;
	}

	public void setMainCamera(Camera camera) {
		this.mainCamera = camera;
		if (!entities.contains(camera)) {
			entities.add(0, camera);
		}
	}

	public Camera getMainCamera() {
		return mainCamera;
	}

	public void setAmbientLight(AmbientLight ambientLight) {
		this.ambientLight = ambientLight;
	}

	public AmbientLight getAmbientLight() {
		return ambientLight;
	}

	public void setDirectionalLight(DirectionalLight dirLight) {
		this.dirLight = dirLight;
	}

	public DirectionalLight getDirectionalLight() {
		return dirLight;
	}

	public List<Entity> getEntities() {
		return new ArrayList<>(entities);
	}
}
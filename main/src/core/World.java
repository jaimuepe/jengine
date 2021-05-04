package core;

import java.util.ArrayList;
import java.util.List;

public class World {

	private List<Entity> entities;

	private Camera mainCamera;

	public World() {
		entities = new ArrayList<>();
	}

	public void register(Entity entity) {
		entities.add(entity);
	}

	public void setMainCamera(Camera camera) {
		this.mainCamera = camera;
	}

	public Camera getMainCamera() {
		return mainCamera;
	}

	public List<Entity> getEntities() {
		return new ArrayList<>(entities);
	}
}
package core;

import io.InputHandler;

import java.util.ArrayList;
import java.util.List;

public class World {

    private final List<Entity> entities;

    private Camera mainCamera;
    private InputHandler inputHandler;

    public World() {
        entities = new ArrayList<>();
    }

    public void register(Entity entity) {
        entities.add(entity);
    }

    public void setInputHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
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

    public List<Entity> getEntities() {
        return new ArrayList<>(entities);
    }
}
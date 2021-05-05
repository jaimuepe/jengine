package shapes;

import components.Renderer;
import core.Entity;
import core.Meshes;

public class Cube extends Entity {

    public Cube() {
        Renderer renderer = new Renderer();
        renderer.setData(Meshes.CUBE_1x1);
        addComponent(renderer);
    }
}
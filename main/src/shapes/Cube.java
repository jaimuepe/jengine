package shapes;

import components.Renderer;
import core.Axis;
import core.Entity;
import core.Mat4;
import core.Meshes;
import core.Vec3;
import util.MyGraphics;
import util.RenderContext;
import util.Transform;

public class Cube extends Entity {

    public Cube() {
        Renderer renderer = new Renderer(this);
        renderer.setData(Meshes.CUBE_1x1);
        addComponent(renderer);
    }
}
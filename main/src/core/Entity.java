package core;

import util.RenderContext;
import util.UpdateContext;

public abstract class Entity {

    public Transform transform;

    protected Vec3[] vertices;

    public Entity() {
        transform = new Transform();
    }

    public void update(UpdateContext context) {
        return;
    }

    public abstract void draw(RenderContext context);
}
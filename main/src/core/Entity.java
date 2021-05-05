package core;

import components.Component;
import util.RenderContext;
import util.UpdateContext;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {

    public Transform transform;

    private final List<Component> components;

    public Entity() {
        transform = new Transform();
        components = new ArrayList<>();
    }

    public void update(UpdateContext context) {
        for (Component c : components) {
            if ((c.getFlags() & Component.Flags.UPDATABLE) != 0) {
                c.update(context);
            }
        }
    }

    public void render(RenderContext context) {
        for (Component c : components) {
            if ((c.getFlags() & Component.Flags.RENDERABLE) != 0) {
                c.render(context);
            }
        }
    }

    public void addComponent(Component component) {
    	component.setOwner(this);
        components.add(component);
    }
}
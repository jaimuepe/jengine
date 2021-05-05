package core;

import components.Component;
import graphics.RenderContext;
import util.UpdateContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public <T extends Component> Optional<T> getComponent(Class<T> componentClass) {
        for (Component c : components) {
            if (c.getClass().isAssignableFrom(componentClass)) {
                return Optional.of((T) c);
            }
        }
        return Optional.empty();
    }

    public <T extends Component> T getComponentUnsafe(Class<T> componentClass) {
        return getComponent(componentClass).get();
    }
}
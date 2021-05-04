package components;

import core.Entity;
import util.RenderContext;
import util.UpdateContext;

public abstract class Component {

    public static class Flags {
        public static final int DRAWABLE = 1;
        public static final int UPDATABLE = 1 << 1;
    }

    protected final Entity owner;
    protected int flags;

    private final String id;

    public Component(String id, Entity owner) {
        this.id = id;
        this.owner = owner;
    }

    public void setFlag(int flag) {
        this.flags |= flag;
    }

    public void unsetFlag(int flag) {
        this.flags &= ~flag;
    }

    public int getFlags() {
        return flags;
    }

    public void render(RenderContext context) {
        throw new UnsupportedOperationException();
    }

    public void update(UpdateContext context) {
        throw new UnsupportedOperationException();
    }
}

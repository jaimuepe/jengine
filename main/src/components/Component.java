package components;

import core.Entity;
import graphics.RenderContext;
import util.UpdateContext;

public abstract class Component {

    public static class Flags {
        public static final int RENDERABLE = 1;
        public static final int UPDATABLE = 1 << 1;
    }

    public final String id;
    
    protected Entity owner;
    
    private int flags;


    public Component(String id) {
        this.id = id;
    }

    public void render(RenderContext context) {
        throw new UnsupportedOperationException();
    }

    public void update(UpdateContext context) {
        throw new UnsupportedOperationException();
    }
    
	public Entity getOwner() {
		return owner;
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

	public void setOwner(Entity owner) {
		this.owner = owner;
	}
}

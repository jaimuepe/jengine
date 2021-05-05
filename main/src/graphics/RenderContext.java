package graphics;

import java.awt.*;

import core.Camera;

public class RenderContext {

    public final Camera camera;
    public final Graphics2D graphics;
    public final int viewportWidth;
    public final int viewportHeight;

    public final DrawQueue queue;

    public RenderContext(Camera camera, int viewportWidth, int viewportHeight, Graphics2D graphics) {

        this.camera = camera;
        this.graphics = graphics;
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;

        this.queue = new DrawQueue();
    }
}

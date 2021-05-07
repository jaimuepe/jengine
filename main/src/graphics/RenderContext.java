package graphics;

import java.awt.Graphics2D;

import core.Camera;
import core.DirectionalLight;
import core.LightInfo;

public class RenderContext {

	public final Camera camera;
	public final Graphics2D graphics;
	public final int viewportWidth;
	public final int viewportHeight;

	public final DrawQueue queue;

	public final LightInfo lightInfo;

	public RenderContext(Camera camera, LightInfo lightInfo, int viewportWidth, int viewportHeight,
			Graphics2D graphics) {

		this.camera = camera;
		this.lightInfo = lightInfo;
		this.graphics = graphics;
		this.viewportWidth = viewportWidth;
		this.viewportHeight = viewportHeight;

		this.queue = new DrawQueue();
	}
}

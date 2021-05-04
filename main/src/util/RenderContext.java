package util;

import java.awt.Graphics;

import core.Camera;

public class RenderContext {

	public final Camera camera;
	public final Graphics graphics;
	public final int viewportWidth;
	public final int viewportHeight;

	public RenderContext(Camera camera, int viewportWidth, int viewportHeight, Graphics graphics) {
		this.camera = camera;
		this.graphics = graphics;
		this.viewportWidth = viewportWidth;
		this.viewportHeight = viewportHeight;
	}
}

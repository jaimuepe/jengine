package util;

import java.awt.Graphics;

import core.Vec3;

public class MyGraphics {

	public static void drawLine(RenderContext context, Vec3 start, Vec3 end) {

		Graphics g = context.graphics;

		// de-normalize from [-1, 1] to [viewport_width, viewport_height]
		int w = context.viewportWidth;
		int h = context.viewportHeight;

		int a = (int) denormalize(start.x, 0.0, w);
		int b = (int) denormalize(start.y, 0.0, h);
		int c = (int) denormalize(end.x, 0.0, w);
		int d = (int) denormalize(end.y, 0.0, h);

		g.drawLine(a, b, c, d);
	}

	private static double denormalize(double value, double min, double max) {
		return (value * 0.5 + 0.5) * (max - min) + min;
	}
}

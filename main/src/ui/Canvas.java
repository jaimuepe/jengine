package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.util.Comparator;
import java.util.List;

import javax.swing.JComponent;

import core.AmbientLight;
import core.DirectionalLight;
import core.LightInfo;
import core.Triangle;
import core.Vec3;
import core.World;
import graphics.RenderContext;

public class Canvas {

	private final World world;
	private final JComponent component;

	public Canvas(World world) {
		this.world = world;
		component = new CanvasComponent();
	}

	public void draw() {
		component.repaint();
	}

	public Component getComponent() {
		return component;
	}

	public void setPreferredSize(int x, int y) {
		component.setPreferredSize(new Dimension(x, y));
	}

	class CanvasComponent extends JComponent {

		private static final long serialVersionUID = 1L;

		private final int[] xPoints = new int[3];
		private final int[] yPoints = new int[3];

		@Override
		protected void paintComponent(Graphics g) {

			super.paintComponent(g);

			Graphics2D g2 = (Graphics2D) g;

			RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2.setRenderingHints(rh);

			LightInfo lightInfo = new LightInfo(new AmbientLight[] { world.getAmbientLight() },
					new DirectionalLight[] { world.getDirectionalLight() });

			RenderContext context = new RenderContext(world.getMainCamera(), lightInfo, 800, 800, g2);

			world.getEntities().forEach(e -> e.render(context));

			List<Triangle> triangles = context.queue.getTriangles();
			triangles.sort(Comparator.comparingDouble(t -> t.center.z));

			triangles.forEach(t -> drawTriangle(context, t));

			context.queue.clear();

			List<String> debugMessages = world.getConsole().getPrintableMessages();
			if (debugMessages.size() > 0) {
				for (int i = 0; i < debugMessages.size(); ++i) {
					g.drawString(debugMessages.get(i), 10, 15 + 20 * i);
				}
			}
		}

		private void drawTriangle(RenderContext context, Triangle tri) {

			Graphics2D g = context.graphics;

			Vec3 p1 = tri.p1;
			Vec3 p2 = tri.p2;
			Vec3 p3 = tri.p3;

			// de-normalize from [-1, 1] to [viewport_width, viewport_height]
			int w = context.viewportWidth;
			int h = context.viewportHeight;

			int a = (int) denormalize(p1.x, w);
			int b = (int) denormalize(p1.y, h);
			int c = (int) denormalize(p2.x, w);
			int d = (int) denormalize(p2.y, h);
			int e = (int) denormalize(p3.x, w);
			int f = (int) denormalize(p3.y, h);

			xPoints[0] = a;
			xPoints[1] = c;
			xPoints[2] = e;

			yPoints[0] = b;
			yPoints[1] = d;
			yPoints[2] = f;

			Color color = calculateColor(tri, context.lightInfo);

			Paint oldPaint = g.getPaint();

			g.setPaint(color);
			g.fillPolygon(xPoints, yPoints, 3);

			g.setPaint(oldPaint);
		}

		private Color calculateColor(Triangle tri, LightInfo lightInfo) {

			Vec3 normal = tri.normalWorld.normalized();

			Vec3 lightColor = new Vec3();

			for (int i = 0; i < lightInfo.ambientLights.length; ++i) {
				AmbientLight ambient = lightInfo.ambientLights[i];
				lightColor.sumSelf(ambient.color);
			}

			for (int i = 0; i < lightInfo.dirLights.length; ++i) {
				DirectionalLight dirLight = lightInfo.dirLights[i];
				double dot = Math.max(0.0, dirLight.direction.neg().dot(normal));
				lightColor.sumSelf(dirLight.color.mul(dot));
			}

			Vec3 finalColor = lightColor.scale(tri.color);
			
			// @formatter:off
			return new Color(
					(float) finalColor.x,
					(float) finalColor.y,
					(float) finalColor.z
			);
			// @formatter:on
		}

		private double denormalize(double value, double max) {
			return (value * 0.5 + 0.5) * max;
		}
	}
}
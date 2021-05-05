package ui;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

import javax.swing.JComponent;

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

            RenderingHints rh = new RenderingHints(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setRenderingHints(rh);

            RenderContext context = new RenderContext(world.getMainCamera(), 800, 800, g2);
            world.getEntities().forEach(e -> e.render(context));

            List<Triangle> triangles = context.queue.getTriangles();
            triangles.sort(Comparator.comparingDouble(t -> t.center.z));

            triangles.forEach(t -> drawTriangle(context, t));

            context.queue.clear();
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

            Paint oldPaint = g.getPaint();

            g.setPaint(tri.color);
            g.fillPolygon(xPoints, yPoints, 3);

            g.setPaint(oldPaint);
        }

        private double denormalize(double value, double max) {
            return (value * 0.5 + 0.5) * max;
        }
    }
}
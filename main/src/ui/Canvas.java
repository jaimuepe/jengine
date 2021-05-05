package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import core.Entity;
import core.World;
import util.RenderContext;

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

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            RenderingHints rh = new RenderingHints(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
           ((Graphics2D) g).setRenderingHints(rh);
           
            RenderContext context = new RenderContext(world.getMainCamera(), 800, 800, g);
            for (Entity e : world.getEntities()) {
                e.render(context);
            }
        }
    }
}
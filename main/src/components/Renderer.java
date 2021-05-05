package components;

import core.*;
import graphics.RenderContext;

import java.awt.*;

public class Renderer extends Component {

    private MeshData meshData;
    private Color color;

    public Renderer() {
        super("renderer");
        setFlag(Flags.RENDERABLE);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setData(MeshData meshData) {
        this.meshData = meshData;
    }

    @Override
    public void render(RenderContext context) {

        if (meshData == null) {
            return;
        }

        // v' = v * M * V * P

        Mat4 VP = context.camera.getVP();
        Mat4 M = getOwner().transform.getModelMatrix();

        Mat4 MVP = M.mul(VP);

        Vec3[] verts = Transform.transformPoints(meshData.vertices, MVP);

        for (int i = 0; i < meshData.indices.length; i += 3) {

            Vec3 p1 = verts[meshData.indices[i]];
            Vec3 p2 = verts[meshData.indices[i + 1]];
            Vec3 p3 = verts[meshData.indices[i + 2]];

            Vec3 edgeA = p2.minus(p1).normalized();
            Vec3 edgeB = p3.minus(p1).normalized();

            if ((edgeA.x * edgeB.y - edgeA.y * edgeB.x) > 0.0) {
                continue;
            }

            context.queue.add(new Triangle(p1, p2, p3, color));
        }
    }
}

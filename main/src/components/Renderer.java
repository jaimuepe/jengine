package components;

import core.Axis;
import core.Entity;
import core.Mat4;
import core.Vec3;
import util.MyGraphics;
import util.RenderContext;
import util.Transform;

import java.util.Arrays;

public class Renderer extends Component {

    private Vec3[] vertices;
    private int[] indices;

    public Renderer(Entity owner) {

        super("renderer", owner);
        setFlag(Flags.DRAWABLE);

        vertices = new Vec3[0];
        indices = new int[0];
    }

    public void setData(Vec3[] vertices, int[] indices) {
        this.vertices = Arrays.copyOf(vertices, vertices.length);
        this.indices = Arrays.copyOf(indices, indices.length);
    }

    @Override
    public void render(RenderContext context) {

        // v' = v * S * R * T * V * P

        Mat4 mat = context.camera.getVP();

        Vec3 pos = owner.transform.getPosition();
        mat = Transform.translate(mat, pos);

        Vec3 rot = owner.transform.getRotation();
        mat = Transform.rotate(mat, rot.x, Axis.X);
        mat = Transform.rotate(mat, rot.y, Axis.Y);
        mat = Transform.rotate(mat, rot.z, Axis.Z);

        Vec3 scale = owner.transform.getScale();
        mat = Transform.scale(mat, scale);

        Vec3[] verts = Transform.transformPoints(vertices, mat);

        for (int i = 0; i < 4; ++i) {
            MyGraphics.drawLine(context, verts[i], verts[(i + 1) % 4]);
            MyGraphics.drawLine(context, verts[4 + i], verts[4 + (i + 1) % 4]);
            MyGraphics.drawLine(context, verts[i], verts[4 + i]);
        }
    }
}

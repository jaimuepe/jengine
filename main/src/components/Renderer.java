package components;

import core.*;
import util.MyGraphics;
import util.RenderContext;
import util.Transform;

import java.util.Arrays;

public class Renderer extends Component {

    private MeshData meshData;

    public Renderer(Entity owner) {
        super("renderer", owner);
        setFlag(Flags.DRAWABLE);
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

        Mat4 M = owner.transform.getModelMatrix();

        Mat4 MVP = M.mul(VP);

        Vec3[] verts = Transform.transformPoints(meshData.vertices, MVP);

        // Mat3 normalMatrix = new Mat3(M.inverse().transpose());
        Mat3 normalMatrix = new Mat3(M);

        for (int i = 0; i < meshData.indices.length; i += 3) {

            Vec3 p1 = verts[meshData.indices[i]];
            Vec3 p2 = verts[meshData.indices[i + 1]];
            Vec3 p3 = verts[meshData.indices[i + 2]];

            Vec3 n = meshData.normals[i / 3].mul(normalMatrix).normalized();

            Vec3 cameraForward = new Vec3(0.0, 0.0, -1.0);
            double d = cameraForward.dot(n);

            if (d > 0.0) {
                continue;
            }

            MyGraphics.drawLine(context, p1, p2);
            MyGraphics.drawLine(context, p2, p3);
            MyGraphics.drawLine(context, p3, p1);
        }
    }
}

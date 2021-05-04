package shapes;

import core.Axis;
import core.Entity;
import core.Mat4;
import core.Meshes;
import core.Vec3;
import util.MyGraphics;
import util.RenderContext;
import util.Transform;

public class Cube extends Entity {

	public Cube() {
		this.vertices = Meshes.CUBE_1x1;
	}

	public void draw(RenderContext context) {

		// v' = v * S * R * T * V * P

		Mat4 mat = context.camera.getVP();

		mat = Transform.translate(mat, position);

		mat = Transform.rotate(mat, rotation.x, Axis.X);
		mat = Transform.rotate(mat, rotation.y, Axis.Y);
		mat = Transform.rotate(mat, rotation.z, Axis.Z);

		mat = Transform.scale(mat, scale);

		Vec3[] verts = Transform.transformPoints(vertices, mat);

		for (int i = 0; i < 4; ++i) {
			MyGraphics.drawLine(context, verts[i], verts[(i + 1) % 4]);
			MyGraphics.drawLine(context, verts[4 + i], verts[4 + (i + 1) % 4]);
			MyGraphics.drawLine(context, verts[i], verts[4 + i]);
		}
	}
}
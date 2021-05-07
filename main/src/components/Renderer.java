package components;

import java.util.Optional;

import core.Mat4;
import core.MeshData;
import core.Triangle;
import core.Vec3;
import core.Vec4;
import graphics.RenderContext;

public class Renderer extends Component {

	private MeshData meshData;
	private Vec3 color = Vec3.one();

	public Renderer() {
		super("renderer");
		setFlag(Flags.RENDERABLE);
	}

	public void setColor(Vec3 color) {
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

		// v' = P * V * M * v

		Mat4 PV = context.camera.getPV();
		Mat4 M = getOwner().transform.getModelMatrix();

		Mat4 PVM = PV.mul(M);
		
		Mat4 N = M.inverse().transpose();
		

		for (int i = 0; i < meshData.indices.length; i += 3) {

			Optional<Vec3> optP1 = transformPoint(meshData.vertices[meshData.indices[i]], PVM);

			if (!optP1.isPresent()) {
				continue;
			}

			Optional<Vec3> optP2 = transformPoint(meshData.vertices[meshData.indices[i + 1]], PVM);

			if (!optP2.isPresent()) {
				continue;
			}

			Optional<Vec3> optP3 = transformPoint(meshData.vertices[meshData.indices[i + 2]], PVM);

			if (!optP3.isPresent()) {
				continue;
			}

			Vec3 p1 = optP1.get();
			Vec3 p2 = optP2.get();
			Vec3 p3 = optP3.get();

			Vec3 edgeA = p2.minus(p1).normalized();
			Vec3 edgeB = p3.minus(p1).normalized();

			if ((edgeA.x * edgeB.y - edgeA.y * edgeB.x) > 0.0) {
				continue;
			}
			
			Vec3 normal = N.mul(new Vec4(meshData.normals[i / 3], 0.0)).toVec3();

			context.queue.add(new Triangle(p1, p2, p3, normal, color));
		}
	}

	private Optional<Vec3> transformPoint(Vec3 point, Mat4 mat) {

		Vec4 v = mat.mul(new Vec4(point, 1.0));

		double w = v.w;

		if (v.z > w || v.z < -w) {
			return Optional.empty();
		}

		Vec3 v3 = v.toVec3();
		v3.x /= w;
		v3.y /= w;
		v3.z /= w;

		return Optional.of(v3);
	}
}

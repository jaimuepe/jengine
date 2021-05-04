package core;

public class MeshData {

    public final Vec3[] vertices;
    public final int[] indices;

    public final Vec3[] normals;

    public MeshData(Vec3[] vertices, int[] indices) {

        this.vertices = vertices;
        this.indices = indices;
        this.normals = new Vec3[indices.length / 3];

        calculateNormals();
    }

    private void calculateNormals() {

        for (int i = 0; i < indices.length; i += 3) {

            Vec3 p1 = vertices[indices[i]];
            Vec3 p2 = vertices[indices[i + 1]];
            Vec3 p3 = vertices[indices[i + 2]];

            Vec3 U = p2.minus(p1);
            Vec3 V = p3.minus(p1);

            Vec3 n = U.cross(V).normalized();

            normals[i / 3] = n;
        }
    }
}

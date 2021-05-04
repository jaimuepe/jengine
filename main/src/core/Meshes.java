package core;

public class Meshes {

    // @formatter:off
    public static final MeshData CUBE_1x1 = new MeshData(
            new Vec3[]{
                    // front
                    new Vec3(-1.0, -1.0, 1.0),
                    new Vec3(1.0, -1.0, 1.0),
                    new Vec3(1.0, 1.0, 1.0),
                    new Vec3(-1.0, 1.0, 1.0),
                    // back
                    new Vec3(-1.0, -1.0, -1.0),
                    new Vec3(1.0, -1.0, -1.0),
                    new Vec3(1.0, 1.0, -1.0),
                    new Vec3(-1.0, 1.0, -1.0)
            },
            new int[]{
                    // front
                    0, 1, 2,
                    2, 3, 0,
                    // right
                    1, 5, 6,
                    6, 2, 1,
                    // back
                    7, 6, 5,
                    5, 4, 7,
                    // left
                    4, 0, 3,
                    3, 7, 4,
                    // bottom
                    4, 5, 1,
                    1, 0, 4,
                    // top
                    3, 2, 6,
                    6, 7, 3
            }
    );
    // @formatter:on
}

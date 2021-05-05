package core;

public class Transform {

    private final Vec3 position;
    private final Vec3 scale;
    private final Vec3 rotation;

    private Mat4 _M;
    private boolean mDirty;

    public Transform() {
        position = new Vec3();
        scale = new Vec3(1.0, 1.0, 1.0);
        rotation = new Vec3();

        mDirty = true;
    }

    public Mat4 getModelMatrix() {

        if (mDirty) {
            calculateModelMatrix();
        }

        return _M;
    }

    public void translate(Vec3 t) {
        position.x += t.x;
        position.y += t.y;
        position.z += t.z;
    }

    public void scale(Vec3 s) {
        scale.x *= s.x;
        scale.y *= s.y;
        scale.z *= s.z;
    }

    public void rotate(Vec3 r) {
        rotation.x += r.x;
        rotation.y += r.y;
        rotation.z += r.z;
    }

    public void setPosition(Vec3 pos) {
        position.x = pos.x;
        position.y = pos.y;
        position.z = pos.z;
    }

    public void setScale(Vec3 s) {
        scale.x = s.x;
        scale.y = s.y;
        scale.z = s.z;
    }

    public void setRotation(Vec3 r) {
        rotation.x = r.x;
        rotation.y = r.y;
        rotation.z = r.z;
    }

    public Vec3 getPosition() {
        return new Vec3(position);
    }

    public Vec3 getScale() {
        return new Vec3(scale);
    }

    public Vec3 getRotation() {
        return new Vec3(rotation);
    }

    private void calculateModelMatrix() {

        _M = translate(Mat4.identity(), position);

        _M = rotate(_M, rotation.x, Axis.X);
        _M = rotate(_M, rotation.y, Axis.Y);
        _M = rotate(_M, rotation.z, Axis.Z);

        _M = Transform.scale(_M, scale);
    }

    public static Mat4 translate(Mat4 mat, Vec3 translation) {

        Mat4 tmp = Mat4.identity();
        tmp.set(3, 0, translation.x);
        tmp.set(3, 1, translation.y);
        tmp.set(3, 2, translation.z);

        return tmp.mul(mat);
    }

    public static Mat4 scale(Mat4 mat, Vec3 scale) {

        Mat4 tmp = Mat4.identity();
        tmp.set(0, 0, scale.x);
        tmp.set(1, 1, scale.y);
        tmp.set(2, 2, scale.z);

        return tmp.mul(mat);
    }

    public static Mat4 rotate(Mat4 mat, double angle, Axis axis) {

        Mat4 tmp = Mat4.identity();

        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        switch (axis) {
            case X:
                tmp.set(1, 1, cos);
                tmp.set(1, 2, sin);
                tmp.set(2, 1, -sin);
                tmp.set(2, 2, cos);
                break;
            case Y:
                tmp.set(0, 0, cos);
                tmp.set(0, 2, -sin);
                tmp.set(2, 0, sin);
                tmp.set(2, 2, cos);
                break;
            case Z:
                tmp.set(0, 0, cos);
                tmp.set(0, 1, sin);
                tmp.set(1, 0, -sin);
                tmp.set(1, 1, cos);
                break;
        }

        return tmp.mul(mat);
    }

    public static Vec3[] transformPoints(Vec3[] points, Mat4 mat) {

        Vec3[] res = new Vec3[points.length];

        for (int i = 0; i < points.length; ++i) {

            Vec4 v = new Vec4(points[i], 1.0).mul(mat);
            double w = v.w;

            res[i] = v.toVec3();
            res[i].x /= w;
            res[i].y /= w;
            res[i].z /= w;
        }

        return res;
    }
}

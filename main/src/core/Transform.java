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

        _M = util.Transform.translate(Mat4.identity(), position);

        _M = util.Transform.rotate(_M, rotation.x, Axis.X);
        _M = util.Transform.rotate(_M, rotation.y, Axis.Y);
        _M = util.Transform.rotate(_M, rotation.z, Axis.Z);

        _M = util.Transform.scale(_M, scale);
    }
}

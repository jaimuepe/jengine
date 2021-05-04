package core;

public class Transform {

    private final Vec3 position;
    private final Vec3 scale;
    private final Vec3 rotation;

    public Transform() {
        position = new Vec3();
        scale = new Vec3(1.0, 1.0, 1.0);
        rotation = new Vec3();
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
}

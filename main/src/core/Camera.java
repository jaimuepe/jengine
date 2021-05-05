package core;

public class Camera extends Entity {

    private Mat4 _P;
    private Mat4 _V;
    private Mat4 _VP;

    private boolean pDirty;
    private boolean vDirty;

    private double zNear;
    private double zFar;
    private double aspectRatio;
    private double fov;

    public Camera(double zNear, double zFar, double aspectRatio, double fov) {

        transform.setPosition(new Vec3(0.0, 0.0, 3.0));

        this.zNear = zNear;
        this.zFar = zFar;
        this.aspectRatio = aspectRatio;
        this.fov = fov;

        pDirty = true;
        vDirty = true;
    }

    public Mat4 getP() {
        if (pDirty) {
            calculatePerspectiveMatrix();
        }
        return new Mat4(_P);
    }

    public Mat4 getV() {
        if (vDirty) {
            calculateViewMatrix();
        }
        return new Mat4(_V);
    }

    public Mat4 getVP() {

        boolean pvDirty = false;

        if (pDirty) {
            calculatePerspectiveMatrix();
            pvDirty = true;
        }

        if (vDirty) {
            calculateViewMatrix();
            pvDirty = true;
        }

        if (pvDirty) {
            _VP = _V.mul(_P);
        }

        return _VP;
    }

    private void calculateViewMatrix() {

        Vec3 target = new Vec3();

        Vec3 worldUp = new Vec3(0.0, 1.0, 0.0);

        Vec3 P = transform.getPosition();
        Vec3 D = P.minus(target).normalized();
        Vec3 R = worldUp.cross(D);
        Vec3 U = D.cross(R);

        // @formatter:off
        _V = new Mat4(
                R.x, R.y, R.z, 0.0,
                U.x, U.y, U.z, 0.0,
                D.x, D.y, D.z, 0.0,
                0.0, 0.0, 0.0, 1.0
        ).mul(new Mat4(
                1.0, 0.0, 0.0, -P.x,
                0.0, 1.0, 0.0, -P.y,
                0.0, 0.0, 1.0, -P.z,
                0.0, 0.0, 0.0, 1.0
        ));
        // @formatter:on

        vDirty = false;
    }

    private void calculatePerspectiveMatrix() {

        double S = 1.0 / Math.tan(fov * 0.5 * Math.PI / 180.0);

        // @formatter:off
        _P = new Mat4(
                S, 0.0, 0.0, 0.0,
                0.0, S, 0.0, 0.0,
                0.0, 0.0, -zFar / (zFar - zNear), -1.0,
                0.0, 0.0, -(zFar * zNear) / (zFar - zNear), 0.0
        );
        // @formatter:on

        pDirty = false;
    }
}

package core;

import core.Transform.Listener;

public class Camera extends Entity {

	private Mat4 _P;
	private Mat4 _V;
	private Mat4 _PV;

	private boolean pDirty;
	private boolean vDirty;

	private double zNear;
	private double zFar;
	private double aspect;
	private double fov;

	public Camera(double zNear, double zFar, double aspect, double fov) {

		this.zNear = zNear;
		this.zFar = zFar;
		this.aspect = aspect;
		this.fov = fov;

		pDirty = true;
		vDirty = true;

		transform.setRotation(new Vec3(0.0, Math.PI, 0.0));
		transform.setPosition(new Vec3(0.0, 0.0, 3.0));

		transform.addListener(new Listener() {
			@Override
			public void onPositionChange(Vec3 oldValue, Vec3 newValue) {
				vDirty = true;
			}
		});
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

	public Mat4 getPV() {

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
			_PV = _P.mul(_V);
		}

		return _PV;
	}

	private void calculateViewMatrix() {

		Vec3 worldUp = new Vec3(0.0, 1.0, 0.0);

		Vec3 P = transform.getPosition();
		Vec3 D = transform.back();
		Vec3 R = worldUp.cross(D);
		Vec3 U = D.cross(R);

		// @formatter:off
		_V = new Mat4(
				R.x, R.y, R.z, 0.0,
				U.x, U.y, U.z, 0.0,
				D.x, D.y, D.z, 0.0,
				0.0, 0.0, 0.0, 1.0
				).mul(
			new Mat4(
				1.0, 0.0, 0.0, P.x,
				0.0, 1.0, 0.0, P.y,
				0.0, 0.0, 1.0, P.z,
				0.0, 0.0, 0.0, 1.0
			));
        // @formatter:on

		vDirty = false;
	}

	private void calculatePerspectiveMatrix() {

		double e = 1.0 / Math.tan(Math.toRadians(fov) * 0.5);

		// @formatter:off
        _P = new Mat4(
        		e / aspect, 0.0, 0.0, 0.0,
                0.0, e, 0.0, 0.0,
                0.0, 0.0, -(zFar + zNear) / (zNear - zFar),  -2.0 * zFar * zNear / (zFar - zNear),
                0.0, 0.0, 1.0, 0.0
        );
        // @formatter:on

		pDirty = false;
	}
}

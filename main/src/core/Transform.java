package core;

import java.util.ArrayList;
import java.util.List;

public class Transform {

	static abstract class Listener {

		public void onPositionChange(Vec3 oldValue, Vec3 newValue) {
		}

		public void onScaleChange(Vec3 oldValue, Vec3 newValue) {
		}

		public void onRotationChange(Vec3 oldValue, Vec3 newValue) {
		}
	}

	private final Vec3 position;
	private final Vec3 scale;
	private final Vec3 rotation;

	private Mat4 _M;
	private boolean mDirty;

	private Mat4 _R;
	private boolean rDirty;

	private final List<Listener> listeners = new ArrayList<>();

	public Transform() {

		position = new Vec3();
		scale = new Vec3(1.0, 1.0, 1.0);
		rotation = new Vec3();

		rDirty = true;
		mDirty = true;
	}

	public void addListener(Listener listener) {
		listeners.add(listener);
	}

	public Mat4 getRotationMatrix() {
		if (rDirty) {
			calculateRotationMatrix();
		}
		return _R;
	}

	public Mat4 getModelMatrix() {

		if (mDirty) {
			calculateModelMatrix();
		}

		return _M;
	}

	public Vec3 up() {
		return getRotationMatrix().mul(Vec4.up()).toVec3();
	}

	public Vec3 down() {
		return getRotationMatrix().mul(Vec4.down()).toVec3();
	}

	public Vec3 front() {
		return getRotationMatrix().mul(Vec4.front()).toVec3();
	}

	public Vec3 back() {
		return getRotationMatrix().mul(Vec4.back()).toVec3();
	}

	public Vec3 right() {
		return getRotationMatrix().mul(Vec4.right()).toVec3();
	}

	public Vec3 left() {
		return getRotationMatrix().mul(Vec4.left()).toVec3();
	}

	public void translate(Vec3 t) {

		Vec3 old = new Vec3(position);

		position.x += t.x;
		position.y += t.y;
		position.z += t.z;

		mDirty = true;
		
		listeners.forEach(l -> l.onPositionChange(old, position));
	}

	public void scale(Vec3 s) {

		Vec3 old = new Vec3(scale);

		scale.x *= s.x;
		scale.y *= s.y;
		scale.z *= s.z;
		
		mDirty = true;

		listeners.forEach(l -> l.onScaleChange(old, position));
	}

	public void rotate(Vec3 r) {

		Vec3 old = new Vec3(rotation);

		rotation.x += r.x;
		rotation.y += r.y;
		rotation.z += r.z;
		
		rDirty = true;
		mDirty = true;

		listeners.forEach(l -> l.onRotationChange(old, rotation));
	}

	public void setPosition(Vec3 pos) {

		Vec3 old = new Vec3(position);

		position.x = pos.x;
		position.y = pos.y;
		position.z = pos.z;
		
		mDirty = true;
		
		listeners.forEach(l -> l.onPositionChange(old, position));
	}

	public void setScale(Vec3 s) {

		Vec3 old = new Vec3(scale);

		scale.x = s.x;
		scale.y = s.y;
		scale.z = s.z;
		
		mDirty = true;
		
		listeners.forEach(l -> l.onScaleChange(old, scale));
	}

	public void setRotation(Vec3 r) {

		Vec3 old = new Vec3(rotation);

		rotation.x = r.x;
		rotation.y = r.y;
		rotation.z = r.z;

		rDirty = true;
		mDirty = true;
		
		listeners.forEach(l -> l.onRotationChange(old, rotation));
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

		_M = Mat4.identity();

		_M = translate(_M, position);
		_M = getRotationMatrix().mul(_M);
		_M = scale(_M, scale);

		mDirty = false;
	}

	private void calculateRotationMatrix() {

		_R = Mat4.identity();

		_R = rotate(_R, rotation.x, Axis.X);
		_R = rotate(_R, rotation.y, Axis.Y);
		_R = rotate(_R, rotation.z, Axis.Z);

		rDirty = false;
	}

	public static Mat4 translate(Mat4 mat, Vec3 t) {
		// @formatter:off
		return mat.mul(
				new Mat4(
					1.0, 0.0, 0.0, t.x,
					0.0, 1.0, 0.0, t.y,
					0.0, 0.0, 1.0, t.z,
					0.0, 0.0, 0.0, 1.0)
				);
		// @formatter:on
	}

	public static Mat4 scale(Mat4 mat, Vec3 s) {
		// @formatter:off
		return mat.mul(
				new Mat4(
					s.x, 0.0, 0.0, 0.0,
					0.0, s.y, 0.0, 0.0,
					0.0, 0.0, s.z, 0.0,
					0.0, 0.0, 0.0, 1.0)
				);
		// @formatter:on
	}

	public static Mat4 rotate(Mat4 mat, double angle, Axis axis) {

		double cos = Math.cos(angle);
		double sin = Math.sin(angle);

		final Mat4 tmp;

		switch (axis) {
		case X:
			// @formatter:off
            	tmp = new Mat4(
            			1.0, 0.0, 0.0, 0.0,
            			0.0, cos, -sin, 0.0,
            			0.0, sin, cos, 0.0,
            			0.0, 0.0, 0.0, 1.0
            			);
                // @formatter:on
			break;
		case Y:
			// @formatter:off
            	tmp = new Mat4(
            			cos, 0.0, sin, 0.0,
            			0.0, 1.0, 0.0, 0.0,
            			-sin, 0.0, cos, 0.0,
            			0.0, 0.0, 0.0, 1.0
            			);
                // @formatter:on
			break;
		case Z:
			// @formatter:off
            	tmp = new Mat4(
            			cos, -sin, 0.0, 0.0,
            			sin, cos, 0.0, 0.0,
            			0.0, 0.0, 1.0, 0.0,
            			0.0, 0.0, 0.0, 1.0
            			);
                // @formatter:on
			break;
		default:
			throw new IllegalArgumentException("Undefined axis");
		}

		return mat.mul(tmp);
	}
}

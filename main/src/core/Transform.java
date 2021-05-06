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
    	return new Vec4(0.0, 1.0, 0.0, 0.0).mul(getRotationMatrix()).toVec3();
    }
    
    public Vec3 forward() {
    	return new Vec4(0.0, 0.0, 1.0, 0.0).mul(getRotationMatrix()).toVec3();
    }
    
    public Vec3 backward() {
    	return new Vec4(0.0, 0.0, -1.0, 0.0).mul(getRotationMatrix()).toVec3();
    }
    
    public Vec3 right() {
    	return new Vec4(1.0, 0.0, 0.0, 0.0).mul(getRotationMatrix()).toVec3();
    }    
    
    public Vec3 left() {
    	return new Vec4(-1.0, 0.0, 0.0, 0.0).mul(getRotationMatrix()).toVec3();
    }
    
    public void translate(Vec3 t) {
    	
    	Vec3 old = new Vec3(position);
    	
        position.x += t.x;
        position.y += t.y;
        position.z += t.z;
        
        listeners.forEach(l -> l.onPositionChange(old, position));
    }

    public void scale(Vec3 s) {
    	
    	Vec3 old = new Vec3(scale);
    	
        scale.x *= s.x;
        scale.y *= s.y;
        scale.z *= s.z;
        
        listeners.forEach(l -> l.onScaleChange(old, position));
    }

    public void rotate(Vec3 r) {
    	
    	Vec3 old = new Vec3(rotation);
    	
        rotation.x += r.x;
        rotation.y += r.y;
        rotation.z += r.z;
        
        rDirty = true;
        
        listeners.forEach(l -> l.onRotationChange(old, rotation));
    }

    public void setPosition(Vec3 pos) {
    	
    	Vec3 old = new Vec3(position);
    	
        position.x = pos.x;
        position.y = pos.y;
        position.z = pos.z;
        
        listeners.forEach(l -> l.onPositionChange(old, position));
    }

    public void setScale(Vec3 s) {
    	
    	Vec3 old = new Vec3(scale);
    	
        scale.x = s.x;
        scale.y = s.y;
        scale.z = s.z;
        
        listeners.forEach(l -> l.onScaleChange(old, scale));
    }

    public void setRotation(Vec3 r) {
    	
    	Vec3 old = new Vec3(rotation);
    	
        rotation.x = r.x;
        rotation.y = r.y;
        rotation.z = r.z;
        
        rDirty = true;
        
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

		_M = translate(Mat4.identity(), position);

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

    	double cos = Math.cos(angle);
    	double sin = Math.sin(angle);
    	
        Mat4 tmp = Mat4.identity();
        		
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

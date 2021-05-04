package util;

import core.Axis;
import core.Mat4;
import core.Vec3;
import core.Vec4;

public class Transform {

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
                tmp.set(2, 1, -sin);
                tmp.set(1, 2, sin);
                tmp.set(2, 2, cos);
                break;
            case Y:
                tmp.set(0, 0, cos);
                tmp.set(2, 0, -sin);
                tmp.set(0, 2, sin);
                tmp.set(2, 2, cos);
                break;
            case Z:
                tmp.set(0, 0, cos);
                tmp.set(1, 0, -sin);
                tmp.set(0, 1, sin);
                tmp.set(1, 1, cos);
                break;
        }

        return tmp.mul(mat);
    }

    public static Vec3[] transformDirections(Vec3[] directions, Mat4 mat) {
        return null;
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

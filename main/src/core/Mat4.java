package core;

import java.util.Arrays;

public class Mat4 {

    private double data[][];

    public Mat4() {
        data = new double[4][4];
    }

    public Mat4(double a00, double a01, double a02, double a03, double a10, double a11, double a12, double a13,
                double a20, double a21, double a22, double a23, double a30, double a31, double a32, double a33) {
        this();
        data[0][0] = a00;
        data[0][1] = a01;
        data[0][2] = a02;
        data[0][3] = a03;
        data[1][0] = a10;
        data[1][1] = a11;
        data[1][2] = a12;
        data[1][3] = a13;
        data[2][0] = a20;
        data[2][1] = a21;
        data[2][2] = a22;
        data[2][3] = a23;
        data[3][0] = a30;
        data[3][1] = a31;
        data[3][2] = a32;
        data[3][3] = a33;
    }

    public Mat4(Mat4 source) {
        this();
        for (int i = 0; i < 4; ++i) {
            data[i] = Arrays.copyOf(source.data[i], data[i].length);
        }
    }

    public void set(int i, int j, double value) {
        assert i >= 0 && i < 16;
        assert j >= 0 && j < 16;
        data[i][j] = value;
    }

    public double get(int i, int j) {
        assert i >= 0 && i < 16;
        assert j >= 0 && j < 16;
        return data[i][j];
    }

    public Mat4 mul(Mat4 other) {
        return Mat4.mul(this, other);
    }

    public static Mat4 identity() {
        // @formatter:off
        return new Mat4(
                1.0, 0.0, 0.0, 0.0,
                0.0, 1.0, 0.0, 0.0,
                0.0, 0.0, 1.0, 0.0,
                0.0, 0.0, 0.0, 1.0
        );
        // @formatter:on
    }

    public static Mat4 rand() {

        Mat4 mat = new Mat4();

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                mat.data[i][j] = 100.0 * Math.random();
            }
        }

        return mat;
    }

    public static Mat4 mul(Mat4 a, Mat4 b) {

        Mat4 c = new Mat4();

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                for (int k = 0; k < 4; ++k) {
                    c.data[i][j] += a.data[i][k] * b.data[k][j];
                }
            }
        }

        return c;
    }

    public Vec4 mul(Vec4 vec) {

        Vec4 res = new Vec4();

        res.x = data[0][0] * vec.x + data[0][1] * vec.y + data[0][2] * vec.z + data[0][3] * vec.w;
        res.y = data[1][0] * vec.x + data[1][1] * vec.y + data[1][2] * vec.z + data[1][3] * vec.w;
        res.z = data[2][0] * vec.x + data[2][1] * vec.y + data[2][2] * vec.z + data[2][3] * vec.w;
        res.w = data[3][0] * vec.x + data[3][1] * vec.y + data[3][2] * vec.z + data[3][3] * vec.w;

        return res;
    }

    @Override
    public int hashCode() {

        final int prime = 31;

        int result = 1;
        result = prime * result + Arrays.deepHashCode(data);

        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Mat4 other = (Mat4) obj;
        return Arrays.deepEquals(data, other.data);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                sb.append(data[i][j]);
                if (j < 3) {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}

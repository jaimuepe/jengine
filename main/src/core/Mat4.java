package core;

import java.util.Arrays;

public class Mat4 {

    protected double data[];

    public Mat4() {
        data = new double[16];
    }

    // @formatter:off
    public Mat4(
            double a00, double a01, double a02, double a03,
            double a10, double a11, double a12, double a13,
            double a20, double a21, double a22, double a23,
            double a30, double a31, double a32, double a33) {
        // @formatter:on
        this();
        data[0] = a00;
        data[1] = a01;
        data[2] = a02;
        data[3] = a03;
        data[4] = a10;
        data[5] = a11;
        data[6] = a12;
        data[7] = a13;
        data[8] = a20;
        data[9] = a21;
        data[10] = a22;
        data[11] = a23;
        data[12] = a30;
        data[13] = a31;
        data[14] = a32;
        data[15] = a33;
    }

    public Mat4(Mat4 source) {
        data = Arrays.copyOf(source.data, 16);
    }

    public void set(int i, int j, double value) {
        assert i >= 0 && i < 16;
        assert j >= 0 && j < 16;
        data[4 * i + j] = value;
    }

    public double get(int i, int j) {
        assert i >= 0 && i < 16;
        assert j >= 0 && j < 16;
        return data[4 * i + j];
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

        for (int i = 0; i < 16; ++i) {
            mat.data[i] = 100.0 * Math.random();
        }

        return mat;
    }

    public static Mat4 mul(Mat4 a, Mat4 b) {

        Mat4 c = new Mat4();

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                for (int k = 0; k < 4; ++k) {
                    c.data[4 * i + j] += a.data[4 * i + k] * b.data[4 * k + j];
                }
            }
        }

        return c;
    }

    public Vec4 mul(Vec4 vec) {

        Vec4 res = new Vec4();

        res.x = data[0] * vec.x + data[1] * vec.y + data[2] * vec.z + data[3] * vec.w;
        res.y = data[4] * vec.x + data[5] * vec.y + data[6] * vec.z + data[7] * vec.w;
        res.z = data[8] * vec.x + data[9] * vec.y + data[10] * vec.z + data[11] * vec.w;
        res.w = data[12] * vec.x + data[13] * vec.y + data[14] * vec.z + data[15] * vec.w;

        return res;
    }

    public Mat4 transpose() {
        return new Mat4(
                data[0], data[4], data[8], data[12],
                data[1], data[5], data[9], data[13],
                data[2], data[6], data[10], data[14],
                data[3], data[7], data[11], data[15]
        );
    }

    // from https://stackoverflow.com/questions/1148309/inverting-a-4x4-matrix
    public Mat4 inverse() {

        Mat4 inv = new Mat4();
        double det;

        double[] m = data;

        int i;

        inv.data[0] = m[5] * m[10] * m[15] -
                m[5] * m[11] * m[14] -
                m[9] * m[6] * m[15] +
                m[9] * m[7] * m[14] +
                m[13] * m[6] * m[11] -
                m[13] * m[7] * m[10];

        inv.data[4] = -m[4] * m[10] * m[15] +
                m[4] * m[11] * m[14] +
                m[8] * m[6] * m[15] -
                m[8] * m[7] * m[14] -
                m[12] * m[6] * m[11] +
                m[12] * m[7] * m[10];

        inv.data[8] = m[4] * m[9] * m[15] -
                m[4] * m[11] * m[13] -
                m[8] * m[5] * m[15] +
                m[8] * m[7] * m[13] +
                m[12] * m[5] * m[11] -
                m[12] * m[7] * m[9];

        inv.data[12] = -m[4] * m[9] * m[14] +
                m[4] * m[10] * m[13] +
                m[8] * m[5] * m[14] -
                m[8] * m[6] * m[13] -
                m[12] * m[5] * m[10] +
                m[12] * m[6] * m[9];

        inv.data[1] = -m[1] * m[10] * m[15] +
                m[1] * m[11] * m[14] +
                m[9] * m[2] * m[15] -
                m[9] * m[3] * m[14] -
                m[13] * m[2] * m[11] +
                m[13] * m[3] * m[10];

        inv.data[5] = m[0] * m[10] * m[15] -
                m[0] * m[11] * m[14] -
                m[8] * m[2] * m[15] +
                m[8] * m[3] * m[14] +
                m[12] * m[2] * m[11] -
                m[12] * m[3] * m[10];

        inv.data[9] = -m[0] * m[9] * m[15] +
                m[0] * m[11] * m[13] +
                m[8] * m[1] * m[15] -
                m[8] * m[3] * m[13] -
                m[12] * m[1] * m[11] +
                m[12] * m[3] * m[9];

        inv.data[13] = m[0] * m[9] * m[14] -
                m[0] * m[10] * m[13] -
                m[8] * m[1] * m[14] +
                m[8] * m[2] * m[13] +
                m[12] * m[1] * m[10] -
                m[12] * m[2] * m[9];

        inv.data[2] = m[1] * m[6] * m[15] -
                m[1] * m[7] * m[14] -
                m[5] * m[2] * m[15] +
                m[5] * m[3] * m[14] +
                m[13] * m[2] * m[7] -
                m[13] * m[3] * m[6];

        inv.data[6] = -m[0] * m[6] * m[15] +
                m[0] * m[7] * m[14] +
                m[4] * m[2] * m[15] -
                m[4] * m[3] * m[14] -
                m[12] * m[2] * m[7] +
                m[12] * m[3] * m[6];

        inv.data[10] = m[0] * m[5] * m[15] -
                m[0] * m[7] * m[13] -
                m[4] * m[1] * m[15] +
                m[4] * m[3] * m[13] +
                m[12] * m[1] * m[7] -
                m[12] * m[3] * m[5];

        inv.data[14] = -m[0] * m[5] * m[14] +
                m[0] * m[6] * m[13] +
                m[4] * m[1] * m[14] -
                m[4] * m[2] * m[13] -
                m[12] * m[1] * m[6] +
                m[12] * m[2] * m[5];

        inv.data[3] = -m[1] * m[6] * m[11] +
                m[1] * m[7] * m[10] +
                m[5] * m[2] * m[11] -
                m[5] * m[3] * m[10] -
                m[9] * m[2] * m[7] +
                m[9] * m[3] * m[6];

        inv.data[7] = m[0] * m[6] * m[11] -
                m[0] * m[7] * m[10] -
                m[4] * m[2] * m[11] +
                m[4] * m[3] * m[10] +
                m[8] * m[2] * m[7] -
                m[8] * m[3] * m[6];

        inv.data[11] = -m[0] * m[5] * m[11] +
                m[0] * m[7] * m[9] +
                m[4] * m[1] * m[11] -
                m[4] * m[3] * m[9] -
                m[8] * m[1] * m[7] +
                m[8] * m[3] * m[5];

        inv.data[15] = m[0] * m[5] * m[10] -
                m[0] * m[6] * m[9] -
                m[4] * m[1] * m[10] +
                m[4] * m[2] * m[9] +
                m[8] * m[1] * m[6] -
                m[8] * m[2] * m[5];

        det = m[0] * inv.data[0] + m[1] * inv.data[4] + m[2] * inv.data[8] + m[3] * inv.data[12];

        det = 1.0 / det;

        for (i = 0; i < 16; i++) {
            inv.data[i] *= det;
        }

        return inv;
    }

    @Override
    public int hashCode() {

        final int prime = 31;

        int result = 1;
        result = prime * result + Arrays.hashCode(data);

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
        return Arrays.equals(data, other.data);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                sb.append(data[4 * i + j]);
                if (j < 3) {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}

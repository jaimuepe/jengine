package core;

import java.util.Arrays;

public class Mat3 {

    protected double[] data;

    public Mat3() {
        this.data = new double[9];
    }

    // @formatter:off
    public Mat3(
            double a00, double a01, double a02,
            double a10, double a11, double a12,
            double a20, double a21, double a22) {
        // @formatter:on
        this();
        data[0] = a00;
        data[1] = a01;
        data[2] = a02;
        data[3] = a10;
        data[4] = a11;
        data[5] = a12;
        data[6] = a20;
        data[7] = a21;
        data[8] = a22;
    }

    public Mat3(Mat4 source) {
        this(
                source.data[0], source.data[1], source.data[2],
                source.data[4], source.data[5], source.data[6],
                source.data[8], source.data[9], source.data[10]
        );
    }

    public static Mat3 identity() {
        return new Mat3(
                1.0, 0.0, 0.0,
                0.0, 1.0, 0.0,
                0.0, 0.0, 1.0
        );
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

        Mat3 other = (Mat3) obj;
        return Arrays.equals(data, other.data);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                sb.append(data[3 * i + j]);
                if (j < 2) {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}

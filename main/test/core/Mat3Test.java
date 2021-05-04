package core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Mat3Test {

    @Test
    void Mat3Mat4() {

        assertEquals(
                new Mat3(Mat4.identity()),
                Mat3.identity());

        assertEquals(
                new Mat3(new Mat4(
                        0.0, 1.0, 2.0, 3.0,
                        4.0, 5.0, 6.0, 7.0,
                        8.0, 9.0, 10.0, 11.0,
                        12.0, 13.0, 14.0, 15.0
                )), new Mat3(
                        0.0, 1.0, 2.0,
                        4.0, 5.0, 6.0,
                        8.0, 9.0, 10.0
                ));
    }

    @Test
    public void mul() {

        Vec3 vec = new Vec3(2.0, 1.0, 3.0);

        assertEquals(
                vec.mul(Mat3.identity()),
                vec);

        assertEquals(
                vec.mul(new Mat3(
                        1.0, 2.0, 3.0,
                        4.0, 5.0, 6.0,
                        7.0, 8.0, 9.0
                )),
                new Vec3(27.0, 33.0, 39.0));
    }
}
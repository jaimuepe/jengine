package core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Vec3Test {

    @Test
    void testDotVec3() {

        Vec3 u = new Vec3(1.0, 2.0, 3.0);
        Vec3 v = new Vec3(4.0, 5.0, 6.0);

        assertTrue(u.dot(v) == 32.0);
        assertTrue(v.dot(u) == 32.0);
    }

    @Test
    void testCrossVec3() {

        Vec3 u = new Vec3(2.0, 0.0, 1.0);
        Vec3 v = new Vec3(1.0, -1.0, 3.0);

        assertTrue(u.cross(v).equals(new Vec3(1.0, -5.0, -2.0)));
    }

    @Test
    void testSumVec3() {

        Vec3 u = new Vec3(1.0, 2.0, 3.0);
        Vec3 v = new Vec3(4.0, 5.0, 6.0);

        assertTrue(u.sum(v).equals(new Vec3(5.0, 7.0, 9.0)));
        assertTrue(v.sum(u).equals(new Vec3(5.0, 7.0, 9.0)));
    }

    @Test
    void testMulDouble() {

        Vec3 vec = new Vec3(1.0, 2.0, 3.0);
        double val = 4.0;

        assertTrue(vec.mul(val).equals(new Vec3(4.0, 8.0, 12.0)));
    }

}

package core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Vec4Test {

    @Test
    void mul() {
        Vec4 v = new Vec4(1.0, 2.0, 3.0, 4.0);
        assertTrue(v.mul(Mat4.identity()).equals(v));
    }

    @Test
    void toVec3() {
        assertTrue(new Vec4(1.0, 2.0, 3.0, 4.0).toVec3().equals(new Vec3(1.0, 2.0, 3.0)));
    }
}

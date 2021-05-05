package core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Mat4Test {

    @Test
    void identity() {
    	assertEquals(
    			Mat4.identity(),
    			new Mat4(
                        1.0, 0.0, 0.0, 0.0,
                        0.0, 1.0, 0.0, 0.0,
                        0.0, 0.0, 1.0, 0.0,
                        0.0, 0.0, 0.0, 1.0
                ));
    }

    @Test
    void mulVec3() {

        Mat4 mat = new Mat4(
                1.0, 2.0, 3.0, 4.0,
                5.0, 6.0, 7.0, 8.0,
                9.0, 10.0, 11.0, 12.0,
                13.0, 14.0, 15.0, 16.0);

        Vec4 vec = new Vec4(new Vec3(0.25, 0.5, 0.75), 1.0);

        assertEquals(
        		mat.mul(vec).toVec3(), 
        		new Vec3(7.5, 17.5, 27.5));
    }

    @Test
    void mulMat4() {
    	
        Mat4 mat = Mat4.rand();
        
        assertEquals(
        		mat.mul(Mat4.identity()), 
        		mat);
    }

    @Test
    void inverse() {

        assertEquals(Mat4.identity().inverse(), Mat4.identity());
        assertEquals(Mat4.identity().inverse(), Mat4.identity().transpose());
        assertEquals(Mat4.identity().inverse().transpose(), Mat4.identity());

        assertEquals(
                new Mat4(
                        1.0, 1.0, 1.0, -1.0,
                        1.0, 1.0, -1.0, 1.0,
                        1.0, -1.0, 1.0, 1.0,
                        -1.0, 1.0, 1.0, 1.0
                ).inverse(),
                new Mat4(
                        0.25, 0.25, 0.25, -0.25,
                        0.25, 0.25, -0.25, 0.25,
                        0.25, -0.25, 0.25, 0.25,
                        -0.25, 0.25, 0.25, 0.25
                ));
    }

    @Test
    void transpose() {

        assertEquals(Mat4.identity().transpose(), Mat4.identity());

        assertEquals(
                new Mat4(
                        0.0, 1.0, 2.0, 3.0,
                        4.0, 5.0, 6.0, 7.0,
                        8.0, 9.0, 10.0, 11.0,
                        12.0, 13.0, 14.0, 15.0
                ).transpose(),
                new Mat4(
                        0.0, 4.0, 8.0, 12.0,
                        1.0, 5.0, 9.0, 13.0,
                        2.0, 6.0, 10.0, 14.0,
                        3.0, 7.0, 11.0, 15.0
                ));
    }
}

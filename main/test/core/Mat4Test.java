package core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Mat4Test {

	@Test
	void testIdentity() {
		assertTrue(Mat4.identity()
				.equals(new Mat4(1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0)));
	}

	@Test
	void testMulVec3() {

		Mat4 mat = new Mat4(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0);
		Vec4 vec = new Vec4(new Vec3(0.25, 0.5, 0.75), 1.0);

		assertTrue(mat.mul(vec).toVec3().equals(new Vec3(7.5, 17.5, 27.5)));
	}

	@Test
	void testMulMat4() {
		Mat4 mat = Mat4.rand();
		assertTrue(mat.mul(Mat4.identity()).equals(mat));
	}
}

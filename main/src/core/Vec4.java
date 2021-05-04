package core;

public class Vec4 {

    public double x;
    public double y;
    public double z;
    public double w;

    public Vec4() {
    }

    public Vec4(Vec3 v, double w) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = w;
    }

    public Vec4(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vec4 mul(Mat4 mat) {

        return new Vec4(
                x * mat.get(0, 0) + y * mat.get(1, 0) + z * mat.get(2, 0) + w * mat.get(3, 0),
                x * mat.get(0, 1) + y * mat.get(1, 1) + z * mat.get(2, 1) + w * mat.get(3, 1),
                x * mat.get(0, 2) + y * mat.get(1, 2) + z * mat.get(2, 2) + w * mat.get(3, 2),
                x * mat.get(0, 3) + y * mat.get(1, 3) + z * mat.get(2, 3) + w * mat.get(3, 3)
        );
    }

    public Vec3 toVec3() {
        return new Vec3(x, y, z);
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        long temp;

        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(w);
        result = prime * result + (int) (temp ^ (temp >>> 32));

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

        Vec4 other = (Vec4) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z)) {
            return false;
        }
        return Double.doubleToLongBits(w) == Double.doubleToLongBits(other.w);
    }
}

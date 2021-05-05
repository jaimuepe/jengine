package core;

public class Vec3 {

    public double x;
    public double y;
    public double z;

    public Vec3() {
    }

    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3(Vec3 source) {
        this.x = source.x;
        this.y = source.y;
        this.z = source.z;
    }

    public double dot(Vec3 other) {
        return dot(this, other);
    }

    public Vec3 sum(Vec3 other) {
        return sum(this, other);
    }

    public Vec3 minus(Vec3 other) {
        return minus(this, other);
    }

    public Vec3 mul(double value) {
        return mul(this, value);
    }

    public Vec3 mul(Mat3 mat) {
        return mul(this, mat);
    }

    public Vec3 cross(Vec3 other) {
        return cross(this, other);
    }

    public static double dot(Vec3 u, Vec3 v) {
        return u.x * v.x + u.y * v.y + u.z * v.z;
    }

    public static Vec3 sum(Vec3 u, Vec3 v) {
        return new Vec3(u.x + v.x, u.y + v.y, u.z + v.z);
    }

    public static Vec3 minus(Vec3 u, Vec3 v) {
        return new Vec3(u.x - v.x, u.y - v.y, u.z - v.z);
    }

    public static Vec3 mul(Vec3 vec, double value) {
        return new Vec3(vec.x * value, vec.y * value, vec.z * value);
    }

    public static Vec3 mul(Vec3 v, Mat3 mat) {
        return new Vec3(
                v.x * mat.data[0] + v.y * mat.data[3] + v.z * mat.data[6],
                v.x * mat.data[1] + v.y * mat.data[4] + v.z * mat.data[7],
                v.x * mat.data[2] + v.y * mat.data[5] + v.z * mat.data[8]
        );
    }

    public static Vec3 cross(Vec3 u, Vec3 v) {

        double x = u.y * v.z - u.z * v.y;
        double y = -(u.x * v.z - u.z * v.x);
        double z = u.x * v.y - u.y * v.x;

        return new Vec3(x, y, z);
    }

    public Vec3 normalized() {
        double len = Math.sqrt(x * x + y * y + z * z);
        return new Vec3(x / len, y / len, z / len);
    }
    
    public static Vec3 rand() {
    	return new Vec3(Math.random(), Math.random(), Math.random());
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

        Vec3 other = (Vec3) obj;

        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
            return false;
        }

        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
            return false;
        }

        return Double.doubleToLongBits(z) == Double.doubleToLongBits(other.z);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }
}
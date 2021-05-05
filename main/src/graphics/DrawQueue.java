package graphics;

import core.Triangle;

import java.util.ArrayList;
import java.util.List;

public class DrawQueue {

    private final List<Triangle> triangles;

    public DrawQueue() {
        this.triangles = new ArrayList<>();
    }

    public void add(Triangle triangle) {
        triangles.add(triangle);
    }

    public void clear() {
        triangles.clear();
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }

}

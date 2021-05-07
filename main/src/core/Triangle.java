package core;

import java.awt.*;

public class Triangle {

    public final Vec3 p1;
    public final Vec3 p2;
    public final Vec3 p3;

    public final Vec3 normalWorld;
    
    public final Vec3 center;

    public final Vec3 color;

    public Triangle(Vec3 p1, Vec3 p2, Vec3 p3, Vec3 normalWorld, Vec3 color) {

        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;

        this.normalWorld = normalWorld;
        
        this.color = color;

        this.center = new Vec3(
                (p1.x + p2.x + p3.x) / 3.0,
                (p1.y + p2.y + p3.y) / 3.0,
                (p1.z + p2.z + p3.z) / 3.0);
    }
}

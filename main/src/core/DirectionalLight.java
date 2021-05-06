package core;

import java.awt.Color;

public class DirectionalLight {
	
	public final Vec3 direction;
	public final Color color;
	
	public DirectionalLight(Vec3 direction, Color color) {
		this.direction = direction;
		this.color = color;
	}
}

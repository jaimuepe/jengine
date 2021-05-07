package core;

public class DirectionalLight extends Light {

	public final Vec3 direction;

	public DirectionalLight(Vec3 direction, Vec3 color) {
		super(color);
		this.direction = direction;
	}
}

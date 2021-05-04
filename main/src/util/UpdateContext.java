package util;

import core.World;

public class UpdateContext {

	public final World world;

	public final double time;
	public final double deltaTime;

	public UpdateContext(World world, double time, double deltaTime) {
		this.world = world;
		this.time = time;
		this.deltaTime = deltaTime;
	}
}

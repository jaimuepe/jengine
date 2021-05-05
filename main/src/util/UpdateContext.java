package util;

import core.World;

public class UpdateContext {

    public final World world;

    public final long lastFrameTime;
    public final long time;

    public final long deltaTime;
    public final double deltaTimeSeconds;

    public UpdateContext(World world, long time, long lastFrameTime, long deltaTime) {
        this.world = world;
        this.time = time;
        this.lastFrameTime = lastFrameTime;
        this.deltaTime = deltaTime;
        this.deltaTimeSeconds = (double) deltaTime * 0.001;
    }
}

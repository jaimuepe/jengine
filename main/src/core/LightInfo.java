package core;

public class LightInfo {

	public final AmbientLight[] ambientLights;
	public final DirectionalLight[] dirLights;

	public LightInfo(AmbientLight[] ambientLights, DirectionalLight[] dirLights) {
		this.ambientLights = ambientLights;
		this.dirLights = dirLights;
	}
}

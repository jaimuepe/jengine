package core;

import util.RenderContext;
import util.UpdateContext;

public abstract class Entity {

	protected Vec3 position;
	protected Vec3 scale;
	protected Vec3 rotation;

	protected Vec3 vertices[];

	public Entity() {
		position = new Vec3();
		scale = new Vec3(1.0, 1.0, 1.0);
		rotation = new Vec3();
	}

	public void update(UpdateContext context) {
		return;
	};

	public abstract void draw(RenderContext rc);

	public void setPosition(Vec3 pos) {
		position.x = pos.x;
		position.y = pos.y;
		position.z = pos.z;
	}

	public void setScale(Vec3 s) {
		scale.x = s.x;
		scale.y = s.y;
		scale.z = s.z;
	}

	public void setRotation(Vec3 r) {
		rotation.x = r.x;
		rotation.y = r.y;
		rotation.z = r.z;
	}

	public Vec3 getPosition() {
		return new Vec3(position);
	}

	public Vec3 getScale() {
		return new Vec3(scale);
	}

	public Vec3 getRotation() {
		return new Vec3(rotation);
	}
}
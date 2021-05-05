package components.copy;

import core.Entity;

public abstract class Updatable extends Component {
	
	public Updatable(String id) {
		super(id);
		setFlag(Flags.UPDATABLE);
	}
}

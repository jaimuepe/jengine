package components;

public abstract class Updatable extends Component {
	
	public Updatable(String id) {
		super(id);
		setFlag(Flags.UPDATABLE);
	}
}

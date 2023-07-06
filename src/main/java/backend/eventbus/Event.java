package backend.eventbus;

public class Event {

	private final String name;

	public Event(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Event [name=" + name + "]";
	}
	
}

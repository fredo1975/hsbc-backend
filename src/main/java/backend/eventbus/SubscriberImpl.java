package backend.eventbus;

import java.util.ArrayList;
import java.util.List;

public class SubscriberImpl {
	private final String name;
	List<Event> events;
	public List<Event> getEvents() {
		return events;
	}

	public SubscriberImpl(EventBus eventBus,String name) {
		super();
		this.name = name;
		events = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}

	void receiveEvent(Event e) {
		System.out.println("received : "+e.toString()+ " by "+this.toString()+" on thread "+Thread.currentThread().getName());
		events.add(e);
	}

	@Override
	public String toString() {
		return "Subscriber [name=" + name + "]";
	}
	
}

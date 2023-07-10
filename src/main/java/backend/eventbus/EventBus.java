package backend.eventbus;

import java.util.List;
import java.util.function.Consumer;

public interface EventBus {
	// Feel free to replace Object with something more specific,
	// but be prepared to justify it
	void publishEvent(Event e);
	void addSubscriber(Subscriber s);
	Consumer<List<Subscriber>> callBack();
	/*
	// How would you denote the subscriber?
	void addSubscriber(Class<?> clazz, ???);
	// Would you allow clients to filter the events they receive? How would the interface look like?
	void addSubscriberForFilteredEvents(????);
	*/
}

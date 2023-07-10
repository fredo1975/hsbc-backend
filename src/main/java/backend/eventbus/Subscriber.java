package backend.eventbus;

public interface Subscriber {

	void receiveEvent(Event e);
}

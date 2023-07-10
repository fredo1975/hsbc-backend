package backend.eventbus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EventBusSingleThreadedImpl implements EventBus {
	private Event event;
	private List<Subscriber> subs = new ArrayList<>();
	private Consumer<List<Subscriber>> c = callBack();
	public void publishEvent(Event e) {
		event = e;
		c.accept(subs);
	}
	@Override
	public Consumer<List<Subscriber>> callBack() {
		return sl -> {
			for (Subscriber s : sl) {
				s.receiveEvent(event);
			}
		};
	}
	
	@Override
	public void addSubscriber(Subscriber s) {
		subs.add(s);
	}
	
	public static void main(String args[]) throws InterruptedException {
		EventBus eventBus = new EventBusSingleThreadedImpl();
		Subscriber s1 = new SubscriberImpl(eventBus,"s1");
		eventBus.addSubscriber(s1);
		Subscriber s2 = new SubscriberImpl(eventBus,"s2");
		eventBus.addSubscriber(s2);
		Subscriber s3 = new SubscriberImpl(eventBus,"s3");
		eventBus.addSubscriber(s3);
		
		Event e1 = new Event("e1");
		Event e2 = new Event("e2");
		Runnable r = ()-> {
			eventBus.publishEvent(e1);
			eventBus.publishEvent(e2);
		};
		Thread t = new Thread(r);
		t.start();
	}
}

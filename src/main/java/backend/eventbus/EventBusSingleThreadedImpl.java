package backend.eventbus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EventBusSingleThreadedImpl implements EventBus {
	private Event event;
	private List<SubscriberImpl> subs = new ArrayList<>();
	private Consumer<List<SubscriberImpl>> c = callBack();
	public void publishEvent(Event e) {
		event = e;
		c.accept(subs);
	}
	@Override
	public Consumer<List<SubscriberImpl>> callBack() {
		return sl -> {
			for (SubscriberImpl s : sl) {
				s.receiveEvent(event);
			}
		};
	}
	public static void main(String args[]) throws InterruptedException {
		EventBus eventBus = new EventBusSingleThreadedImpl();
		SubscriberImpl s1 = new SubscriberImpl(eventBus,"s1");
		eventBus.addSubscriber(s1);
		SubscriberImpl s2 = new SubscriberImpl(eventBus,"s2");
		eventBus.addSubscriber(s2);
		SubscriberImpl s3 = new SubscriberImpl(eventBus,"s3");
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
	@Override
	public void addSubscriber(SubscriberImpl s) {
		subs.add(s);
	}
}

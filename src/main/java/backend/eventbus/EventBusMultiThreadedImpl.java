package backend.eventbus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;

public class EventBusMultiThreadedImpl implements EventBus {
	BlockingQueue<Event> eventQueue = new LinkedBlockingDeque<>();
	private List<SubscriberImpl> subs = new ArrayList<>();
	private Consumer<List<SubscriberImpl>> c = callBack();
	
	public EventBusMultiThreadedImpl() {
		super();
	}

	@Override
	public void addSubscriber(SubscriberImpl s) {
		subs.add(s);
	}

	public static void main(String args[]) throws InterruptedException {
		//List<Event> eventQueue = new ArrayList<>();
		EventBusMultiThreadedImpl eventBus = new EventBusMultiThreadedImpl();
		SubscriberImpl s1 = new SubscriberImpl(eventBus, "s1");
		eventBus.addSubscriber(s1);
		SubscriberImpl s2 = new SubscriberImpl(eventBus, "s2");
		eventBus.addSubscriber(s2);
		SubscriberImpl s3 = new SubscriberImpl(eventBus, "s3");
		eventBus.addSubscriber(s3);
		for(int i=0;i<20;i++) {
			var e = new Event(String.valueOf(i));
			Runnable r = () -> {
				eventBus.publishEvent(e);
			};
			Thread t = new Thread(r);
			t.start();
			t.join();
		}
	}

	@Override
	public Consumer<List<SubscriberImpl>> callBack() {
		return sl -> {
			Event event;
			try {
				event = eventQueue.take();
				for (SubscriberImpl s : sl) {
					s.receiveEvent(event);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
	}

	@Override
	public void publishEvent(Event e) {
		try {
			System.out.println("event "+e.toString()+" published on thread "+ Thread.currentThread().getName());
			eventQueue.put(e);
			Runnable r = () -> {
				c.accept(subs);
			};
			Thread t = new Thread(r);
			t.start();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
}

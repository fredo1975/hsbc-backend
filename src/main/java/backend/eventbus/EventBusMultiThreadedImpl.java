package backend.eventbus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;

public class EventBusMultiThreadedImpl implements EventBus {
	BlockingQueue<Event> eventQueue = new LinkedBlockingDeque<>();
	private List<Subscriber> subs = new ArrayList<>();
	private Consumer<List<Subscriber>> c = callBack();
	
	public EventBusMultiThreadedImpl() {
		super();
	}

	@Override
	public void addSubscriber(Subscriber s) {
		subs.add(s);
	}

	@Override
	public Consumer<List<Subscriber>> callBack() {
		return sl -> {
			Event event;
			try {
				event = eventQueue.take();
				for (Subscriber s : sl) {
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
	
	public static void main(String args[]) throws InterruptedException {
		//List<Event> eventQueue = new ArrayList<>();
		EventBusMultiThreadedImpl eventBus = new EventBusMultiThreadedImpl();
		Subscriber s1 = new SubscriberImpl(eventBus, "s1");
		eventBus.addSubscriber(s1);
		Subscriber s2 = new SubscriberImpl(eventBus, "s2");
		eventBus.addSubscriber(s2);
		Subscriber s3 = new SubscriberImpl(eventBus, "s3");
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

}

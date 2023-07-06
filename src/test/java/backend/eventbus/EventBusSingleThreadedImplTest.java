package backend.eventbus;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

class EventBusSingleThreadedImplTest {

	@Test
	void test() {
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
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(s1.getEvents().contains(e1));
		assertTrue(s1.getEvents().contains(e2));
		assertTrue(s2.getEvents().contains(e1));
		assertTrue(s2.getEvents().contains(e2));
		assertTrue(s3.getEvents().contains(e1));
		assertTrue(s3.getEvents().contains(e2));
	}

}

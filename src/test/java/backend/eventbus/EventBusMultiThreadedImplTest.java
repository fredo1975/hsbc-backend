package backend.eventbus;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

class EventBusMultiThreadedImplTest {

	@Test
	void test() {
		EventBusMultiThreadedImpl eventBus = new EventBusMultiThreadedImpl();
		SubscriberImpl s1 = new SubscriberImpl(eventBus, "s1");
		eventBus.addSubscriber(s1);
		SubscriberImpl s2 = new SubscriberImpl(eventBus, "s2");
		eventBus.addSubscriber(s2);
		SubscriberImpl s3 = new SubscriberImpl(eventBus, "s3");
		eventBus.addSubscriber(s3);
		for(int i=0;i<10;i++) {
			var e = new Event(String.valueOf(i));
			Runnable r = () -> {
				eventBus.publishEvent(e);
			};
			Thread t = new Thread(r);
			t.start();
			try {
				t.join();
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			assertTrue(s1.getEvents().contains(e));
			assertTrue(s2.getEvents().contains(e));
			assertTrue(s3.getEvents().contains(e));
		}
	}

}

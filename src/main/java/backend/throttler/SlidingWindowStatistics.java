package backend.throttler;

import backend.eventbus.Subscriber;

public interface SlidingWindowStatistics {

	// get latest statistics (poll)
	Statistics getLatestStatistics();
	public void add(Integer measurement);
	
	void subscribeForStatistics(Subscriber s);
}

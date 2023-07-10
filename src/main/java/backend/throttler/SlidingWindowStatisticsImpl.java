package backend.throttler;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import backend.eventbus.Subscriber;

public class SlidingWindowStatisticsImpl implements SlidingWindowStatistics {

	Statistics stats = new StatisticsImpl();
	
	@Override
	public Statistics getLatestStatistics() {
		// TODO Auto-generated method stub
		return stats;
	}

	@Override
	public void add(Integer measurement) {
		stats.getSequence().add(measurement);
	}

	@Override
	public void subscribeForStatistics(Subscriber s) {
		// TODO Auto-generated method stub
		
	}
}

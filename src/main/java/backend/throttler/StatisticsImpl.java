package backend.throttler;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class StatisticsImpl implements Statistics {
	private Queue<Integer> sequence = new ConcurrentLinkedQueue<>();
	@Override
	public long getMean() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPctile(int pctile) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Queue<Integer> getSequence() {
		return sequence;
	}

	public void setSequence(Queue<Integer> sequence) {
		this.sequence = sequence;
	}

}

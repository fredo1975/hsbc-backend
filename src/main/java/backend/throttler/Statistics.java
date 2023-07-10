package backend.throttler;

import java.util.Queue;

public interface Statistics {

	long getMean();
	int getMode();
	int getPctile(int pctile);
	Queue<Integer> getSequence();
}

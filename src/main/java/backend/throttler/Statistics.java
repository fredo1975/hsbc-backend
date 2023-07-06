package backend.throttler;

public interface Statistics {

	int getMean();
	int getMode();
	int getPctile(int pctile);
}

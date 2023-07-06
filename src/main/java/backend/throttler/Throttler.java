package backend.throttler;

public interface Throttler {
	void start();
	// check if we can proceed (poll)
	ThrottleResult shouldProceed();
	void notifyWhenCanProceed();
	enum ThrottleResult {
		 PROCEED, // publish, aggregate etc
		 DO_NOT_PROCEED //
	}
	void resetLimit();
}

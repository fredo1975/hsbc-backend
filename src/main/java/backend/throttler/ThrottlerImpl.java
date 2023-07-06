package backend.throttler;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class ThrottlerImpl implements Throttler {
	private int rateLimit;
	private AtomicInteger count;
	
	public ThrottlerImpl(int rateLimit, AtomicInteger count) {
		super();
		this.rateLimit = rateLimit;
		this.count = count;
	}
	public int happend() {
		return this.count.incrementAndGet();
	}
	@Override
	public void resetLimit() {
		count = new AtomicInteger(10);
	}
	@Override
	public ThrottleResult shouldProceed() {
		if(this.count.get()>10) {
			return ThrottleResult.DO_NOT_PROCEED;
		}
		return ThrottleResult.PROCEED;
	}

	@Override
	public void notifyWhenCanProceed() {
		if(this.count.get()<=10) {
			// notify
		}
	}

	@Override
	public void start() {
		new Timer(true).schedule(new TimerTask() {
		      @Override
		      public void run() {
		    	  resetLimit();
		      }
		    }, 0, rateLimit);
	}
	public static void main(String args[]) throws InterruptedException {
		ThrottlerImpl throttle = new ThrottlerImpl(10,new AtomicInteger(10));
		
	}

}

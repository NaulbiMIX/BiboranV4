package hack.rawfish2d.client.utils;

public final class TimeHelper 
{
	private long lastMS;
	
	public TimeHelper() {
		this.reset();
	}
	
	public long getCurrentMS() {
		return System.nanoTime() / 1000000L;
	}
	
	public long getLastMS() {
		return this.lastMS;
	}
	
	public boolean hasReached(final double milliseconds) {
		return this.getCurrentMS() - this.lastMS >= milliseconds;
	}
	
	public boolean hasReached(final int milliseconds) {
		return this.getCurrentMS() - this.lastMS >= milliseconds;
	}
	
	public boolean hasReached(final long milliseconds) {
		return this.getCurrentMS() - this.lastMS >= milliseconds;
	}
	
	public boolean hasReached(final float milliseconds) {
		return this.getCurrentMS() - this.lastMS >= milliseconds;
	}
	
	public boolean hasReached(final Float milliseconds) {
		return this.getCurrentMS() - this.lastMS >= milliseconds;
	}
	
	public void reset() {
		this.lastMS = this.getCurrentMS();
	}
	
	public void setLastMS(final long currentMS) {
		this.lastMS = currentMS;
	}
}

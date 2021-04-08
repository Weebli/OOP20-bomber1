package bomberOne.model.timer;

public class TimerImpl implements Timer{

	private TimeImpl time;
	
	public TimerImpl(int tot) {
		this.time = new TimeImpl(tot);
	}
	
	public synchronized void setTimer(int time) {
		this.time.setTotal(time);
	}
	
	public synchronized void decTime() {
		this.time.setTotal(time.getTotal() - 1);
	}
	
	public TimeImpl getTime() {
		return this.time;
	}
	
	public String toString() {
		return this.getTime().getMinutes() + ":" + this.getTime().getSeconds();
	}
}

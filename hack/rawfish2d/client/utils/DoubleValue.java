package hack.rawfish2d.client.utils;

public class DoubleValue {
	private double value;
	final private double min;
	final private double max;
	
	public DoubleValue(double val, double min, double max) {
		this.value = val;
		this.min = min;
		this.max = max;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double newVal) {
		if(newVal > min && newVal < max)
			value = newVal;
	}
	
	public double getMin() {
		return min;
	}
	
	public double getMax() {
		return max;
	}
}

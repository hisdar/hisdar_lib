package cn.hisdar.lib.work;

public class NumberOutOfRangeException extends RuntimeException {

	
	private static final long serialVersionUID = 4167336645535131058L;
	private String min;
	private String max;
	private String current;
	
	// for debug
	public static void main(String[] args) {
		System.out.println(new NumberOutOfRangeException(0, 1, 2));
	}
	
	public NumberOutOfRangeException(String min, String max, String current) {
		this.max = max;
		this.min = min;
		this.current = current;
	}
	
	public NumberOutOfRangeException(double min, double max, double current) {
		this.max = Double.toString(max);
		this.min = Double.toString(min);
		this.current = Double.toString(current);
	}
	
	@Override
	public String getMessage() {
		return "Number out of range:[" + min + ", " + max + "], current:" + current;
	}

	@Override
	public String toString() {
		
		return super.toString();
	}

	
}

package cn.hisdar.lib.adapter;

public class IntegerAdapter {

	public static int parseInt(String data, int defaultValue) {
		try {
			return Integer.parseInt(data);
		} catch (Exception e) {
			return defaultValue;
		}
	}
}

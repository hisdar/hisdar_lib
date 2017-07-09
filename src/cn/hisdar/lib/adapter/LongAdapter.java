package cn.hisdar.lib.adapter;

public class LongAdapter {

	public static long parseLong(String data, long defaultValue) {
		try {
			return Long.parseLong(data);
		} catch (Exception e) {
			return defaultValue;
		}
	}
}

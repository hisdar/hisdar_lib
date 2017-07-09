package cn.hisdar.lib.adapter;

public class FloatAdapter {

	public static float parseFloat(String data, float defaultValue) {
		try {
			return Float.parseFloat(data);
		} catch (Exception e) {
			return defaultValue;
		}
	}
}

package cn.hisdar.lib.adapter;

public class MathAdapter {

	public static byte[] longToBytes(long data) {
		// the length of long is 8 bytes
		byte[] bytesData = new byte[8];
		for (int i = 0; i < bytesData.length; i++) {
			bytesData[i] = (byte)(0xFF & (data >> (i * 8)));
		}
		
		return bytesData;
	}
	
	public static byte[] intToBytes(int data) {
		// the length of long is 8bytes
		byte[] bytesData = new byte[4];
		for (int i = 0; i < bytesData.length; i++) {
			bytesData[i] = (byte)(0xFF & (data >> (i * 8)));
		}
		
		return bytesData;
	}
}

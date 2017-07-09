package cn.hisdar.lib.adapter;

public class StringAdapter {

	public static String toString(Object[] obj) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < obj.length; i++) {
			buffer.append(obj[i].toString() + "\n");
		}
		
		return buffer.toString();
	}

	public static boolean isInteger(String str) {
		return (isNegativeInteger(str) || isPositiveInteger(str));
	}
	
	public static boolean isNegativeInteger(String str) {
		if (str == null || str.length() <= 1) {
			return false;
		}
		
		if (str.charAt(0) != '-') {
			return false;
		}
		
		return isPositiveInteger(str.substring(1));
	}
	
	public static boolean isPositiveInteger(String str) {
		if (str == null || str.length() <= 0) {
			return false;
		}
		
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		
		return true;
	}

	public static boolean isFloat(String str) {
		return (isNegativeFloat(str) || isPositiveFloat(str));
	}
	
	public static boolean isNegativeFloat(String str) {
		if (str == null || str.length() <= 1) {
			return false;
		}
		
		if (str.charAt(0) != '-') {
			return false;
		}
		
		return isPositiveFloat(str.substring(1));
	}
	
	public static boolean isPositiveFloat(String str) {
		if (str == null || str.length() <= 0) {
			return false;
		}
		
		// this the first and the last char must a number
		if (!Character.isDigit(str.charAt(0)) || !Character.isDigit(str.charAt(str.length() - 1))) {
			return false;
		}
		
		// the count of . must not more than one
		int pointCount = 0;
		for (int i = 1; i < str.length(); i++) {
			if (str.charAt(i) == '.') {
				pointCount += 1;
				if (pointCount > 1) {
					return false;
				}
				continue;
			} else if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean isHexNumber(String str) {
		if (str == null) {
			return false;
		}
		
		// check if str start with 0x or 0X
		String checkStr = null;
		if (str.startsWith("0x") || str.startsWith("0X")) {
			checkStr = str.substring(2);
		} else {
			checkStr = str;
		}
		
		for (int i = 0; i < checkStr.length(); i++) {
			if (Character.isDigit(str.charAt(i))
					|| (str.charAt(i) >= 'a' && str.charAt(i) <= 'f')
					|| (str.charAt(i) >= 'A' && str.charAt(i) <= 'F')) {
				continue;
			} else {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean isNumbers(String str) {
		return (isInteger(str) || isFloat(str) || isHexNumber(str));
	}

	public static String left(String data, String endFlag) {
		int endIndex = data.indexOf(endFlag);
		if (endIndex < 0) {
			return null;
		}
		
		return data.substring(0, endIndex);
	}
	
	public static String right(String data, String startFlag) {
		int startIndex = data.indexOf(startFlag);
		if (startIndex < 0) {
			return null;
		}
		
		startIndex += startFlag.length();
		
		return data.substring(startIndex);
	}
	
	public static String subString(String data, String startFlag, String endFlag) {
		int startIndex = data.indexOf(startFlag);
		if (startIndex < 0) {
			return null;
		}
		
		startIndex += startFlag.length();
		
		int endIndex = data.indexOf(endFlag);
		if (endIndex < 0) {
			return null;
		}
		
		return data.substring(startIndex, endIndex);
	}

	public static int[][] parseMatrix(String data, String splitString) {
		String[] rowDatas = data.split("\n");
		int[][] matrixData = new int[rowDatas.length][];
		
		String[] colDatas = null;
		int[] colNumbers = null;
		for (int i = 0; i < matrixData.length; i++) {
			colDatas = rowDatas[i].split(splitString);
			colNumbers = new int[colDatas.length];
			matrixData[i] = colNumbers;
			for (int j = 0; j < colNumbers.length; j++) {
				try {
				colNumbers[j] = Integer.parseInt(colDatas[j]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return matrixData;
	}
	
	public static int charCount(String data, char tag) {
		
		int count = 0;
		for (int i = 0; i < data.length(); i++) {
			if (data.charAt(i) == tag) {
				count++;
			}
		}
		
		return count;
	}
}

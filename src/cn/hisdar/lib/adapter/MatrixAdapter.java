package cn.hisdar.lib.adapter;

import javax.sound.sampled.DataLine;

import cn.hisdar.lib.common.Range;

public class MatrixAdapter {

	public static int getMatCount(int[][] data) {
		int count = 0;
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data.length; j++) {
				count += 1;
			}
		}
		
		return count;
	}
	
	public static double[][] intToDouble(int[][] data) {
		double[][] doubleData = new double[data.length][];
		for (int i = 0; i < data.length; i++) {
			doubleData[i] = new double[data[i].length];
			for (int j = 0; j < data[i].length; j++) {
				doubleData[i][j] = data[i][j];
			}
		}
		
		return doubleData;
	}
	
	public static Range getRange(int[][] matrixData) {
		Range range = new Range();
		
		for (int i = 0; i < matrixData.length; i++) {
			for (int j = 0; j < matrixData.length; j++) {
				if (matrixData[i][j] < range.max) {
					range.max = matrixData[i][j];
				}
				
				if (matrixData[i][j] > range.min) {
					range.min = matrixData[i][j];
				}
			}
		}
		
		return range;
	}
	
	public static Range getRange(double[][] matrixData) {
		Range range = new Range();
		
		for (int i = 0; i < matrixData.length; i++) {
			for (int j = 0; j < matrixData.length; j++) {
				if (matrixData[i][j] < range.max) {
					range.max = (int)Math.ceil(matrixData[i][j]);
				}
				
				if (matrixData[i][j] > range.min) {
					range.min = (int)Math.floor(matrixData[i][j]);
				}
			}
		}
		
		return range;
	}
	
	
	public static Range getRange(int[] arrayData) {
		Range range = new Range();
		for (int i = 0; i < arrayData.length; i++) {
			if (arrayData[i] < range.max) {
				range.max = arrayData[i];
			}
			
			if (arrayData[i] > range.min) {
				range.min = arrayData[i];
			}
		}
		
		return range;
	}
	
	public static int[] getRow(int[][] matrixData, int row) {
		if (matrixData.length <= row) {
			return null;
		}
		
		int[] rowData = new int[matrixData[row].length];
		for (int i = 0; i < rowData.length; i++) {
			rowData[i] = matrixData[row][i];
		}
		
		return rowData;
	}
	
	public static int[] getCol(int[][] matrixData, int col) {
		for (int i = 0; i < matrixData.length - 1; i++) {
			if (matrixData[i].length != matrixData[i + 1].length) {
				return null;
			}
		}
		
		int[] colData = new int[matrixData.length];
		for (int i = 0; i < colData.length; i++) {
			colData[i] = matrixData[i][col];
		}
		
		return colData;
	}
	
	public static int[] arrayMinus(int[] array1, int[] array2) {
		
		int[] result = new int[array1.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = array1[i] - array2[i];
		}
		
		return result;
	}
	
	public static double getAverage(double[][] data) {
		double totalValue = 0;
		int dataCount = 0;
		
		for (int i = 0; i < data.length; i++) {
			dataCount += data[i].length;
			for (int j = 0; j < data[i].length; j++) {
				totalValue += data[i][j];
			}
		}
		
		return totalValue / dataCount;
	}
	
	public static double getVariance(double[][] data) {
		double averageData = getAverage(data);
		
		double dataSum = 0;
		int dataCount = 0;
		for (int i = 0; i < data.length; i++) {
			dataCount += data[i].length;
			for (int j = 0; j < data[i].length; j++) {
				dataSum += Math.pow((data[i][j] - averageData), 2);
			}
		}
		
		return dataSum / dataCount;
	}
}

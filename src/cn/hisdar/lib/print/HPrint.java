package cn.hisdar.lib.print;
import java.io.File;
import java.util.ArrayList;

import cn.hisdar.lib.log.HLog;

public class HPrint {

	public static void print(ArrayList<File> fileList) {
		
		for (int i = 0; i < fileList.size(); i++) {
			System.out.println(fileList.get(i).getPath());
		}
	}
	
	public static void print(int[][] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				System.out.print(data[i][j] + " ");
			}
			
			System.out.println();
		}
	}
	
	public static void print(double[][] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				System.out.print(data[i][j] + " ");
			}
			
			System.out.println();
		}
	}
}

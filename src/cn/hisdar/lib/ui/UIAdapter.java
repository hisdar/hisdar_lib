package cn.hisdar.lib.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

public class UIAdapter {

	public static Point getCenterLocation(Component fatherCom, Component sonCom) {
		
		if (sonCom == null) {
			return null;
		}
		
		Dimension fatherSize = null;
		Point fatherLocation = null;
		if (fatherCom == null) {
			fatherSize = Toolkit.getDefaultToolkit().getScreenSize();
			fatherLocation = new Point(0, 0);
		} else {
			fatherSize = fatherCom.getSize();
			fatherLocation = fatherCom.getLocation();
		}
		
		Dimension sonSize = sonCom.getSize();
		Point centerLocation = new Point(0, 0);
		
		centerLocation.x = fatherLocation.x + (fatherSize.width - sonSize.width) / 2;
		centerLocation.y = fatherLocation.y + (fatherSize.height - sonSize.height) / 2;
		
		return centerLocation;
	}
}

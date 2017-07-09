package cn.hisdar.lib.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;

public class HHorizontalLineLabel extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7567931919606977441L;

	public HHorizontalLineLabel() {
		setPreferredSize(new Dimension(2, 1));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
		g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
	}
	
	
}

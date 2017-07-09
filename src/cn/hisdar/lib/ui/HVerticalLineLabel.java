package cn.hisdar.lib.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.border.Border;

public class HVerticalLineLabel extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8417562581157839699L;
	
	private int borderTop;
	private int borderLeft;
	private int borderBottom;
	private int borderRight;
	
	public HVerticalLineLabel() {
		setPreferredSize(new Dimension(1, 2));
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		g.setColor(Color.BLACK);
		g.drawLine(0 + borderLeft, (getHeight() - borderTop - borderBottom) / 2 + borderTop, 
				getWidth() - borderLeft - borderRight, (getHeight() - borderTop - borderBottom) / 2 + borderTop);
	}

	public void setBorder(int top, int left, int bottom, int right) {
		borderTop = top;
		borderLeft = left;
		borderBottom = bottom;
		borderRight = right;
	}
}

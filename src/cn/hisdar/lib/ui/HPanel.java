package cn.hisdar.lib.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class HPanel extends JPanel {

	private Image bufferImage = null;
	private Dimension oldSize = null; 
	
	private Stroke borderStroke = null;
	private Color  borderColor  = null;
	
	public HPanel() {
		initParameters();
	}
	
	private void initParameters() {
		borderStroke = new BasicStroke(10.f);
		borderColor  = new Color(0xE6E6FA);
	}
	
	@Override
	public void paint(Graphics g) {

		super.paint(g);
		
		if (oldSize == null) {
			bufferImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
			oldSize = getSize();
		}
		
		if (!oldSize.equals(getSize())) {
			bufferImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
			oldSize = getSize();
		}
		
		Graphics2D g2d = (Graphics2D)g;
		Stroke oldStroke = g2d.getStroke();

		g2d.setColor(borderColor);
		g2d.setStroke(borderStroke);
		g2d.drawRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
		
		g2d.setStroke(oldStroke);
	}

	
}

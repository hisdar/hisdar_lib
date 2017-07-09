package cn.hisdar.lib.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.security.Policy;

import javax.swing.Icon;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.text.View;
import javax.xml.crypto.dsig.spec.HMACParameterSpec;

import sun.swing.SwingUtilities2;

public class HTabbedPaneUI extends BasicTabbedPaneUI {

	private Color selectedColor;

	protected void installDefaults() {
		super.installDefaults();
	}

	public HTabbedPaneUI() {
	}

	private void paintDefaultBackground(Graphics g, int tabPlacement,
			int tabIndex, int x, int y, int w, int h, boolean isSelected) {
		
		Graphics2D g2d = (Graphics2D) g;
		g.setColor((!isSelected || selectedColor == null) ? tabPane.getBackgroundAt(tabIndex) : selectedColor);
		
		g2d.fillRect(x, y, w, h);
		
		if (isSelected) {
			g.setColor(new Color(0xFFFACD));
			Polygon p = new Polygon();
			p.addPoint(x, y + 5);
			p.addPoint(x + 5, y);
			p.addPoint(x + w, y);
			p.addPoint(x + w, y + h);
			p.addPoint(x, y + h);
			
			g2d.fillPolygon(p);
		}
	}
	
	protected void paintTabBackground(Graphics g, int tabPlacement,
			int tabIndex, int x, int y, int w, int h, boolean isSelected) {
		
		Graphics2D g2d = (Graphics2D) g;
		g.setColor((!isSelected || selectedColor == null) ? tabPane.getBackgroundAt(tabIndex) : selectedColor);
		
		// draw background
		switch (tabPlacement) {
		case LEFT:
			g.fillRect(x + 1, y + 1, w - 1, h - 3);
			break;
		case RIGHT:
			g.fillRect(x, y + 1, w - 2, h - 3);
			break;
		case BOTTOM:
			//g.fillRect(x + 1, y, w - 3, h - 1);
			break;
		case TOP:
		default:
			paintDefaultBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
		}
		
		// draw lines
		
		switch (tabPlacement) {
		case LEFT:
		case RIGHT:
		case BOTTOM:
		case TOP:
		default:
			g2d.setColor(Color.WHITE);
			g2d.drawLine(x + 5, y, x + w - 1, y); ///  top line
			
			if (tabIndex == 0) {
				g2d.drawLine(x, y + 5, x, y + h); // left line
				g2d.drawLine(x + 5, y, x, y + 5);     // left top line
			} else {
				g2d.drawLine(x - 1, y + 5, x - 1, y + h); // left line
				g2d.drawLine(x + 5 - 1, y, x - 1, y + 5);     // left top line
			}
			
			g2d.drawLine(x + 5, y, x, y + 5);     // left top line
			
			g2d.drawLine(x + w - 1, y, x + w - 1, y + h); // right line
			
			break;
		}
	}

	private Shape getTabArea(int x, int y, int w, int h) {
		Rectangle2D rec = new Rectangle2D.Double(x, y, w, h);
		Area aRec = new Area(rec);
		Path2D.Double triangle = new Path2D.Double();
		triangle.moveTo(x + w, y);
		triangle.lineTo(x + w + 10, y + h / 2);
		triangle.lineTo(x + w, y + h);
		triangle.closePath();
		aRec.add(new Area(triangle));
		triangle.reset();
		return aRec;
	}

	@SuppressWarnings("restriction")
	protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected) {

		g.setFont(font);

		View v = getTextViewForTab(tabIndex);
		if (v != null) {
			// html
			v.paint(g, textRect);
		} else {
			// plain text
			int mnemIndex = tabPane.getDisplayedMnemonicIndexAt(tabIndex);

			if (tabPane.isEnabled() && tabPane.isEnabledAt(tabIndex)) {
				Color fg = tabPane.getForegroundAt(tabIndex);
				if (isSelected) {
					fg = tabPane.getBackgroundAt(tabIndex);
				}
				
				if (isSelected && (fg instanceof UIResource)) {
					Color selectedFG = UIManager.getColor("TabbedPane.selectedForeground");
					if (selectedFG != null) {
						fg = selectedFG;
					}
				}
				
				g.setColor(fg);
				//g.drawString(title, textRect.x, textRect.y);
				SwingUtilities2.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x + 5, textRect.y + metrics.getAscent());

			} else { // tab disabled
				g.setColor(tabPane.getBackgroundAt(tabIndex).brighter());
				SwingUtilities2.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x, textRect.y + metrics.getAscent());
				g.setColor(tabPane.getBackgroundAt(tabIndex).darker());
				SwingUtilities2.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x - 1, textRect.y + metrics.getAscent() - 1);
			}
		}
	}
	
	protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
			int x, int y, int w, int h, boolean isSelected) {
		
		g.setColor(lightHighlight);
		switch (tabPlacement) {
		case LEFT:
			g.drawLine(x + 1, y + h - 2, x + 1, y + h - 2); // bottom-left
															// highlight
			g.drawLine(x, y + 2, x, y + h - 3); // left highlight
			g.drawLine(x + 1, y + 1, x + 1, y + 1); // top-left highlight
			g.drawLine(x + 2, y, x + w - 1, y); // top highlight
			g.setColor(shadow);
			g.drawLine(x + 2, y + h - 2, x + w - 1, y + h - 2); // bottom shadow
			g.setColor(darkShadow);
			g.drawLine(x + 2, y + h - 1, x + w - 1, y + h - 1); // bottom dark
																// shadow
			break;
		case RIGHT:
			g.drawLine(x, y, x + w - 3, y); // top highlight
			g.setColor(shadow);
			g.drawLine(x, y + h - 2, x + w - 3, y + h - 2); // bottom shadow
			g.drawLine(x + w - 2, y + 2, x + w - 2, y + h - 3); // right shadow
			g.setColor(darkShadow);
			g.drawLine(x + w - 2, y + 1, x + w - 2, y + 1); // top-right dark
															// shadow
			g.drawLine(x + w - 2, y + h - 2, x + w - 2, y + h - 2); // bottom-right
																	// dark
																	// shadow
			g.drawLine(x + w - 1, y + 2, x + w - 1, y + h - 3); // right dark
																// shadow
			g.drawLine(x, y + h - 1, x + w - 3, y + h - 1); // bottom dark
															// shadow
			break;
		case BOTTOM:
//			g.drawLine(x, y, x, y + h - 3); // left highlight
//			g.drawLine(x + 1, y + h - 2, x + 1, y + h - 2); // bottom-left
//															// highlight
//			g.setColor(shadow);
//			g.drawLine(x + 2, y + h - 2, x + w - 3, y + h - 2); // bottom shadow
//			g.drawLine(x + w - 2, y, x + w - 2, y + h - 3); // right shadow
//			g.setColor(darkShadow);
//			g.drawLine(x + 2, y + h - 1, x + w - 3, y + h - 1); // bottom dark
//																// shadow
//			g.drawLine(x + w - 2, y + h - 2, x + w - 2, y + h - 2); // bottom-right
//																	// dark
//																	// shadow
//			g.drawLine(x + w - 1, y, x + w - 1, y + h - 3); // right dark shadow
			break;
		case TOP:
		default:
			//g.fillRect(x, y, w, h);
		}
	}

	protected void layoutLabel(int tabPlacement, FontMetrics metrics,
			int tabIndex, String title, Icon icon, Rectangle tabRect,
			Rectangle iconRect, Rectangle textRect, boolean isSelected) {
		textRect.x = textRect.y = iconRect.x = iconRect.y = 0;
		View v = getTextViewForTab(tabIndex);
		if (v != null) {
			tabPane.putClientProperty("html", v);
		}
		SwingUtilities.layoutCompoundLabel(tabPane, metrics, title, icon,
				SwingUtilities.CENTER, SwingUtilities.CENTER,
				SwingUtilities.CENTER, SwingUtilities.TRAILING, tabRect,
				iconRect, textRect, textIconGap);
		tabPane.putClientProperty("html", null);
		int xNudge = getTabLabelShiftX(tabPlacement, tabIndex, isSelected);
		int yNudge = getTabLabelShiftY(tabPlacement, tabIndex, isSelected);
		iconRect.x += xNudge;
		iconRect.y += yNudge;
		textRect.y += yNudge;
		switch (tabPlacement) {
		case LEFT:
			textRect.x += xNudge;
			break;
		case RIGHT:
			textRect.x += xNudge;
			break;
		case BOTTOM:
			textRect.x += xNudge;
			break;
		case TOP:
		default:
			textRect.x += xNudge - 4;
		}
	}
	


}

package cn.hisdar.lib.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JPanel;

public class HSplitPane extends JPanel {
	
	private static final long serialVersionUID = -3644000558823172003L;
	
	public final static int HORIZONTAL_SPLIT = 0;
	public final static int VERTICAL_SPLIT = 1;

	public static final Color DEFAULT_DIVIDER_COLOR = new Color(0x293955);
	
	private Stroke dividerStroke = null;
	private Color  dividerColor  = null;
	
	private int dividerLocation 	= 100;
	private int dividerSize     	= 5;
	private int dividerOrientation  = HORIZONTAL_SPLIT;
	
	private double dividerPercentValue = -1;
	
	private Component leftComponent = null;
	private Component rightComponent = null;
	
	private MouseEventHandler mouseEventHandler = null;
	private Cursor oldCursor = null;
	private boolean resizeFlag = false;
	private Vector<DividerLocationChangeListener> dividerLocationChangeListeners = null;
	
	public HSplitPane() {
		initParameters();
	}
	
	public HSplitPane(int orientation) {
		dividerOrientation = orientation;
		initParameters();
	}
	
	private void initParameters() {
		dividerStroke = new BasicStroke(dividerSize);
		dividerColor  = DEFAULT_DIVIDER_COLOR;
		setBackground(dividerColor);
		dividerLocationChangeListeners = new Vector<DividerLocationChangeListener>();
		
		mouseEventHandler = new MouseEventHandler();
		addMouseListener(mouseEventHandler);
		addMouseMotionListener(mouseEventHandler);
		setBackground(dividerColor);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D)g;
		Stroke oldStroke = g2d.getStroke();
		
		if (dividerPercentValue > 0 && dividerPercentValue <= 1) {
			if (dividerOrientation == HORIZONTAL_SPLIT) {
				dividerLocation = (int)(dividerPercentValue * getHeight());
			} else {
				dividerLocation = (int)(dividerPercentValue * getWidth());
			}
			
			//notifyDividerLocationListeners();
		}
		
		/////////////////////////////////////////////////////////////////
		drawBorder((Graphics2D)g);
		if (dividerOrientation == VERTICAL_SPLIT) {
			if (leftComponent != null) {
				leftComponent.setBounds(0, 0, dividerLocation, getHeight());
				leftComponent.setPreferredSize(new Dimension(dividerLocation, getHeight()));
			}
			
			if (rightComponent != null) {
				int startIndex = dividerLocation + dividerSize;
				rightComponent.setBounds(startIndex, 0, getWidth() - startIndex, getHeight());
				rightComponent.setPreferredSize(new Dimension(getWidth() - startIndex, getHeight()));
			}
		} else if (dividerOrientation == HORIZONTAL_SPLIT) {
			if (leftComponent != null) {
				leftComponent.setBounds(0, 0, getWidth(), dividerLocation);
				leftComponent.setPreferredSize(new Dimension(getWidth(), dividerLocation));
			}
			
			if (rightComponent != null) {
				int startIndex = dividerLocation + dividerSize;
				rightComponent.setBounds(0, startIndex, getWidth(), getHeight() - startIndex);
				rightComponent.setPreferredSize(new Dimension(getWidth(), getHeight() - startIndex));
			}
		}
		/////////////////////////////////////////////////////////////////
		validate();
		g2d.setStroke(oldStroke);
	}
	
	private void drawBorder(Graphics2D g2d) {
		g2d.setColor(dividerColor);

		// 绘制分割线
		g2d.setStroke(dividerStroke);
		if (dividerOrientation == VERTICAL_SPLIT) {
			if (dividerLocation < 0) {
				dividerLocation = 0;
				notifyDividerLocationListeners();
			} else if (dividerLocation > getWidth() - dividerSize) {
				dividerLocation = getWidth() - dividerSize;
				notifyDividerLocationListeners();
			}
			
			g2d.drawLine(dividerLocation, 0, dividerLocation, getHeight());
		} else if (dividerOrientation == HORIZONTAL_SPLIT) {
			if (dividerOrientation < 0) {
				dividerLocation = 0;
				notifyDividerLocationListeners();
			} else if (dividerLocation > getHeight() - dividerSize) {
				dividerLocation = getHeight() - dividerSize;
				notifyDividerLocationListeners();
			}
			
			g2d.drawLine(0, dividerLocation, getWidth(), dividerLocation);
		}
		
		// 去除锯齿
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON);
	}

	public void setDividerLocation(int location) {
		disableDividerPercent();
		dividerLocation = location;
		notifyDividerLocationListeners();
	}
	
	private void disableDividerPercent() {
		dividerPercentValue = -1;
	}
	
	public int getDividerLocation() {
		return dividerLocation;
	}
	
	public void setDividerSize(int newSize) {
		dividerSize = newSize;
	}

	public Component getLeftComponent() {
		return leftComponent;
	}

	public void setTopComponent(Component component) {
		setLayout(null);
		if (leftComponent != null) {
			remove(leftComponent);
		}
		
		leftComponent = component;
		add(leftComponent);
	}
	
	public void setLeftComponent(Component component) {
		setLayout(null);
		if (leftComponent != null) {
			remove(leftComponent);
		}
		
		leftComponent = component;
		add(leftComponent);
	}

	public Component getRightComponent() {
		return rightComponent;
	}

	public void setBottomComponent(Component component) {
		setLayout(null);
		if (rightComponent != null) {
			remove(rightComponent);
		}
		
		rightComponent = component;
		add(rightComponent);
	}
	
	public void setRightComponent(Component component) {
		setLayout(null);
		if (rightComponent != null) {
			remove(rightComponent);
		}
		
		rightComponent = component;
		add(rightComponent);
	}
	
	public void setOrientation(int orientation) {
		dividerOrientation = orientation;
	}
	
	public int getOrientation() {
		return dividerOrientation;
	}
	
	public void addDividerLocationChangeListener(DividerLocationChangeListener listener) {
		for (int i = 0; i < dividerLocationChangeListeners.size(); i++) {
			if (dividerLocationChangeListeners.get(i) == listener) {
				return;
			}
		}
		
		dividerLocationChangeListeners.add(listener);
	}
	
	public void removeDividerLocationChangeListener(DividerLocationChangeListener listener) {
		for (int i = 0; i < dividerLocationChangeListeners.size(); i++) {
			if (dividerLocationChangeListeners.get(i) == listener) {
				dividerLocationChangeListeners.remove(i);
			}
		}
	}
	
	private void notifyDividerLocationListeners() {
		for (int i = 0; i < dividerLocationChangeListeners.size(); i++) {
			dividerLocationChangeListeners.get(i).DividerLocationChangeEvent(this);
		}
	}
	
	/**
	 * HSplitPane 的鼠标事件处理
	 * @author Hisdar
	 *
	 */
	private class MouseEventHandler extends MouseAdapter {
		
		private void setCurrenCursor(Point point) {
			if (dividerOrientation == VERTICAL_SPLIT) {
				if (point.getX() <= dividerLocation + dividerSize && point.getX() >= dividerLocation) {
					setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
				} else {
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			} else if (dividerOrientation == HORIZONTAL_SPLIT) {
				if ((point.getY() >= dividerLocation) && (point.getY() <= dividerLocation + dividerSize)) {
					setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
				} else {
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			if (dividerOrientation == VERTICAL_SPLIT) {
				if (e.getX() <= dividerLocation + dividerSize && e.getX() >= dividerLocation) {
					resizeFlag = true;
				} 
			} else if (dividerOrientation == HORIZONTAL_SPLIT) {
				if (e.getY() >= dividerLocation && e.getY() <= dividerLocation + dividerSize) {
					resizeFlag = true;
				} 
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			setCurrenCursor(e.getPoint());
			resizeFlag = false;
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (e.getX() <= 0 || e.getX() >= getWidth() - dividerSize) {
				return;
			}
			
			if (dividerOrientation == VERTICAL_SPLIT) {
				if (resizeFlag) {
					dividerLocation = e.getX();
					notifyDividerLocationListeners();
				}
				disableDividerPercent();
			} else if (dividerOrientation == HORIZONTAL_SPLIT) {
				if (resizeFlag) {
					dividerLocation = e.getY();
					notifyDividerLocationListeners();
				}
				disableDividerPercent();
			}

			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			setCurrenCursor(e.getPoint());
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			oldCursor = getCursor();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (oldCursor != null) {
				setCursor(oldCursor);
			}
		}
	}

	public void setDividerLocation(double dividerLocation) {
		dividerPercentValue = dividerLocation;
		repaint();
	}

	public void setDividerColor(Color color) {
		dividerColor = color;
		setBackground(dividerColor);
	}
}

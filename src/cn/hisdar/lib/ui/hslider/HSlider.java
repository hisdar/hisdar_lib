package cn.hisdar.lib.ui.hslider;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JComponent;

public class HSlider extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5694510556933880680L;

	private static final int DEFAULT_TRACK_HEIGHT = 10;
	
	private ArrayList<HSliderValueChangeListener> valueChangeListeners;
	private ArrayList<HSliderPressedListener> pressedListeners;
	private ArrayList<HSliderReleaseListener> releaseListeners;
	
	protected Dimension thumbSize = null;
	protected Dimension trackSize = null;
	protected Rectangle thumbRect = null;
	protected Rectangle trackRect = null;
	protected int max;
	protected int min;
	private int oldValue;
	
	private Point mouseOldPoint = null;
	
	private boolean isMouseInThumb = false;
	
	private MouseEventHandler mouseEventHandler = null;
	
	public HSlider() {
		super();
		
		valueChangeListeners = new ArrayList<>();
		pressedListeners = new ArrayList<>();
		releaseListeners = new ArrayList<>();
		
		min = 0;
		max = 100;
		oldValue = min - 1;
		thumbSize = new Dimension(35, 20);
		trackSize = new Dimension(0, DEFAULT_TRACK_HEIGHT);
		
		mouseEventHandler = new MouseEventHandler();
		addMouseListener(mouseEventHandler);
		addMouseMotionListener(mouseEventHandler);
		
		setPreferredSize(thumbSize);
	}

	public void setValue(int value) {
		
		if (thumbRect == null || trackRect == null) {
			return;
		}
		
		// 根据值计算滑块的位置
		int range = max - min;
		
		setThumbRect((int)(value * 1.0f / range * trackRect.width), thumbRect.y, thumbRect.width, thumbRect.height);
		//notifyValueChangeListeners();
	}
	
	public void setRange(int min, int max) {
		
		this.min = min;
		this.max = max;
	}
	
	public void addPressedListener(HSliderPressedListener l) {
		for (int i = 0; i < pressedListeners.size(); i++) {
			if (pressedListeners.get(i) == l) {
				return;
			}
		}
		
		pressedListeners.add(l);
	}
	
	public void addReleaseListener(HSliderReleaseListener l) {
		for (int i = 0; i < releaseListeners.size(); i++) {
			if (releaseListeners.get(i) == l) {
				return;
			}
		}
		
		releaseListeners.add(l);
	}
	
	public void addValueChangeListener(HSliderValueChangeListener l) {
		for (int i = 0; i < valueChangeListeners.size(); i++) {
			if (valueChangeListeners.get(i) == l) {
				return;
			}
		}
		
		valueChangeListeners.add(l);
		l.sliderValueChangeEvent(this, getValue());
	}
	
	public void removePressedListener(HSliderPressedListener l) {
		int listenerCount = pressedListeners.size();
		for (int i = listenerCount - 1; i >= 0; i--) {
			if (pressedListeners.get(i) == l) {
				pressedListeners.remove(i);
			}
		}
	}
	
	public void removeReleaseListener(HSliderReleaseListener l) {
		int listenerCount = releaseListeners.size();
		for (int i = listenerCount - 1; i >= 0; i--) {
			if (releaseListeners.get(i) == l) {
				releaseListeners.remove(i);
			}
		}
	}
	
	public void removeValueChangeListener(HSliderValueChangeListener l) {
		int listenerCount = valueChangeListeners.size();
		for (int i = listenerCount - 1; i >= 0; i--) {
			if (valueChangeListeners.get(i) == l) {
				valueChangeListeners.remove(i);
			}
		}
	}
	
	private void notifyValueChangeListeners() {
		
		int currentValue = getValue();
		if (oldValue == currentValue) {
			return;
		} else {
			oldValue = currentValue;
		}
		
		for (int i = 0; i < valueChangeListeners.size(); i++) {
			valueChangeListeners.get(i).sliderValueChangeEvent(this, currentValue);
		}
	}
	
	private void notifyPressedEvent(Point location, long value) {
		for (int i = 0; i < pressedListeners.size(); i++) {
			pressedListeners.get(i).parsedEvent(this, location, value);
		}
	}
	
	private void notifyReleaseEvent(Point location, long value) {
		for (int i = 0; i < releaseListeners.size(); i++) {
			releaseListeners.get(i).releaseEvent(this, location, value);
		}
	}
	
	public int getValue() {
		
		if (thumbRect == null || trackRect == null) {
			return 0;
		}
		
		float pixValue = thumbRect.x * 1.0f / (trackRect.width - thumbRect.width);
		return (int)((max - min) * pixValue);
	}
	
	public int getRange() {
		return max - min;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		if (thumbRect == null) {
			thumbRect = new Rectangle();
			thumbRect.x = 0;
			thumbRect.y = (getHeight() - thumbSize.height) / 2;
			thumbRect.width = thumbSize.width;
			thumbRect.height = thumbSize.height;
		} else {
			// 界面 大小发生变化
			if (trackRect.width != getWidth()) {
				thumbRect.x = thumbRect.x * (getWidth() - thumbRect.width) / (trackRect.width - thumbRect.width);
			}
		}

		if (trackRect == null) {
			trackRect = new Rectangle();
			trackRect.x = 0;
			trackRect.y = (getHeight() - trackSize.height) / 2;
			trackRect.width = getWidth();
			trackRect.height = trackSize.height;
		} else {
			trackRect.y = (getHeight() - trackSize.height) / 2;
			trackRect.width = getWidth();
		}
		
		paintFocus(g);
		paintTrack(g);
		paintThumb(g);
	}

	public void paintThumb(Graphics g) {
		
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //填充椭圆框为当前thumb位置
 
        g2d.setColor(new Color(0x293955));
        g2d.fillRoundRect(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height, 5, 5);
        
        g2d.setColor(new Color(0xFFFFFF));
        int startX = thumbRect.x + thumbRect.width * 2 / 7 - 1;
        int startY = thumbRect.y + thumbRect.height / 6;
        int width = 2;
        int height = thumbRect.height * 2 / 3;
        
        g2d.fillRoundRect(startX, startY, width, height, 1, 1);
        
        startX = thumbRect.x + thumbRect.width / 2 - 1;
        startY = thumbRect.y + thumbRect.height * 2 / 6;
        height = thumbRect.height / 3;
        g2d.fillRect(startX, startY, width, height);
        
        startX = thumbRect.x + thumbRect.width - thumbRect.width * 2 / 7 - 2;
        startY = thumbRect.y + thumbRect.height / 6;
        height = thumbRect.height * 2 / 3;
        g2d.fillRect(startX, startY , width, height);
    }

    /** *//** 
     * 绘制刻度轨迹
     */
    public void paintTrack(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(new Color(0x00008B));
        g2.drawRoundRect(trackRect.x, trackRect.y, trackRect.width - 1, trackRect.height, 5, 5);
    }

	public void paintFocus(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0xFFFACD));
		g2.fillRoundRect(trackRect.x, trackRect.y, thumbRect.x + thumbRect.width, trackRect.height, 5, 5);
	}

	private void setThumbRect(int x, int y, int width, int height) {
		Rectangle buffRect = new Rectangle(thumbRect);

		if (x <= 0) {
			buffRect.x = 0;
		} else {
			buffRect.x = x;
		}
		
		if (y <= 0) {
			buffRect.y = 0;
		} else {
			buffRect.y = y;
		}
		
		if (buffRect.width == width) {
			if (buffRect.x + width > trackRect.width + trackRect.x) {
				buffRect.x = trackRect.width - buffRect.width;
			}
		} else {
			if (buffRect.x + width > trackRect.width) {
				return;
			} else {
				buffRect.width = width;
			}
		}
		
		thumbRect.setRect(buffRect.x, buffRect.y, buffRect.width, buffRect.height);
		repaint();
	}
	
	private class MouseEventHandler extends MouseAdapter {

		@Override
		public void mouseDragged(MouseEvent e) {
			if (isMouseInThumb) {

				if (mouseOldPoint == null) {
					mouseOldPoint = new Point(e.getX(), e.getY());
				} else {
					setThumbRect(thumbRect.x + e.getPoint().x - mouseOldPoint.x, thumbRect.y, thumbRect.width, thumbRect.height);
					mouseOldPoint.x = e.getPoint().x;
					
					notifyValueChangeListeners();
				}
			}
			
			super.mouseDragged(e);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
			if (mouseOldPoint == null) {
				mouseOldPoint = new Point(e.getX(), e.getY());
			} else {
				mouseOldPoint.setLocation(e.getX(), e.getY());
			}
			
			if ((e.getPoint().x >= thumbRect.x && e.getPoint().x <= thumbRect.x + thumbRect.width)
					&& (e.getPoint().y >= thumbRect.y && e.getPoint().y <= thumbRect.y + thumbRect.height)) {
				isMouseInThumb = true;
			} else {
				isMouseInThumb = false;
				
				if ((e.getPoint().x >= trackRect.x && e.getPoint().x <= trackRect.x + trackRect.width)
						&& (e.getPoint().y >= trackRect.y && e.getPoint().y <= trackRect.y + trackRect.height)) {
					setThumbRect(e.getPoint().x - thumbRect.width / 2, thumbRect.y, thumbRect.width, thumbRect.height);
					notifyValueChangeListeners();
				} else {
					
				}
			}
			
			notifyPressedEvent(e.getPoint(), getValue());
			
			super.mousePressed(e);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			isMouseInThumb = false;
			
			notifyReleaseEvent(e.getPoint(), getValue());
			
			super.mouseReleased(e);
		}
	}
}

package cn.hisdar.lib.ui.hslider;

import java.awt.Point;

public interface HSliderReleaseListener {
	public void releaseEvent(HSlider slider, Point location, long value);
}

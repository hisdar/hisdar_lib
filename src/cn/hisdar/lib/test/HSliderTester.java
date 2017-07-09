package cn.hisdar.lib.test;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import cn.hisdar.lib.ui.UIAdapter;
import cn.hisdar.lib.ui.hslider.HSlider;
import cn.hisdar.lib.ui.hslider.HSliderValueChangeListener;

public class HSliderTester implements HSliderValueChangeListener {

	public HSliderTester() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setSize(600, 200);
		frame.setLocation(UIAdapter.getCenterLocation(null, frame));
		
		HSlider slider = new HSlider();
		slider.addValueChangeListener(this);
		
		frame.setLayout(new BorderLayout());
		frame.add(slider);
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new HSliderTester();
	}

	@Override
	public void sliderValueChangeEvent(HSlider slider, long value) {
		System.out.println("Slider value:" + value);
	}
}

package cn.hisdar.lib.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class HLinearPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6513558736588483439L;
	
	public static final int VERTICAL   = 0;
	public static final int HORIZONTAL = 1;
	
	private ArrayList<JComponent> componentList;
	private ArrayList<JPanel> componentParentList;
	private int orientation;
	
	public HLinearPanel() {
		super();
		init(VERTICAL);
	}
	
	public HLinearPanel(int orientation) {
		super();
		init(orientation);
	}
	
	public void init(int orientation) {
		
		this.orientation = orientation;
		componentList = new ArrayList<JComponent>();
		componentParentList = new ArrayList<JPanel>();
		setLayout(new BorderLayout());
		componentParentList.add(this);
	}
	
	public void add(JComponent component) {
		JPanel componentParentPanel = new JPanel(new BorderLayout());
		componentParentPanel.setOpaque(false);
		if (orientation == VERTICAL) {
			componentParentPanel.add(component, BorderLayout.NORTH);
		} else {
			componentParentPanel.add(component, BorderLayout.WEST);
		}
		
		JPanel lastPanel = componentParentList.get(componentParentList.size() - 1);
		lastPanel.add(componentParentPanel, BorderLayout.CENTER);
		componentParentList.add(componentParentPanel);
		componentList.add(component);
	}
	
	public void removeAllChilds() {
		for (int i = componentList.size(); i > 0; i--) {
			removeChild(componentList.get(i - 1));
		}
	}
	
	public void removeChild(JComponent component) {
		int componentIndex = -1;
		for (int i = 0; i < componentList.size(); i++) {
			if (componentList.get(i) == component) {
				componentIndex = i;
			}
		}
		
//		System.out.println("index=" + componentIndex);
//		System.out.println("componentListsize=" + componentList.size());
//		System.out.println("componentParentList=" + componentParentList.size());
		if (componentIndex == -1) {
			return;
		} else {
			// 即将被移除的组件
			JComponent waitToRemoveComponent = componentList.get(componentIndex);
			JPanel waitToRemoveComponentParent = componentParentList.get(componentIndex + 1);
			
			JPanel parentComponentPanel = componentParentList.get(componentIndex);
			
			// 如果是最后一个组件, 直接将此组件从父组件中移除
			if (componentIndex == componentList.size() - 1) {
				componentParentList.remove(componentIndex + 1);
				componentList.remove(waitToRemoveComponent);
				parentComponentPanel.remove(waitToRemoveComponentParent);
				
			} else {
				// 如果不是最后一个组件, 将该组件从父组件中移除,并将子组件添加到父组件中
				JPanel childComponentParent = componentParentList.get(componentIndex + 2);
				
				componentParentList.remove(componentIndex + 1);
				componentList.remove(waitToRemoveComponent);
				
				waitToRemoveComponentParent.remove(childComponentParent);
				parentComponentPanel.remove(waitToRemoveComponentParent);
				parentComponentPanel.add(childComponentParent, BorderLayout.CENTER);
			}

			revalidate();
			repaint();
		}
	}
	
	public void setOpaqueAll(boolean isOpaque) {
		for (int i = 0; i < componentList.size(); i++) {
			componentList.get(i).setOpaque(isOpaque);
		}
	}
	
	public void setBackgroundAll(Color color) {
		for (int i = 0; i < componentList.size(); i++) {
			componentList.get(i).setBackground(color);
		}
	}
	
	public void setDividingLineSize(int size) {
		Border border = null;
		if (orientation == VERTICAL) {
			border = BorderFactory.createEmptyBorder(0, 0, size, 0);
		} else {
			border = BorderFactory.createEmptyBorder(0, 0, 0, size);
		}
		for (int i = 0; i < componentList.size(); i++) {
			componentList.get(i).setBorder(border);
		}
	}
}

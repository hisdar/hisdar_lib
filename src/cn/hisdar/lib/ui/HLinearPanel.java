package cn.hisdar.lib.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class HLinearPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6513558736588483439L;
	private ArrayList<JComponent> componentList;
	private ArrayList<JPanel> componentParentList;
	
	public HLinearPanel() {
		super();
		componentList = new ArrayList<JComponent>();
		componentParentList = new ArrayList<JPanel>();
		setLayout(new BorderLayout());
		componentParentList.add(this);
	}
	
	public void add(JComponent component) {
		JPanel componentParentPanel = new JPanel(new BorderLayout());
		componentParentPanel.add(component, BorderLayout.NORTH);
		componentParentList.get(componentParentList.size() - 1).add(componentParentPanel, BorderLayout.CENTER);
		componentParentList.add(componentParentPanel);
		componentList.add(component);
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
			
			// 如果是最后一个组件，直接将此组件从父组件中移除
			if (componentIndex == componentList.size() - 1) {
				componentParentList.remove(componentIndex + 1);
				componentList.remove(waitToRemoveComponent);
				parentComponentPanel.remove(waitToRemoveComponentParent);
				
			} else {
				// 如果不是最后一个组件，将该组件从父组件中移除，并将子组件添加到父组件中
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
	
	
	
}

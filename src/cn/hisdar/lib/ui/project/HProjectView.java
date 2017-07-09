package cn.hisdar.lib.ui.project;

import java.awt.BorderLayout;
import java.awt.event.ComponentListener;
import java.awt.event.FocusListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyListener;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cn.hisdar.lib.ui.HLinearPanel;

public class HProjectView extends JPanel {

	private static final long serialVersionUID = -987989739434992672L;

	private ArrayList<HProjectViewItem> projectViewItems = null;
	private ArrayList<MouseListener> mouseListeners = null;
	
	private HLinearPanel projectPanel = null;
	
	private MouseEventHandler mouseEventHandler = null;
	
	public HProjectView() {
		
		mouseEventHandler = new MouseEventHandler();
		
		mouseListeners = new ArrayList<>();
		projectViewItems = new ArrayList<>();
		projectPanel = new HLinearPanel();
		
		setLayout(new BorderLayout());
		add(new JScrollPane(projectPanel), BorderLayout.CENTER);
		
		addMouseListener(mouseEventHandler);
		projectPanel.addMouseListener(mouseEventHandler);
	}
	
	public void addProject(HProjectViewItem projectViewItem) {
		projectPanel.add(projectViewItem);
		projectViewItems.add(projectViewItem);
		
		projectViewItem.addMouseListener(mouseEventHandler);
		for (int j = 0; j < mouseListeners.size(); j++) {
			projectViewItem.addMouseListener(mouseListeners.get(j));
		}
		
		projectPanel.revalidate();
		projectPanel.repaint();
		
		revalidate();
		repaint();
	}
	
	public void removeProject(HProjectViewItem projectViewItem) {
		for (int i = 0; i < projectViewItems.size(); i++) {
			if (projectViewItems.get(i) == projectViewItem) {
				projectViewItems.remove(i);
				projectPanel.removeChild(projectViewItem);
				
				projectViewItem.removeMouseListener(mouseEventHandler);
				for (int j = 0; j < mouseListeners.size(); j++) {
					projectViewItem.removeMouseListener(mouseListeners.get(i));
				}
				
				projectPanel.revalidate();
				projectPanel.repaint();
				
				revalidate();
				repaint();
				break;
			}
		}
	}
	
	public HProjectViewItem getSelectedProject() {
		HProjectViewItem selectedProjectViewItem = null;
		for (int i = 0; i < projectViewItems.size(); i++) {
			if (projectViewItems.get(i).isSelected()) {
				selectedProjectViewItem = projectViewItems.get(i);
				break;
			}
		}
		
		return selectedProjectViewItem;
	}

	public HProjectViewItemNode[] getSelectedNodes() {
		HProjectViewItem selectedProjectViewItem = getSelectedProject();
		return selectedProjectViewItem.getSelectedNodes();
	}
	
	private class MouseEventHandler extends MouseAdapter {
		
		public MouseEventHandler() {
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			
			if (e.getButton() == MouseEvent.BUTTON1) {
				// 如果事件发生在面板上，清空所有的选中项
				for (int i = 0; i < projectViewItems.size(); i++) {
					if (projectViewItems.get(i) != e.getComponent()) {
						projectViewItems.get(i).clearSelection();
					}
				}
			}
			
			super.mousePressed(e);
		}
	}
	
	/***********************************************************************
	 * listener add and remove
	 * *********************************************************************/
	/////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public synchronized void addComponentListener(ComponentListener arg0) {
		super.addComponentListener(arg0);
		projectPanel.addComponentListener(arg0);
		for (int i = 0; i < projectViewItems.size(); i++) {
			projectViewItems.get(i).addComponentListener(arg0);
		}
	}

	@Override
	public synchronized void addFocusListener(FocusListener arg0) {
		super.addFocusListener(arg0);
		projectPanel.addFocusListener(arg0);
		for (int i = 0; i < projectViewItems.size(); i++) {
			projectViewItems.get(i).addFocusListener(arg0);
		}
	}

	@Override
	public void addHierarchyBoundsListener(HierarchyBoundsListener arg0) {
		super.addHierarchyBoundsListener(arg0);
		projectPanel.addHierarchyBoundsListener(arg0);
		for (int i = 0; i < projectViewItems.size(); i++) {
			projectViewItems.get(i).addHierarchyBoundsListener(arg0);
		}
	}

	@Override
	public void addHierarchyListener(HierarchyListener arg0) {
		super.addHierarchyListener(arg0);
		projectPanel.addHierarchyListener(arg0);
		for (int i = 0; i < projectViewItems.size(); i++) {
			projectViewItems.get(i).addHierarchyListener(arg0);
		}
	}

	@Override
	public synchronized void addInputMethodListener(InputMethodListener arg0) {
		super.addInputMethodListener(arg0);
		projectPanel.addInputMethodListener(arg0);
		for (int i = 0; i < projectViewItems.size(); i++) {
			projectViewItems.get(i).addInputMethodListener(arg0);
		}
	}

	@Override
	public synchronized void addKeyListener(KeyListener arg0) {
		super.addKeyListener(arg0);
		projectPanel.addKeyListener(arg0);
		for (int i = 0; i < projectViewItems.size(); i++) {
			projectViewItems.get(i).addKeyListener(arg0);
		}
	}

	@Override
	public synchronized void addMouseListener(MouseListener arg0) {
		super.addMouseListener(arg0);
		projectPanel.addMouseListener(arg0);
		mouseListeners.add(arg0);
		for (int i = 0; i < projectViewItems.size(); i++) {
			projectViewItems.get(i).addMouseListener(arg0);
		}
	}

	@Override
	public synchronized void addMouseMotionListener(MouseMotionListener arg0) {
		super.addMouseMotionListener(arg0);
		projectPanel.addMouseMotionListener(arg0);
		for (int i = 0; i < projectViewItems.size(); i++) {
			projectViewItems.get(i).addMouseMotionListener(arg0);
		}
	}

	@Override
	public synchronized void addMouseWheelListener(MouseWheelListener arg0) {
		super.addMouseWheelListener(arg0);
		projectPanel.addMouseWheelListener(arg0);
		for (int i = 0; i < projectViewItems.size(); i++) {
			projectViewItems.get(i).addMouseWheelListener(arg0);
		}
	}

	@Override
	public synchronized void removeComponentListener(ComponentListener l) {
		super.removeComponentListener(l);
		projectPanel.removeComponentListener(l);
		for (int i = 0; i < projectViewItems.size(); i++) {
			projectViewItems.get(i).removeComponentListener(l);
		}
	}

	@Override
	public synchronized void removeFocusListener(FocusListener l) {
		super.removeFocusListener(l);
		projectPanel.removeFocusListener(l);
		for (int i = 0; i < projectViewItems.size(); i++) {
			projectViewItems.get(i).removeFocusListener(l);
		}
	}

	@Override
	public void removeHierarchyBoundsListener(HierarchyBoundsListener l) {
		super.removeHierarchyBoundsListener(l);
		projectPanel.removeHierarchyBoundsListener(l);
		for (int i = 0; i < projectViewItems.size(); i++) {
			projectViewItems.get(i).removeHierarchyBoundsListener(l);
		}
	}

	@Override
	public void removeHierarchyListener(HierarchyListener l) {
		super.removeHierarchyListener(l);
		projectPanel.removeHierarchyListener(l);
		for (int i = 0; i < projectViewItems.size(); i++) {
			projectViewItems.get(i).removeHierarchyListener(l);
		}
	}

	@Override
	public synchronized void removeInputMethodListener(InputMethodListener l) {
		super.removeInputMethodListener(l);
		projectPanel.removeInputMethodListener(l);
		for (int i = 0; i < projectViewItems.size(); i++) {
			projectViewItems.get(i).removeInputMethodListener(l);
		}
	}

	@Override
	public synchronized void removeKeyListener(KeyListener l) {
		super.removeKeyListener(l);
		projectPanel.removeKeyListener(l);
		for (int i = 0; i < projectViewItems.size(); i++) {
			projectViewItems.get(i).removeKeyListener(l);
		}
	}

	@Override
	public synchronized void removeMouseListener(MouseListener l) {
		super.removeMouseListener(l);
		projectPanel.removeMouseListener(l);
		mouseListeners.remove(l);
		for (int i = 0; i < projectViewItems.size(); i++) {
			projectViewItems.get(i).removeMouseListener(l);
		}
	}

	@Override
	public synchronized void removeMouseMotionListener(MouseMotionListener l) {
		super.removeMouseMotionListener(l);
		projectPanel.removeMouseMotionListener(l);
		for (int i = 0; i < projectViewItems.size(); i++) {
			projectViewItems.get(i).removeMouseMotionListener(l);
		}
	}

	@Override
	public synchronized void removeMouseWheelListener(MouseWheelListener l) {
		super.removeMouseWheelListener(l);
		projectPanel.removeMouseWheelListener(l);
		for (int i = 0; i < projectViewItems.size(); i++) {
			projectViewItems.get(i).removeMouseWheelListener(l);
		}
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		super.removePropertyChangeListener(listener);
		projectPanel.removePropertyChangeListener(listener);
		for (int i = 0; i < projectViewItems.size(); i++) {
			projectViewItems.get(i).removePropertyChangeListener(listener);
		}
	}

	@Override
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		super.removePropertyChangeListener(propertyName, listener);
		projectPanel.removePropertyChangeListener(propertyName, listener);
		for (int i = 0; i < projectViewItems.size(); i++) {
			projectViewItems.get(i).removePropertyChangeListener(propertyName, listener);
		}
	}
}

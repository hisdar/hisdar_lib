package cn.hisdar.lib.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.hisdar.lib.log.HLog;
import cn.hisdar.lib.ui.HLinearPanel;

public class HTabbedPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static Color DEFAULT_TITLE_PANEL_COLOR = new Color(0x44587c);
	
	private ArrayList<HTabbedTitle> titleList;
	private ArrayList<JComponent>   componentList;
	private HLinearPanel titleManagerPanel;
	private int selectedIndex = -1;
	
	public HTabbedPane() {
		setLayout(new BorderLayout());
		titleList = new ArrayList<>();
		componentList = new ArrayList<>();
		
		titleManagerPanel = new HLinearPanel(HLinearPanel.HORIZONTAL);
		titleManagerPanel.setBackground(DEFAULT_TITLE_PANEL_COLOR);
		super.add(titleManagerPanel, BorderLayout.SOUTH);
	}

	public void add(JComponent component, String name) {
		// check exist
		for (int i = 0; i < componentList.size(); i++) {
			if (componentList.get(i) == component) {
				HLog.il("componet is exist");
				return;
			}
		}
		
		HTabbedTitle label = new HTabbedTitle(name, false);

		titleList.add(label);
		componentList.add(component);
		titleManagerPanel.add(label);
		
		if (selectedIndex == -1) {
			selectedIndex = 0;
			updateSelectedComponent();
		}
		
		HLog.il("add " + name + " success!!!");
		repaint();
	}
	
	private void updateSelectedComponent() {
		super.add(componentList.get(selectedIndex), BorderLayout.CENTER);
	}
	
	private class HTabbedTitle extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private JLabel nameLabel;
		private JButton exitButton;
		private boolean enableDeleteButton;
		
		public HTabbedTitle(String name, boolean enableDeleteButton) {
			
			this.enableDeleteButton = enableDeleteButton;
			
			setLayout(new BorderLayout());
			
			setOpaque(false);
			
			nameLabel = new JLabel(name);
			nameLabel.setForeground(Color.WHITE);
			nameLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
			add(nameLabel, BorderLayout.CENTER);
			
			if (enableDeleteButton) {
				exitButton = new JButton("x");
				exitButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				exitButton.setBackground(null);
				exitButton.setOpaque(false);
				add(exitButton, BorderLayout.EAST);
			}
		}
	}
}

package cn.hisdar.lib.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitlePanel extends JPanel {

	private static final long serialVersionUID = -2801681204415430320L;
	public final static Color DEFAULT_TITLE_PANEL_COLOR = new Color(0x44587c);
	private JLabel titleLabel = null;
	
	public TitlePanel(String title) {
		
		titleLabel = new JLabel(title);
		
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
		
		setBackground(DEFAULT_TITLE_PANEL_COLOR);
		setBorder(null);
		((FlowLayout)getLayout()).setHgap(2);
		((FlowLayout)getLayout()).setVgap(2);
		((FlowLayout)getLayout()).setAlignment(FlowLayout.LEFT);
		add(titleLabel);
	}
	
	public void setBackgroundColor(Color color) {
		setBackground(color);
	}
}

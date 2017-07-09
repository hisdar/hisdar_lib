package cn.hisdar.lib.ui.output;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class HKeyValuePanel extends JPanel {

	private JLabel keyLabel = null;
	private JLabel valueLabel = null;
	
	public HKeyValuePanel() {
		initUI();
	}
	
	public HKeyValuePanel(String key, String value) {
		initUI();
		keyLabel.setText(key);
		valueLabel.setText(value);
	}
	
	private void initUI() {
		setLayout(new BorderLayout());
		
		keyLabel = new JLabel("");
		valueLabel = new JLabel("");
		
		add(keyLabel, BorderLayout.WEST);
		add(valueLabel, BorderLayout.CENTER);
	}
	
	public void setKey(String key) {
		keyLabel.setText(key);
	}
	
	public void setValue(String value) {
		valueLabel.setText(value);
	}
	
	public String getKey() {
		return keyLabel.getText();
	}
	
	public String getValue() {
		return valueLabel.getText();
	}

	public JLabel getKeyLabel() {
		return keyLabel;
	}

	public void setKeyLabel(JLabel keyLabel) {
		this.keyLabel = keyLabel;
	}

	public JLabel getValueLabel() {
		return valueLabel;
	}

	public void setValueLabel(JLabel valueLabel) {
		this.valueLabel = valueLabel;
	}
	
	
}

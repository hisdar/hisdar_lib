package cn.hisdar.lib.ui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class HProgressDialog extends JDialog {

	private JLabel logoLabel;
	private JLabel messageLabel;
	private JProgressBar progressBar;
	
	public HProgressDialog() {
		setSize(600, 200);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setLocation(UIAdapter.getCenterLocation(null, this));
	
		setLayout(new BorderLayout());
		logoLabel = new JLabel("");
		add(logoLabel, BorderLayout.NORTH);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		messageLabel = new JLabel(" ");
		messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
		messageLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		progressBar = new JProgressBar(0, 100);
		mainPanel.add(messageLabel, BorderLayout.NORTH);
		mainPanel.add(progressBar, BorderLayout.CENTER);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
		
		add(mainPanel, BorderLayout.CENTER);
	}

	public JLabel getLogoLabel() {
		return logoLabel;
	}

	public void setLogoLabel(JLabel logoLabel) {
		this.logoLabel = logoLabel;
	}

	public JLabel getMessageLabel() {
		return messageLabel;
	}

	public void setMessageLabel(JLabel messageLabel) {
		this.messageLabel = messageLabel;
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}
	
	public void setLogo(Icon logoIcon) {
		logoLabel.setIcon(logoIcon);
	}
	
	public void setMessage(String message) {
		messageLabel.setText(message);
	}
	
	public void setProgress(int progress) {
		progressBar.setValue(progress);
	}
	
	public void setProgressModel(boolean indeterminate) {
		progressBar.setIndeterminate(indeterminate);
	}
}

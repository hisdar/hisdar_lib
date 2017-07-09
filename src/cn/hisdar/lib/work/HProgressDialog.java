package cn.hisdar.lib.work;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import cn.hisdar.lib.ui.HVerticalLineLabel;
import cn.hisdar.lib.ui.UIAdapter;

public class HProgressDialog extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = -1603724955326829022L;

	public static final int DEFAULT_PROGRESS_DIALOG_WIDTH = 600;
	public static final int PROGRESS_DIALOG_HEIGHT_WITH_ACTION_PANEL = 200;
	public static final int PROGRESS_DIALOG_HEIGHT_WITHOUT_ACTION_PANEL = 145;
	public static final int EMPTY_PANEL_HEIGHT = 35;

	public static final int DEFAULT_LOGO_HEIGHT = 60;
	
	//private Color DEFAULT_LOGO_BASE_COLOR = new Color(0x293955);
	public Color DEFAULT_LOGO_BASE_COLOR = new Color(0xF0FFF0);
	private Color DEFAULT_LOGO_TEXT_COLOR = new Color(0x000000);

	public JLabel logoLabel;
	public JLabel messageLabel;
	public JProgressBar progressBar;
	public JButton cancelButton;
	
	public Icon logoIcon;
	public Font textFont;
	
	private ProgressDialogLister progressDialogLister;
	private JPanel emptyActionPanel;
	private JPanel actionPanel;
	private HWorkActionListener workActionListener = null;
	
	public HProgressDialog() {
		super();
		initDialogUI(null);
	}
	
	public HProgressDialog(HWorkActionListener listener) {
		super();
		workActionListener = listener;
		initDialogUI(null);
	}
	
	public HProgressDialog(HWorkActionListener listener, Icon logoIcon) {
		super();
		workActionListener = listener;
		
		initDialogUI(logoIcon);
	}
	
	public void setProgressIndeterminate(boolean indeterminate) {
		progressBar.setIndeterminate(indeterminate);
	}
	
	public void setProgressValue(float progress) throws NumberOutOfRangeException {
		if (progress < 0 || progress > 1) {
			throw new NumberOutOfRangeException(0, 1, progress);
		}
		
		progressBar.setIndeterminate(false);
		progressBar.setValue((int) (progress * 100));
	}
	
	public void setMessage(String message) {
		messageLabel.setText(message);
	}
	
	public void setLogo(Icon logoIcon) {
		this.logoIcon = logoIcon;
		logoLabel.setIcon(logoIcon);
		refreshUI();
	}
	
	public HWorkActionListener getHWorkActionListener() {
		return workActionListener;
	}

	public void setHWorkActionListener(HWorkActionListener workActionListener) {
		this.workActionListener = workActionListener;
		refreshUI();
	}
	
	private void initDialogUI(Icon logoIcon) {

		// if logoIcon is null, create default logo
		if (logoIcon == null) {
			this.logoIcon = createDefaultLogIcon(DEFAULT_PROGRESS_DIALOG_WIDTH, DEFAULT_LOGO_HEIGHT);
		} else {
			this.logoIcon = logoIcon;
		}

		progressDialogLister = new ProgressDialogLister();
		addWindowListener(progressDialogLister);
		
		// init logo area
		setLayout(new BorderLayout());
		logoLabel = new JLabel(this.logoIcon);
		add(logoLabel, BorderLayout.NORTH);
		
		// init message and progress area
		progressBar = new JProgressBar(0, 100);
		progressBar.setIndeterminate(true);
		
		textFont = new Font("微软雅黑", Font.PLAIN, 14);
		messageLabel = new JLabel("任务执行中，请稍等......");
		messageLabel.setBorder(BorderFactory.createEmptyBorder(1, 1, 15, 5));
		messageLabel.setFont(textFont);
		
		JPanel messageAndProgressPanel = new JPanel(new BorderLayout());
		messageAndProgressPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		messageAndProgressPanel.add(messageLabel, BorderLayout.NORTH);
		messageAndProgressPanel.add(progressBar, BorderLayout.CENTER);
		
		add(messageAndProgressPanel, BorderLayout.CENTER);
		
		// init button area
		JPanel buttonPanel = new JPanel(new GridLayout(1, 1, 10, 10));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 350, 25, 20));
		cancelButton = new JButton("取  消");
		cancelButton.setFont(textFont);
		cancelButton.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		cancelButton.addActionListener(this);
		buttonPanel.add(cancelButton);
		
		actionPanel = new JPanel(new BorderLayout());
		actionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		actionPanel.add(new HVerticalLineLabel(), BorderLayout.NORTH);
		actionPanel.add(buttonPanel, BorderLayout.CENTER);
		
		emptyActionPanel = new JPanel();
		emptyActionPanel.setPreferredSize(new Dimension(0, EMPTY_PANEL_HEIGHT));
		
		refreshUI();
		setModal(true);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	}
	
	private void refreshUI() {
		
		int logoHeight = logoIcon.getIconHeight();
		
		if (workActionListener != null) {
			add(actionPanel, BorderLayout.SOUTH);
			setSize(DEFAULT_PROGRESS_DIALOG_WIDTH, PROGRESS_DIALOG_HEIGHT_WITH_ACTION_PANEL + logoHeight);
		} else {
			add(emptyActionPanel, BorderLayout.SOUTH);
			setSize(DEFAULT_PROGRESS_DIALOG_WIDTH, PROGRESS_DIALOG_HEIGHT_WITHOUT_ACTION_PANEL + logoHeight);
		}
		
		setLocation(UIAdapter.getCenterLocation(null, this));
	}
	
	private ImageIcon createDefaultLogIcon(int width, int height) {
		BufferedImage defaultIconImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D imageG2D = (Graphics2D)defaultIconImage.getGraphics();
		imageG2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		imageG2D.setPaint(new GradientPaint(0, 0, DEFAULT_LOGO_BASE_COLOR, 500, 0, Color.WHITE));
		imageG2D.fillRect(0, 0, width, height);

		String defaultLogoMessage = "任务执行中，请稍等";
		imageG2D.setFont(new Font("新宋体", Font.BOLD, 18));
		int textHeight = imageG2D.getFontMetrics().getHeight();
		
		int textStartIndexY = (height + textHeight * 2 / 3) / 2;
		
		imageG2D.setColor(DEFAULT_LOGO_TEXT_COLOR);
		imageG2D.drawString(defaultLogoMessage, textHeight, textStartIndexY);
		
		return new ImageIcon(defaultIconImage);
	}

	private void cancelButtonEventHandler() {
		if (workActionListener != null) {
			if (workActionListener.cancelTaskEvent()) {
				setVisible(false);
			}
		} else {
			// no cancel listener, do nothing
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancelButton) {
			cancelButtonEventHandler();
		}
	}

//	@Override
//	public void setVisible(boolean b) {
//		if (!b) {
//			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			setModal(false);
//		}
//		
//		super.setVisible(b);
//	}

	private class ProgressDialogLister extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			cancelButtonEventHandler();
		}
	}
}
